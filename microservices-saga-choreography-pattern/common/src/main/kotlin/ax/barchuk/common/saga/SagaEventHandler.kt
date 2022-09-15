package ax.barchuk.common.saga

interface SagaEventHandler<T : SagaEvent, R : SagaEvent> {
    fun handle(event: T): R
}