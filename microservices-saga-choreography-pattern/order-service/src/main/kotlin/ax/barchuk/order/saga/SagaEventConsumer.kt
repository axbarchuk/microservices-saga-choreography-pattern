package ax.barchuk.order.saga

interface SagaEventConsumer<T : SagaEvent> {

    fun consume(event: T);

}