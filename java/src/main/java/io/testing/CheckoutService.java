package io.testing;

public class CheckoutService {
    private final PaymentService paymentService;
    private final TransactionRecord transactionRecord;

    public CheckoutService(PaymentService ps, TransactionRecord tr) {
        paymentService = ps;
        transactionRecord = tr;
    }

    public boolean checkout(Cart cart) {
        int totalPrice = cart.totalPrice();
        if (paymentService.charge(totalPrice)) {
            transactionRecord.logTransaction(totalPrice);
            return true;
        }
        return false;
    }
}
