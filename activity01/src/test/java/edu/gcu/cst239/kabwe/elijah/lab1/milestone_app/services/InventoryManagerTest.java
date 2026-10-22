package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.InventoryItem;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InventoryManagerTest {

    @Test
    void constructorCopiesTheProvidedInventoryCollection() {
        InventoryItem item = inventoryItem(101, "Test product", "Controlled data", 12.50, 4);
        List<InventoryItem> initialItems = new ArrayList<>(List.of(item));

        InventoryManager inventoryManager = new InventoryManager(initialItems);
        initialItems.clear();

        assertEquals(List.of(item), inventoryManager.getAllInventoryItems());
    }

    @Test
    void returnsEveryControlledInventoryItemInInsertionOrder() {
        InventoryItem first = inventoryItem(101, "Beta", "Second item", 20.00, 2);
        InventoryItem second = inventoryItem(102, "Alpha", "First item", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(first, second);

        assertIterableEquals(List.of(first, second), inventoryManager.getAllInventoryItems());
    }

    @Test
    void findsAnInventoryItemByProductId() {
        InventoryItem expected = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(expected);

        assertEquals(expected, inventoryManager.getInventoryItemByProductId(101));
    }

    @Test
    void returnsNullWhenProductIdDoesNotExist() {
        InventoryManager inventoryManager = inventoryManager(
                inventoryItem(101, "Alpha", "First", 10.00, 5));

        assertNull(inventoryManager.getInventoryItemByProductId(999));
    }

    @Test
    void findsProductsByPartialName() {
        Product expected = product(102, "Wireless Mouse", "Ergonomic device", 18.50);
        InventoryManager inventoryManager = inventoryManager(
                inventoryItem(101, "Keyboard", "Input device", 25.00, 8),
                new InventoryItem(expected, 3));

        assertEquals(List.of(expected), inventoryManager.searchProductsByName("Mouse"));
    }

    @Test
    void findsProductsByPartialDescription() {
        Product expected = product(102, "Desk Lamp", "Adjustable reading light", 32.00);
        InventoryManager inventoryManager = inventoryManager(
                inventoryItem(101, "Notebook", "Ruled paper", 4.00, 12),
                new InventoryItem(expected, 6));

        assertEquals(List.of(expected), inventoryManager.searchProductsByDescription("reading"));
    }

    @Test
    void searchesNamesAndDescriptionsWithoutCaseSensitivity() {
        Product expected = product(101, "Wireless Mouse", "ERgonomic Optical Device", 18.50);
        InventoryManager inventoryManager = inventoryManager(new InventoryItem(expected, 3));

        assertEquals(List.of(expected), inventoryManager.searchProductsByName("wIRELESS"));
        assertEquals(List.of(expected), inventoryManager.searchProductsByDescription("optical"));
    }

    @Test
    void returnsAnEmptyListWhenSearchHasNoMatches() {
        InventoryManager inventoryManager = inventoryManager(
                inventoryItem(101, "Keyboard", "Input device", 25.00, 8));

        assertTrue(inventoryManager.searchProductsByName("missing").isEmpty());
        assertTrue(inventoryManager.searchProductsByDescription("missing").isEmpty());
    }

    @Test
    void rejectsNullSearchTerms() {
        InventoryManager inventoryManager = inventoryManager(
                inventoryItem(101, "Keyboard", "Input device", 25.00, 8));

        assertThrows(NullPointerException.class, () -> inventoryManager.searchProductsByName(null));
        assertThrows(NullPointerException.class, () -> inventoryManager.searchProductsByDescription(null));
    }

    @Test
    void returnsAnImmutableInventorySnapshot() {
        InventoryItem item = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(item);
        List<InventoryItem> snapshot = inventoryManager.getAllInventoryItems();

        assertThrows(UnsupportedOperationException.class, snapshot::clear);
        assertEquals(List.of(item), inventoryManager.getAllInventoryItems());
    }

    private static InventoryManager inventoryManager(InventoryItem... items) {
        return new InventoryManager(List.of(items));
    }

    private static InventoryItem inventoryItem(
            int id, String name, String description, double price, int quantity) {
        return new InventoryItem(product(id, name, description, price), quantity);
    }

    private static Product product(int id, String name, String description, double price) {
        return new Product.Builder()
                .setId(id)
                .setName(name)
                .setDescription(description)
                .setDateOfManufacture(LocalDate.of(2025, 1, 1))
                .setPrice(price)
                .setCategory("test")
                .build();
    }
}
