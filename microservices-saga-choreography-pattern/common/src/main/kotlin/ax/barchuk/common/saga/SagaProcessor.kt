package ax.barchuk.common.saga

interface SagaProcessor<T> {

    fun process(t: T)

}