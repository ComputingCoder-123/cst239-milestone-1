package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.util.InputUtilities;

public class CustomerActions {

    public void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Customer Menu ---");
            System.out.println("1. View Shopping Cart");
            System.out.println("2. Add Item to Cart");
            System.out.println("3. Back to Main Menu");

            int choice = InputUtilities.readInt("Select option: ");

            switch (choice) {
                case 1 -> viewCart();
                case 2 -> addToCart();
                case 3 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewCart() {
        System.out.println("\n--- Shopping Cart ---");
        System.out.println("Your cart is currently empty.");
    }

    // Handles adding product items to the customer cart
    private void addToCart() {
        int id = InputUtilities.readInt("Enter Product ID to add: ");
        int qty = InputUtilities.readInt("Enter Quantity: ");
        System.out.println("Added " + qty + " of Product ID " + id + " to your cart.");
    }
}