package ax.barchuk.common.saga

interface SagaEvent {
    fun getEvent(): String
}