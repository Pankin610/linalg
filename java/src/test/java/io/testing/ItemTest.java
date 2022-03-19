package io.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    //1. przegotowaniu objekta
    //2. wykonianie koda objektu
    //3. sprawdziamy wyniki
    @Test
    void TestItemConstruction(){
        Item item = new Item("item", 100);

        assertEquals("item", item.name());
        assertEquals(100, item.price());
    }

    @Test
    void testSetName(){
        Item item = new Item("item", 100);
        item.setName("other");
        assertEquals("other", item.name());
    }

    @Test
    void testSetPrice(){
        Item item = new Item("item", 100);
        item.setPrice(200);
        assertEquals(200, item.name());
    }
}