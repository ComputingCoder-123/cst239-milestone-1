package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.InventoryItem;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutServiceTest {
    @Test
    void successfulCheckoutReducesInventoryAndEmptiesTheCart() {
        Product product = product(101, "Lamp", 12.50);
        InventoryItem inventoryItem = new InventoryItem(product, 6);
        InventoryManager inventory = new InventoryManager(List.of(inventoryItem));
        CartService cart = new CartService();
        cart.addProduct(product, 2);
        CheckoutService checkout = new CheckoutService(inventory, cart);

        boolean completed = checkout.checkout();

        assertTrue(completed);
        assertEquals(4, inventoryItem.getQuantityInStock());
        assertTrue(cart.isEmpty());
    }

    @Test
    void successfulCheckoutCanReduceStockExactlyToZero() {
        Product product = product(101, "Lamp", 12.50);
        InventoryItem inventoryItem = new InventoryItem(product, 2);
        InventoryManager inventory = new InventoryManager(List.of(inventoryItem));
        CartService cart = new CartService();
        cart.addProduct(product, 2);
        CheckoutService checkout = new CheckoutService(inventory, cart);

        assertTrue(checkout.checkout());
        assertEquals(0, inventoryItem.getQuantityInStock());
        assertTrue(cart.isEmpty());
    }

    @Test
    void insufficientStockDoesNotChangeInventoryOrCart() {
        Product product = product(101, "Lamp", 12.50);
        InventoryItem inventoryItem = new InventoryItem(product, 1);
        InventoryManager inventory = new InventoryManager(List.of(inventoryItem));
        CartService cart = new CartService();
        cart.addProduct(product, 2);
        CheckoutService checkout = new CheckoutService(inventory, cart);

        boolean completed = checkout.checkout();

        assertFalse(completed);
        assertEquals(1, inventoryItem.getQuantityInStock());
        assertEquals(2, cart.getItems().get(product));
    }

    @Test
    void shortageOfOneProductPreventsPartialInventoryUpdates() {
        Product firstProduct = product(101, "Lamp", 12.50);
        Product secondProduct = product(102, "Chair", 30.00);
        InventoryItem firstInventoryItem = new InventoryItem(firstProduct, 5);
        InventoryItem secondInventoryItem = new InventoryItem(secondProduct, 1);
        InventoryManager inventory = new InventoryManager(
                List.of(firstInventoryItem, secondInventoryItem));
        CartService cart = new CartService();
        cart.addProduct(firstProduct, 2);
        cart.addProduct(secondProduct, 2);
        CheckoutService checkout = new CheckoutService(inventory, cart);

        boolean completed = checkout.checkout();

        assertFalse(completed);
        assertEquals(5, firstInventoryItem.getQuantityInStock());
        assertEquals(1, secondInventoryItem.getQuantityInStock());
        assertEquals(2, cart.getItems().get(firstProduct));
        assertEquals(2, cart.getItems().get(secondProduct));
    }

    @Test
    void missingInventoryProductPreventsCheckoutWithoutChangingTheCart() {
        Product product = product(101, "Lamp", 12.50);
        InventoryManager inventory = new InventoryManager(List.of());
        CartService cart = new CartService();
        cart.addProduct(product, 1);
        CheckoutService checkout = new CheckoutService(inventory, cart);

        boolean completed = checkout.checkout();

        assertFalse(completed);
        assertTrue(inventory.getAllInventoryItems().isEmpty());
        assertEquals(1, cart.getItems().get(product));
    }

    @Test
    void emptyCartCheckoutReturnsFalseWithoutChangingInventory() {
        InventoryItem inventoryItem = new InventoryItem(product(101, "Lamp", 12.50), 6);
        InventoryManager inventory = new InventoryManager(List.of(inventoryItem));
        CartService cart = new CartService();
        CheckoutService checkout = new CheckoutService(inventory, cart);

        boolean completed = checkout.checkout();

        assertFalse(completed);
        assertEquals(6, inventoryItem.getQuantityInStock());
        assertTrue(cart.isEmpty());
    }

    private static Product product(int id, String name, double price) {
        return new Product.Builder()
                .setId(id)
                .setName(name)
                .setDescription("Controlled test product")
                .setDateOfManufacture(LocalDate.of(2025, 1, 1))
                .setPrice(price)
                .setCategory("test")
                .build();
    }
}
