package ax.barchuk.payment.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.scheduler.Scheduler
import reactor.core.scheduler.Schedulers
import java.util.concurrent.Executors

@Configuration
class JDBCSchedulerConfig {

    @Bean
    fun jdbcScheduler(@Value("\${spring.datasource.maximum-pool-size}") connectionPoolSize: Int): Scheduler? {
        return Schedulers.fromExecutor(Executors.newFixedThreadPool(connectionPoolSize))
    }

}