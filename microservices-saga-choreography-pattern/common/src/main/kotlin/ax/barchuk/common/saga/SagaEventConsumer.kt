package ax.barchuk.common.saga

interface SagaEventConsumer<T : SagaEvent> {

    fun consume(event: T);

}