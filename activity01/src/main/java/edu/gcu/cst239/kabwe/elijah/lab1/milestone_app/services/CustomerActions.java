package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.util.InputUtilities;

public class CustomerActions {

    public void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View Shopping Cart");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Remove Item from Cart");
            System.out.println("4. Back to Main Menu");

            int choice = InputUtilities.readInt("Select option: ");

            switch (choice) {
                case 1 -> viewCart();
                case 2 -> addToCart();
                case 3 -> removeFromCart();
                case 4 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewCart() {
        System.out.println("\n--- Shopping Cart ---");
        System.out.println("Your cart is currently empty.");
    }

    private void addToCart() {
        int id = InputUtilities.readInt("Enter Product ID to add: ");
        int qty = InputUtilities.readInt("Enter Quantity: ");
        System.out.println("Added " + qty + " of Product ID " + id + " to your cart.");
    }

    // Handles removing items from the customer shopping cart
    private void removeFromCart() {
        int id = InputUtilities.readInt("Enter Product ID to remove from cart: ");
        System.out.println("Removed Product ID " + id + " from your cart.");
    }
}