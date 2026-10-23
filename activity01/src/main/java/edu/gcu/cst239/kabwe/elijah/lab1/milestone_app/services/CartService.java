package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Manages an in-memory shopping cart keyed by product identity.
 */
public class CartService {
    private final Map<Product, Integer> items = new LinkedHashMap<>();

    /**
     * Adds a positive quantity of a product, accumulating quantity when it is already present.
     *
     * @param product the product to add
     * @param quantity the quantity to add
     * @throws NullPointerException if {@code product} is null
     * @throws IllegalArgumentException if {@code quantity} is not positive
     */
    public void addProduct(Product product, int quantity) {
        Product productToAdd = Objects.requireNonNull(product, "product");
        validateQuantity(quantity);
        items.merge(productToAdd, quantity, Integer::sum);
    }

    /**
     * Sets the quantity of a product already in the cart.
     *
     * @param product the product to update
     * @param quantity the positive replacement quantity
     * @throws NullPointerException if {@code product} is null
     * @throws IllegalArgumentException if the quantity is not positive or the product is absent
     */
    public void setQuantity(Product product, int quantity) {
        Product productToUpdate = Objects.requireNonNull(product, "product");
        validateQuantity(quantity);
        Product existing = findProduct(productToUpdate.getId());
        if (existing == null) {
            throw new IllegalArgumentException("Product is not in the cart: " + productToUpdate.getId());
        }
        items.put(existing, quantity);
    }

    /**
     * Removes a product by ID.
     *
     * @param productId the product ID to remove
     * @return {@code true} if an item was removed
     */
    public boolean removeProduct(int productId) {
        Product existing = findProduct(productId);
        return existing != null && items.remove(existing) != null;
    }

    /**
     * Returns an immutable snapshot of the cart contents.
     *
     * @return a product-to-quantity snapshot
     */
    public Map<Product, Integer> getItems() {
        return Collections.unmodifiableMap(new LinkedHashMap<>(items));
    }

    /**
     * Calculates the cart total using current product prices and quantities.
     *
     * @return the total price
     */
    public double getTotal() {
        return items.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    /**
     * Indicates whether the cart has no products.
     *
     * @return {@code true} when the cart is empty
     */
    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Removes all items from the cart.
     */
    public void clear() {
        items.clear();
    }

    private Product findProduct(int productId) {
        return items.keySet().stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElse(null);
    }

    private static void validateQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }
}
