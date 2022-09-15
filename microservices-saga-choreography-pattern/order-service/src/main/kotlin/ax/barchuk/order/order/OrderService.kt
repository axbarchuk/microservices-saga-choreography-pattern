package ax.barchuk.order.order

import ax.barchuk.common.order.OrderStatus
import ax.barchuk.order.product.ProductRepository
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
class OrderService(
    val orderPurchaseRepository: OrderPurchaseRepository,
    val orderPurchaseProcessor: OrderPurchaseProcessor,
    val productRepository: ProductRepository,
    val scheduler: Scheduler
) {
    fun create(order: OrderCreateRequest): Mono<OrderPurchase> {
        val orderPurchase = getOrderPurchase(order)
        val savedOrderPurchase = orderPurchaseRepository.save(orderPurchase)
        orderPurchaseProcessor.process(orderPurchase)

        return Mono.just(savedOrderPurchase)
    }

    fun getAll(): Flux<OrderPurchase> {
        return Flux.defer { Flux.fromIterable(orderPurchaseRepository.findAll()) }
            .subscribeOn(scheduler)
    }

    fun reactiveGetAll(): Flux<List<OrderPurchase>> {
        val interval = Flux.interval(Duration.ofMillis(2000))
        val orderPurchaseFlux = Flux.fromStream(Stream.generate(orderPurchaseRepository::findAll))

        return Flux.zip(interval, orderPurchaseFlux)
            .map { obj: Tuple2<Long, List<OrderPurchase>> -> obj.t2 }
    }

    fun getById(id: Long): Mono<OrderPurchase> {
        return Mono.fromCallable {
            orderPurchaseRepository.findById(id)
                .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "Order [$id] does not exist") }
        }.subscribeOn(scheduler)
    }

    private fun getOrderPurchase(order: OrderCreateRequest): OrderPurchase {
        val productId = order.productId
        return OrderPurchase(
            userId = order.userId,
            productId = productId,
            price = productRepository.findById(productId)
                .orElseThrow {
                    ResponseStatusException(HttpStatus.NOT_FOUND, "Product [$productId] doesn not exit")
                }.price,
            status = OrderStatus.CREATED
        )
    }
}
