package ax.barchuk.order.transaction

import ax.barchuk.order.saga.SagaEvent
import java.math.BigDecimal
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "payments")
data class Payment(
    @Id
    @GeneratedValue
    val id: Long,
    val amount: BigDecimal
)

class PaymentEvent(
    val orderId: Long,
    val status: PaymentStatus,
    val price: Double
) : SagaEvent {
    companion object {
        const val EVENT = "Payment"
    }

    override fun getEvent() = EVENT

}

enum class PaymentStatus {
    APPROVED,
    DECLINED
}