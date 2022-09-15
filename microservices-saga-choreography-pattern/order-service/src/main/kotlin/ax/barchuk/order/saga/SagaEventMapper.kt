package ax.barchuk.order.saga

interface SagaEventMapper<T : SagaEvent> {
    fun map(): T
}