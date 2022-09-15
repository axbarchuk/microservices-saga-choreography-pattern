package ax.barchuk.order.order

import ax.barchuk.order.saga.SagaEvent
import ax.barchuk.order.saga.SagaEventMapper
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
    val price: Double,
    var status: OrderStatus
): SagaEventMapper<OrderPurchaseEvent> {
    override fun map(): OrderPurchaseEvent {
        return OrderPurchaseEvent(userId, productId, price)
    }
}

data class OrderPurchaseEvent(
    val userId: Long,
    val productId: Long,
    val price: Double
) : SagaEvent {
    companion object {
        const val EVENT = "OrderPurchase"
    }

    override fun getEvent() = EVENT

}

enum class OrderStatus {
    CREATED,
    COMPLETED,
    FAILED
}