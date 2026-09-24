package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models;

public class InventoryItem {
    private Product product;
    private int quantityInStock;

    public InventoryItem(Product product, int quantityInStock) {
        this.product = product;
        this.quantityInStock = Math.max(0, quantityInStock);
    }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
    public int getQuantityInStock() { return quantityInStock; }

    public void increaseQuantity(int amount) {
        if (amount > 0) this.quantityInStock += amount;
    }

    public void decreaseQuantity(int amount) {
        if (amount > 0) this.quantityInStock = Math.max(0, this.quantityInStock - amount);
    }

    @Override
    public String toString() {
        return product + " | Stock: " + quantityInStock;
    }
}