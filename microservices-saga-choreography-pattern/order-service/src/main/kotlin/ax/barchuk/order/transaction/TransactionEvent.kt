package ax.barchuk.order.transaction

import ax.barchuk.order.saga.SagaEvent

class TransactionEvent(
    val orderId: Long,
    val status: TransactionStatus
) : SagaEvent {
    companion object {
        const val EVENT = "Transaction"
    }

    override fun getEvent() = EVENT
}

enum class TransactionStatus {
    SUCCESSFUL,
    UNSUCCESSFUL
}