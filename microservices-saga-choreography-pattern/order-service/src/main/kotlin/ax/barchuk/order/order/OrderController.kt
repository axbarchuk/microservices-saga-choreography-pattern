package ax.barchuk.order.order

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/orders")
class OrderController(val orderService: OrderService) {

    @PostMapping
    fun create(@RequestBody order: OrderCreateRequest): Mono<OrderPurchase> {
        return orderService.create(order)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): Mono<OrderPurchase> {
        return orderService.getById(id)
    }

    @GetMapping
    fun getAll(): Flux<OrderPurchase> {
        return orderService.getAll()
    }

    @GetMapping(path = ["/stream"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getAllAsStream(): Flux<List<OrderPurchase>> {
        return orderService.reactiveGetAll()
    }

}

data class OrderCreateRequest(
    val userId: Long,
    val productId: Long
)
