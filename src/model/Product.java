package model;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id; this.name = name; this.quantity = quantity; this.price = price;
    }
    public Product(String name, int quantity, double price) {
        this(0, name, quantity, price);
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public void setQuantity(int q) { this.quantity = q; }
    public void setPrice(double p) { this.price = p; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + quantity + " | " + price;
    }
}
