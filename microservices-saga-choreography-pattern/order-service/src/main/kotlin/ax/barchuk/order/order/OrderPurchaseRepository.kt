package ax.barchuk.order.order

import org.springframework.data.jpa.repository.JpaRepository

interface OrderPurchaseRepository : JpaRepository<OrderPurchase, Long>