package ax.barchuk.common.order

import ax.barchuk.common.saga.SagaEvent

data class OrderPurchaseEvent(
    val orderId: Long,
    val userId: Long,
    val price: Float
) : SagaEvent {
    companion object {
        const val EVENT = "OrderPurchase"
    }

    override fun getEvent() = EVENT

}
