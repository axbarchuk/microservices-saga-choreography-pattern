package ax.barchuk.order.order

import ax.barchuk.common.order.OrderPurchaseEvent
import ax.barchuk.common.saga.SagaProcessor
import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks

@Component
class OrderPurchaseProcessor(val sink: Sinks.Many<OrderPurchaseEvent>) : SagaProcessor<OrderPurchase> {
    override fun process(order: OrderPurchase) {
        sink.emitNext(order.map(), Sinks.EmitFailureHandler.FAIL_FAST);
    }
}
