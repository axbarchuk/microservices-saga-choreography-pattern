package ax.barchuk.payment.order

import ax.barchuk.common.order.OrderPurchaseEvent
import ax.barchuk.common.payment.PaymentEvent
import ax.barchuk.common.payment.PaymentStatus
import ax.barchuk.common.saga.SagaEventHandler
import ax.barchuk.payment.user.User
import ax.barchuk.payment.user.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.server.ResponseStatusException
import javax.transaction.Transactional

@Component
class OrderPurchaseEventHandler(
    val userRepository: UserRepository
) : SagaEventHandler<OrderPurchaseEvent, PaymentEvent> {

    @Transactional
    override fun handle(event: OrderPurchaseEvent): PaymentEvent {
        val user = userRepository.findById(event.orderId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, "User [${event.userId}] does not exist") }
        val paymentEvent = PaymentEvent(event.orderId, PaymentStatus.DECLINED, event.price)

        userRepository.findById(event.userId).ifPresent { deductUserBalance(event.price, paymentEvent, user) }

        return paymentEvent
    }

    private fun deductUserBalance(orderPrice: Float, paymentEvent: PaymentEvent, user: User) {
        if (user.balance >= orderPrice) {
            user.balance = user.balance - orderPrice

            userRepository.save(user)
            paymentEvent.status = PaymentStatus.APPROVED
        }
    }

}