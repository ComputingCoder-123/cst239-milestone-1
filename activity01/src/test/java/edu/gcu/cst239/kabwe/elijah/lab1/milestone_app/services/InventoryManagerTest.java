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
    private static final double DELTA = 0.001;

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

    @Test
    void addsANewInventoryItem() {
        InventoryManager inventoryManager = inventoryManager(
                inventoryItem(101, "Alpha", "First", 10.00, 5));
        InventoryItem added = inventoryItem(102, "Beta", "Second", 20.00, 2);

        inventoryManager.addInventoryItem(added);

        assertEquals(List.of(101, 102), productIds(inventoryManager.getAllInventoryItems()));
        assertEquals(added, inventoryManager.getInventoryItemByProductId(102));
    }

    @Test
    void rejectsAnInventoryItemWithADuplicateProductId() {
        InventoryItem existing = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(existing);
        InventoryItem duplicate = inventoryItem(101, "Different", "Duplicate ID", 50.00, 9);

        assertThrows(IllegalArgumentException.class, () -> inventoryManager.addInventoryItem(duplicate));
        assertEquals(List.of(existing), inventoryManager.getAllInventoryItems());
    }

    @Test
    void rejectsANullInventoryItem() {
        InventoryManager inventoryManager = inventoryManager();

        assertThrows(NullPointerException.class, () -> inventoryManager.addInventoryItem(null));
        assertTrue(inventoryManager.getAllInventoryItems().isEmpty());
    }

    @Test
    void updatesProductDetailsWithoutChangingStockQuantity() {
        InventoryItem item = inventoryItem(101, "Old name", "Old description", 10.00, 7);
        InventoryManager inventoryManager = inventoryManager(item);
        Product replacement = product(101, "New name", "New description", 13.25);

        inventoryManager.updateProduct(replacement);

        assertEquals(replacement, item.getProduct());
        assertEquals(7, item.getQuantityInStock());
        assertEquals(13.25, item.getProduct().getPrice(), DELTA);
    }

    @Test
    void updatingAnUnknownProductLeavesInventoryUnchanged() {
        InventoryItem existing = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(existing);

        inventoryManager.updateProduct(product(999, "Missing", "Not stored", 20.00));

        assertEquals(List.of(existing), inventoryManager.getAllInventoryItems());
        assertEquals(5, existing.getQuantityInStock());
    }

    @Test
    void setsStockToTheRequestedQuantityAtZeroAndBothSidesOfTheCurrentValue() {
        InventoryItem item = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(item);

        inventoryManager.updateQuantity(101, 8);
        assertEquals(8, item.getQuantityInStock());

        inventoryManager.updateQuantity(101, 3);
        assertEquals(3, item.getQuantityInStock());

        inventoryManager.updateQuantity(101, 0);
        assertEquals(0, item.getQuantityInStock());
    }

    @Test
    void updatingStockForAnUnknownProductLeavesInventoryUnchanged() {
        InventoryItem existing = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(existing);

        inventoryManager.updateQuantity(999, 2);

        assertEquals(List.of(existing), inventoryManager.getAllInventoryItems());
        assertEquals(5, existing.getQuantityInStock());
    }

    @Test
    void rejectsNegativeStockQuantitiesWithoutChangingInventory() {
        InventoryItem item = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(item);

        assertThrows(IllegalArgumentException.class, () -> inventoryManager.updateQuantity(101, -1));
        assertEquals(5, item.getQuantityInStock());
    }

    @Test
    void removesAnExistingProductById() {
        InventoryItem removed = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryItem retained = inventoryItem(102, "Beta", "Second", 20.00, 2);
        InventoryManager inventoryManager = inventoryManager(removed, retained);

        inventoryManager.removeProductById(101);

        assertEquals(List.of(retained), inventoryManager.getAllInventoryItems());
        assertNull(inventoryManager.getInventoryItemByProductId(101));
    }

    @Test
    void removingAnUnknownProductDoesNotDamageInventory() {
        InventoryItem retained = inventoryItem(101, "Alpha", "First", 10.00, 5);
        InventoryManager inventoryManager = inventoryManager(retained);

        inventoryManager.removeProductById(999);

        assertEquals(List.of(retained), inventoryManager.getAllInventoryItems());
    }

    @Test
    void sortsAnIndependentViewByNameAscending() {
        InventoryItem charlie = inventoryItem(103, "Charlie", "Third", 30.00, 3);
        InventoryItem alpha = inventoryItem(101, "Alpha", "First", 10.00, 1);
        InventoryItem bravo = inventoryItem(102, "Bravo", "Second", 20.00, 2);
        InventoryManager inventoryManager = inventoryManager(charlie, alpha, bravo);

        assertEquals(
                List.of(101, 102, 103),
                productIds(inventoryManager.getInventoryItemsSortedByNameAscending()));
    }

    @Test
    void sortsAnIndependentViewByPriceAscending() {
        InventoryItem expensive = inventoryItem(103, "Expensive", "Third", 30.00, 3);
        InventoryItem cheapest = inventoryItem(101, "Cheapest", "First", 10.00, 1);
        InventoryItem middle = inventoryItem(102, "Middle", "Second", 20.00, 2);
        InventoryManager inventoryManager = inventoryManager(expensive, cheapest, middle);

        assertEquals(
                List.of(101, 102, 103),
                productIds(inventoryManager.getInventoryItemsSortedByPriceAscending()));
    }

    @Test
    void sortsAnIndependentViewByNameDescending() {
        InventoryItem bravo = inventoryItem(102, "Bravo", "Second", 20.00, 2);
        InventoryItem alpha = inventoryItem(101, "Alpha", "First", 10.00, 1);
        InventoryItem charlie = inventoryItem(103, "Charlie", "Third", 30.00, 3);
        InventoryManager inventoryManager = inventoryManager(bravo, alpha, charlie);

        assertEquals(
                List.of(103, 102, 101),
                productIds(inventoryManager.getInventoryItemsSortedByNameDescending()));
    }

    @Test
    void sortingDoesNotReorderOrExposeTheStoredInventory() {
        InventoryItem bravo = inventoryItem(102, "Bravo", "Second", 20.00, 2);
        InventoryItem alpha = inventoryItem(101, "Alpha", "First", 10.00, 1);
        InventoryManager inventoryManager = inventoryManager(bravo, alpha);

        List<InventoryItem> sorted = inventoryManager.getInventoryItemsSortedByNameAscending();

        assertEquals(List.of(102, 101), productIds(inventoryManager.getAllInventoryItems()));
        assertThrows(UnsupportedOperationException.class, sorted::clear);
        assertEquals(List.of(102, 101), productIds(inventoryManager.getAllInventoryItems()));
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

    private static List<Integer> productIds(List<InventoryItem> items) {
        return items.stream().map(item -> item.getProduct().getId()).toList();
    }
}
