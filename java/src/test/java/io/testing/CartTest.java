package io.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class CartTest {
    @Test
    void testIsNewCartEmpty(){
        Cart cart = new Cart();
        assertThat(cart.items()).isEmpty();
    }

    @Test
    void testAddItem(){
        Cart cart = new Cart();
        Item item = new Item("item", 100);
        cart.addItem(item);
        //assertEquals(item, cart.items().get(0));
        assertThat(cart.items()).containsExactly(item);
    }

    @Test
    void testAddManyItem(){
        Cart cart = new Cart();
        Item item1 = new Item("item1", 100);
        Item item2 = new Item("item2", 200);
        Item item3 = new Item("item3", 300);
        cart.addItem(item1);
        cart.addItem(item2);
        cart.addItem(item3);
        assertThat(cart.items()).containsExactly(item1, item2, item3);
    }

    @Test
    void testTotalPrice(){
        Cart cart = new Cart();
        Item item1 = new Item("item1", 100);
        Item item2 = new Item("item2", 200);
        Item item3 = new Item("item3", 300);
        cart.addItem(item1);
        cart.addItem(item2);
        cart.addItem(item3);
        int result = cart.totalPrice();
        assertThat(result).isEqualTo(600);
    }
}