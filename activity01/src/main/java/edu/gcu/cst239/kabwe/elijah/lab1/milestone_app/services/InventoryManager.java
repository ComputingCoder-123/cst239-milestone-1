package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.InventoryItem;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;

/**
 * In-memory implementation of the store inventory service.
 */
public class InventoryManager implements InventoryService {
    private final List<InventoryItem> inventory = new ArrayList<>();

    /**
     * Creates an inventory manager populated with the application's default products.
     */
    public InventoryManager() {
        this(createDefaultInventory());
    }

    /**
     * Creates an inventory manager with a defensive copy of the supplied items.
     *
     * @param inventoryItems the initial inventory items
     * @throws NullPointerException if {@code inventoryItems} or one of its items is null
     */
    public InventoryManager(Collection<InventoryItem> inventoryItems) {
        inventory.addAll(List.copyOf(Objects.requireNonNull(inventoryItems, "inventoryItems")));
    }

    private static List<InventoryItem> createDefaultInventory() {
        Product p1 = new Product.Builder().setId(1).setName("Gaming Laptop").setDescription("High perf laptop").setPrice(1200.00).setCategory("Electronics").setDateOfManufacture(LocalDate.now()).build();
        Product p2 = new Product.Builder().setId(2).setName("Wireless Mouse").setDescription("Ergonomic optical mouse").setPrice(25.50).setCategory("Electronics").setDateOfManufacture(LocalDate.now()).build();
        Product p3 = new Product.Builder().setId(3).setName("Mechanical Keyboard").setDescription("RGB Backlit switch").setPrice(75.00).setCategory("Electronics").setDateOfManufacture(LocalDate.now()).build();
        return List.of(new InventoryItem(p1, 10), new InventoryItem(p2, 50), new InventoryItem(p3, 20));
    }

    /** {@inheritDoc} */
    @Override
    public List<InventoryItem> getAllInventoryItems() {
        return List.copyOf(inventory); 
    }

    /** {@inheritDoc} */
    @Override
    public InventoryItem getInventoryItemByProductId(int id) {
        return inventory.stream()
                .filter(i -> i.getProduct().getId() == id)
                .findFirst()
                .orElse(null);
    }

    /** {@inheritDoc} */
    @Override
    public List<Product> searchProductsByName(String term) {
        String searchTerm = Objects.requireNonNull(term, "term").toLowerCase(Locale.ROOT);
        return inventory.stream()
                .filter(i -> i.getProduct().getName().toLowerCase(Locale.ROOT).contains(searchTerm))
                .map(InventoryItem::getProduct)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public List<Product> searchProductsByDescription(String term) {
        String searchTerm = Objects.requireNonNull(term, "term").toLowerCase(Locale.ROOT);
        return inventory.stream()
                .filter(i -> i.getProduct().getDescription().toLowerCase(Locale.ROOT).contains(searchTerm))
                .map(InventoryItem::getProduct)
                .collect(Collectors.toList());
    }

    /** {@inheritDoc} */
    @Override
    public void addInventoryItem(InventoryItem item) {
        InventoryItem itemToAdd = Objects.requireNonNull(item, "item");
        if (getInventoryItemByProductId(itemToAdd.getProduct().getId()) != null) {
            throw new IllegalArgumentException("Product ID already exists: " + itemToAdd.getProduct().getId());
        }
        inventory.add(itemToAdd);
    }

    /** {@inheritDoc} */
    @Override
    public void updateProduct(Product product) {
        Objects.requireNonNull(product, "product");
        InventoryItem existing = getInventoryItemByProductId(product.getId());
        if (existing != null) {
            existing.setProduct(product);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void updateQuantity(int productId, int newQuantity) {
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        InventoryItem item = getInventoryItemByProductId(productId);
        if (item != null) {
            int adjustment = newQuantity - item.getQuantityInStock();
            if (adjustment > 0) {
                item.increaseQuantity(adjustment);
            } else {
                item.decreaseQuantity(-adjustment);
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void removeProductById(int productId) {
        inventory.removeIf(i -> i.getProduct().getId() == productId);
    }

    /** {@inheritDoc} */
    @Override
    public List<InventoryItem> getInventoryItemsSortedByNameAscending() {
        return inventory.stream()
                .sorted(Comparator.comparing(item -> item.getProduct().getName(), String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<InventoryItem> getInventoryItemsSortedByPriceAscending() {
        return inventory.stream()
                .sorted(Comparator.comparingDouble(item -> item.getProduct().getPrice()))
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<InventoryItem> getInventoryItemsSortedByNameDescending() {
        return inventory.stream()
                .sorted(Comparator.comparing(
                        (InventoryItem item) -> item.getProduct().getName(),
                        String.CASE_INSENSITIVE_ORDER).reversed())
                .toList();
    }
}