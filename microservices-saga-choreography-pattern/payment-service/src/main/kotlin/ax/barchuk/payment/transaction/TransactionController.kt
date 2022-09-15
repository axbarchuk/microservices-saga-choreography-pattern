package ax.barchuk.payment.transaction

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/transactions/orders")
class TransactionController(val transactionService: TransactionService) {

    @GetMapping
    fun getAll(): Flux<Transaction> = transactionService.getAll()

    @GetMapping(path = ["/stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getAllAsStream(): Flux<List<Transaction>> = transactionService.reactiveGetAll()

    @GetMapping("/{id}")
    fun getByID(@PathVariable id: Long): Mono<Transaction> = transactionService.getById(id)

}