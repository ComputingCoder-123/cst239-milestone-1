package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import java.util.List;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.InventoryItem;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;

/**
 * Defines read and write operations for store inventory.
 */
public interface InventoryService {
    /**
     * Returns a snapshot of all inventory items.
     *
     * @return the inventory snapshot
     */
    List<InventoryItem> getAllInventoryItems();

    /**
     * Finds an inventory item by product ID.
     *
     * @param id the product ID
     * @return the matching item, or {@code null} when no item has that ID
     */
    InventoryItem getInventoryItemByProductId(int id);

    /**
     * Finds products whose names contain the supplied term, ignoring case.
     *
     * @param term the name fragment to search for
     * @return matching products in inventory order
     */
    List<Product> searchProductsByName(String term);

    /**
     * Finds products whose descriptions contain the supplied term, ignoring case.
     *
     * @param term the description fragment to search for
     * @return matching products in inventory order
     */
    List<Product> searchProductsByDescription(String term);

    /**
     * Adds an item to inventory.
     *
     * @param item the item to add
     * @throws NullPointerException if {@code item} is null
     * @throws IllegalArgumentException if its product ID is already present
     */
    void addInventoryItem(InventoryItem item);

    /**
     * Replaces the product details for an existing ID without changing its stock.
     *
     * @param product replacement product details
     */
    void updateProduct(Product product);

    /**
     * Sets the stock for an existing product ID.
     *
     * @param productId the product ID
     * @param newQuantity the new nonnegative stock quantity
     * @throws IllegalArgumentException if {@code newQuantity} is negative
     */
    void updateQuantity(int productId, int newQuantity);

    /**
     * Removes a product from inventory when its ID is present.
     *
     * @param productId the product ID to remove
     */
    void removeProductById(int productId);

    /**
     * Returns an independent inventory view sorted by product name ascending.
     *
     * @return the sorted inventory view
     */
    List<InventoryItem> getInventoryItemsSortedByNameAscending();

    /**
     * Returns an independent inventory view sorted by product price ascending.
     *
     * @return the sorted inventory view
     */
    List<InventoryItem> getInventoryItemsSortedByPriceAscending();

    /**
     * Returns an independent inventory view sorted by product name descending.
     *
     * @return the sorted inventory view
     */
    List<InventoryItem> getInventoryItemsSortedByNameDescending();
}
    
