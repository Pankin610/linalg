package io.testing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private List<Item> items = new ArrayList<>();

    public void addItem(Item item) { items.add(item); }
    public void removeItem(Item item) { items.remove(item); }
    public List<Item> items() { return Collections.unmodifiableList(items); }

    public int totalPrice() {
        int result = 0;
        for (Item item : items) result += item.price();
        return result;
    }
}
