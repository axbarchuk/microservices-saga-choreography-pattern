package ax.barchuk.order.saga

interface SagaEvent {
    fun getEvent(): String
}