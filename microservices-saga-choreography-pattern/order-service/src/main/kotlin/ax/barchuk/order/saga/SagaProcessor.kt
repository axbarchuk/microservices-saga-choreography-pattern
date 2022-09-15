package ax.barchuk.order.saga

interface SagaProcessor<T> {

    fun process(t: T)

}