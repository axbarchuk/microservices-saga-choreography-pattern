package ax.barchuk.payment.payment

import ax.barchuk.common.payment.PaymentEvent
import ax.barchuk.common.payment.PaymentStatus
import ax.barchuk.common.saga.SagaEventHandler
import ax.barchuk.common.transaction.TransactionEvent
import ax.barchuk.common.transaction.TransactionStatus
import ax.barchuk.payment.transaction.Transaction
import ax.barchuk.payment.transaction.TransactionRepository
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.core.scheduler.Scheduler

@Component
class PaymentEventHandler(
    val scheduler: Scheduler,
    val transactionRepository: TransactionRepository,
) : SagaEventHandler<PaymentEvent, TransactionEvent> {

    override fun handle(event: PaymentEvent): TransactionEvent {
        Mono.fromRunnable<Any> {
            transactionRepository.save(Transaction(orderId = event.orderId, price = event.price))
        }.subscribeOn(scheduler).subscribe()

        return TransactionEvent(
            event.orderId,
            if (PaymentStatus.APPROVED == event.status) TransactionStatus.SUCCESSFUL else TransactionStatus.UNSUCCESSFUL
        )
    }

}