package ax.barchuk.common.saga

interface SagaEventMapper<T : SagaEvent> {
    fun map(): T
}