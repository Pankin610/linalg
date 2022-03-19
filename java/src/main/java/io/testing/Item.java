package io.testing;

public class Item {
    private String name;
    private int price;

    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String name() { return name; }
    public int price() { return price; }

    public void setName(String name) { this.name = name; }
    public void setPrice(int price) { this.price = price; }
}
