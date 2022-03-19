package io.testing;

import javax.lang.model.type.ExecutableType;

public interface PaymentService {
    public class PaymentException{

    }
    boolean charge(int amount);
}
