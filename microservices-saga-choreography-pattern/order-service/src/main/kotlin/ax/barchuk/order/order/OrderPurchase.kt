package ax.barchuk.order.order

import ax.barchuk.common.order.OrderPurchaseEvent
import ax.barchuk.common.order.OrderStatus
import ax.barchuk.common.saga.SagaEventMapper
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "order_purchase")
data class OrderPurchase(
    @Id
    @GeneratedValue
    var id: Long = 0,
    val userId: Long,
    val productId: Long,
    val price: Float,
    var status: OrderStatus
): SagaEventMapper<OrderPurchaseEvent> {
    override fun map(): OrderPurchaseEvent {
        return OrderPurchaseEvent(userId, productId, price)
    }
}
