package ax.barchuk.order.product

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "products")
data class Product(
    @Id
    @GeneratedValue
    val id: Long,
    val productId: Long,
    val price: Double
)
