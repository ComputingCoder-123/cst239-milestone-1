package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;

class CartServiceTest {
    private static final double DELTA = 0.001;

    @Test
    void startsEmptyWithZeroTotal() {
        CartService cartService = new CartService();

        assertTrue(cartService.isEmpty());
        assertTrue(cartService.getItems().isEmpty());
        assertEquals(0.0, cartService.getTotal(), DELTA);
    }

    @Test
    void addsAProductWithQuantityAndCalculatesItsTotal() {
        Product product = product(101, "Notebook", 2.75);
        CartService cartService = new CartService();

        cartService.addProduct(product, 4);

        assertEquals(Map.of(product, 4), cartService.getItems());
        assertEquals(11.00, cartService.getTotal(), DELTA);
        assertFalse(cartService.isEmpty());
    }

    @Test
    void addingAnExistingProductAccumulatesItsQuantity() {
        Product product = product(101, "Notebook", 2.75);
        CartService cartService = new CartService();

        cartService.addProduct(product, 2);
        cartService.addProduct(product, 3);

        assertEquals(Map.of(product, 5), cartService.getItems());
        assertEquals(13.75, cartService.getTotal(), DELTA);
    }

    @Test
    void updatesTheQuantityOfAProductAlreadyInTheCart() {
        Product product = product(101, "Notebook", 2.75);
        CartService cartService = new CartService();
        cartService.addProduct(product, 4);

        cartService.setQuantity(product, 2);

        assertEquals(Map.of(product, 2), cartService.getItems());
        assertEquals(5.50, cartService.getTotal(), DELTA);
    }

    @Test
    void rejectsZeroAndNegativeQuantities() {
        Product product = product(101, "Notebook", 2.75);
        CartService cartService = new CartService();

        assertNotNull(assertThrows(IllegalArgumentException.class, () -> cartService.addProduct(product, 0)));
        assertNotNull(assertThrows(IllegalArgumentException.class, () -> cartService.addProduct(product, -1)));
        assertTrue(cartService.isEmpty());
    }

    @Test
    void rejectsInvalidQuantityUpdatesAndUpdatesForProductsOutsideTheCart() {
        Product product = product(101, "Notebook", 2.75);
        Product missingProduct = product(102, "Pen", 1.25);
        CartService cartService = new CartService();
        cartService.addProduct(product, 4);

        assertNotNull(assertThrows(IllegalArgumentException.class, () -> cartService.setQuantity(product, 0)));
        assertNotNull(assertThrows(IllegalArgumentException.class, () -> cartService.setQuantity(product, -1)));
        assertNotNull(assertThrows(IllegalArgumentException.class, () -> cartService.setQuantity(missingProduct, 2)));
        assertEquals(Map.of(product, 4), cartService.getItems());
    }

    @Test
    void removesAnExistingProductAndReportsWhetherItWasPresent() {
        Product product = product(101, "Notebook", 2.75);
        CartService cartService = new CartService();
        cartService.addProduct(product, 4);

        assertTrue(cartService.removeProduct(101));
        assertFalse(cartService.removeProduct(101));
        assertTrue(cartService.isEmpty());
        assertEquals(0.0, cartService.getTotal(), DELTA);
    }

    @Test
    void returnsAnImmutableSnapshotOfCartItems() {
        Product product = product(101, "Notebook", 2.75);
        CartService cartService = new CartService();
        cartService.addProduct(product, 4);
        Map<Product, Integer> snapshot = cartService.getItems();

        assertNotNull(assertThrows(UnsupportedOperationException.class, snapshot::clear));
        assertEquals(Map.of(product, 4), cartService.getItems());
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
