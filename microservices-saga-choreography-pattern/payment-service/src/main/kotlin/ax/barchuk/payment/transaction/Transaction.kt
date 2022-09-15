package ax.barchuk.payment.transaction

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id
    @GeneratedValue
    var id: Long = 0,
    val orderId: Long,
    val price: Float
)
