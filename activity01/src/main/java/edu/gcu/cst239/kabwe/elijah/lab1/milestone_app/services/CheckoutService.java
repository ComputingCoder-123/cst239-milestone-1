package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.InventoryItem;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;

import java.util.Map;
import java.util.Objects;

/**
 * Coordinates checkout between a cart and the inventory service.
 */
public class CheckoutService {
    private final InventoryService inventoryService;
    private final CartService cartService;

    /**
     * Creates a checkout service backed by the supplied services.
     *
     * @param inventoryService the inventory service
     * @param cartService the cart service
     * @throws NullPointerException if either service is null
     */
    public CheckoutService(InventoryService inventoryService, CartService cartService) {
        this.inventoryService = Objects.requireNonNull(inventoryService, "inventoryService");
        this.cartService = Objects.requireNonNull(cartService, "cartService");
    }

    /**
     * Completes checkout only when every cart item is available in sufficient quantity.
     *
     * @return {@code true} when the cart was purchased; otherwise {@code false}
     */
    public boolean checkout() {
        Map<Product, Integer> cartItems = cartService.getItems();
        if (cartItems.isEmpty()) {
            return false;
        }

        for (Map.Entry<Product, Integer> cartEntry : cartItems.entrySet()) {
            InventoryItem inventoryItem = inventoryService.getInventoryItemByProductId(
                    cartEntry.getKey().getId());
            if (inventoryItem == null || inventoryItem.getQuantityInStock() < cartEntry.getValue()) {
                return false;
            }
        }

        for (Map.Entry<Product, Integer> cartEntry : cartItems.entrySet()) {
            InventoryItem inventoryItem = inventoryService.getInventoryItemByProductId(
                    cartEntry.getKey().getId());
            inventoryService.updateQuantity(
                    cartEntry.getKey().getId(),
                    inventoryItem.getQuantityInStock() - cartEntry.getValue());
        }

        cartService.clear();
        return true;
    }
}
