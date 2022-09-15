package ax.barchuk.payment.payment

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
