package ax.barchuk.common.payment

import ax.barchuk.common.saga.SagaEvent

class PaymentEvent(
    val orderId: Long,
    var status: PaymentStatus,
    val price: Float
) : SagaEvent {
    companion object {
        const val EVENT = "Payment"
    }

    override fun getEvent() = EVENT

}
