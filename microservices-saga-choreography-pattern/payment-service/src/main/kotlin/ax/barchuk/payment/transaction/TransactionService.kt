package ax.barchuk.payment.transaction

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler
import reactor.util.function.Tuple2
import java.time.Duration
import java.util.stream.Stream

@Service
class TransactionService(
    val scheduler: Scheduler,
    val transactionRepository: TransactionRepository
) {
    fun getAll(): Flux<Transaction> {
        return Flux.defer { Flux.fromIterable(transactionRepository.findAll()) }
            .subscribeOn(scheduler)
    }

    fun reactiveGetAll(): Flux<List<Transaction>> {
        val interval = Flux.interval(Duration.ofMillis(2000))
        val transactionFlux = Flux.fromStream { Stream.generate(transactionRepository::findAll) }

        return Flux.zip(interval, transactionFlux)
            .map { obj: Tuple2<Long, List<Transaction>> -> obj.t2 }
    }

    fun getById(id: Long): Mono<Transaction> {
        return Mono.fromCallable {
            transactionRepository.findById(id)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction [$id] does not exist") }
        }.subscribeOn(scheduler)
    }
}