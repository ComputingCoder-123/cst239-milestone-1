package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.InventoryItem;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;

public class InventoryManager implements InventoryService {
    // Logic for handling inventory file persistence and data loading
    private final List<InventoryItem> inventory = new ArrayList<>();

    public InventoryManager() {
        // Seed initial products
        Product p1 = new Product.Builder().setId(1).setName("Gaming Laptop").setDescription("High perf laptop").setPrice(1200.00).setCategory("Electronics").setDateOfManufacture(LocalDate.now()).build();
        Product p2 = new Product.Builder().setId(2).setName("Wireless Mouse").setDescription("Ergonomic optical mouse").setPrice(25.50).setCategory("Electronics").setDateOfManufacture(LocalDate.now()).build();
        Product p3 = new Product.Builder().setId(3).setName("Mechanical Keyboard").setDescription("RGB Backlit switch").setPrice(75.00).setCategory("Electronics").setDateOfManufacture(LocalDate.now()).build();
        
        inventory.add(new InventoryItem(p1, 10));
        inventory.add(new InventoryItem(p2, 50));
        inventory.add(new InventoryItem(p3, 20));
    }

    @Override
    public List<InventoryItem> getAllInventoryItems() { 
        return List.copyOf(inventory); 
    }

    @Override
    public InventoryItem getInventoryItemByProductId(int id) {
        return inventory.stream()
                .filter(i -> i.getProduct().getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> searchProductsByName(String term) {
        return inventory.stream()
                .filter(i -> i.getProduct().getName().toLowerCase().contains(term.toLowerCase()))
                .map(InventoryItem::getProduct)
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> searchProductsByDescription(String term) {
        return inventory.stream()
                .filter(i -> i.getProduct().getDescription().toLowerCase().contains(term.toLowerCase()))
                .map(InventoryItem::getProduct)
                .collect(Collectors.toList());
    }

    @Override
    public void addInventoryItem(InventoryItem item) { 
        inventory.add(item); 
    }

    @Override
    public void updateProduct(Product product) {
        InventoryItem existing = getInventoryItemByProductId(product.getId());
        if (existing != null) {
            existing.setProduct(product);
        }
    }

    @Override
    public void updateQuantity(int productId, int newQuantity) {
        InventoryItem item = getInventoryItemByProductId(productId);
        if (item != null) {
            item.increaseQuantity(newQuantity - item.getQuantityInStock());
        }
    }

    @Override
    public void removeProductById(int productId) {
        inventory.removeIf(i -> i.getProduct().getId() == productId);
    }
}