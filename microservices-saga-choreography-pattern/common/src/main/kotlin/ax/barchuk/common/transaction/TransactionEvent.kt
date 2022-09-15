package ax.barchuk.common.transaction

import ax.barchuk.common.saga.SagaEvent

class TransactionEvent(
    val orderId: Long,
    val status: TransactionStatus
) : SagaEvent {
    companion object {
        const val EVENT = "Transaction"
    }

    override fun getEvent() = EVENT
}
