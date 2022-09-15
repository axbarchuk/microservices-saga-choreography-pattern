package ax.barchuk.order.config

import ax.barchuk.common.order.OrderPurchaseEvent
import ax.barchuk.common.saga.SagaEventConsumer
import ax.barchuk.common.transaction.TransactionEvent
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;


@Configuration
class OrderServiceConfig(val transactionEventConsumer: SagaEventConsumer<TransactionEvent>) {
    @Bean
    fun sink(): Sinks.Many<OrderPurchaseEvent> {
        return Sinks.many()
            .multicast()
            .directBestEffort()
    }

    @Bean
    fun orderPurchaseEventPublisher(publisher: Sinks.Many<OrderPurchaseEvent>): Supplier<Flux<OrderPurchaseEvent>> {
        return Supplier { publisher.asFlux() }
    }

    @Bean
    fun transactionEventProcessor(): Consumer<TransactionEvent> {
        return Consumer { transactionEventConsumer.consume(it) }
    }
}