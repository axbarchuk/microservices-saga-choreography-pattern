package ax.barchuk.order.transaction

import ax.barchuk.common.order.OrderStatus
import ax.barchuk.common.saga.SagaEventConsumer
import ax.barchuk.common.transaction.TransactionEvent
import ax.barchuk.common.transaction.TransactionStatus
import ax.barchuk.order.order.OrderPurchaseRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler;

@Component
class TransactionEventConsumer(
    val orderPurchaseRepo: OrderPurchaseRepository,
    val scheduler: Scheduler
) : SagaEventConsumer<TransactionEvent> {
    override fun consume(event: TransactionEvent) {
        Mono.fromRunnable<Any> {
            orderPurchaseRepo.findById(event.orderId).ifPresent {
                it.status =
                    if (TransactionStatus.SUCCESSFUL == event.status) OrderStatus.COMPLETED else OrderStatus.FAILED
            }
        }.subscribeOn(scheduler)
            .subscribe()
    }
}