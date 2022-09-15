package ax.barchuk.payment.config

import ax.barchuk.common.order.OrderPurchaseEvent
import ax.barchuk.common.payment.PaymentEvent
import ax.barchuk.common.saga.SagaEventHandler
import ax.barchuk.common.transaction.TransactionEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PaymentServiceConfig(
    val paymentEventHandler: SagaEventHandler<PaymentEvent, TransactionEvent>,
    val orderPurchaseEventHandler: SagaEventHandler<OrderPurchaseEvent, PaymentEvent>
) {
    @Bean
    fun orderPurchaseEventProcessor(): (OrderPurchaseEvent) -> PaymentEvent {
        return orderPurchaseEventHandler::handle
    }

    @Bean
    fun paymentEventSubscriber(): (PaymentEvent) -> TransactionEvent {
        return paymentEventHandler::handle
    }
}