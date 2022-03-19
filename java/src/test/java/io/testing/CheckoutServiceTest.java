package io.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckoutServiceTest {
    Cart sampleCart(){
        Cart cart = new Cart();
        cart.addItem(new Item("item1", 100));
        cart.addItem(new Item("item2", 200));
        return cart;
    }
    @Test
    void TestCheckoutSuccess(){
        PaymentService ps = mock(PaymentService.class);
        TransactionRecord tr = mock(TransactionRecord.class);
        CheckoutService cs = new CheckoutService(ps, tr);
        when(ps.charge(300)).thenReturn(true);
        boolean result = cs.checkout(sampleCart());
        verify(ps).charge(300);
        verify(tr).logTransaction(300);
        //verifyNoInteractions(ps, tr);
        assertTrue(result, "checkout fails but should success");
    }
    @Test
    void TestCheckoutFailure(){
        PaymentService ps = mock(PaymentService.class);
        TransactionRecord tr = mock(TransactionRecord.class);
        CheckoutService cs = new CheckoutService(ps, tr);
        var exception = new PaymentService().PaymentException;
        doThrow(exception).when(ps).charge(300);        
        assertThrows(expectedType, () -> cs.checkout(sampleCart()));
        verify(ps).charge(300);
        verify(tr).logTransaction(300);
        //verifyNoInteractions(ps, tr);
        assertTrue(result, "checkout fails but should success");
    }
}