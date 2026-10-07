package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import java.time.LocalDate;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.InventoryItem;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.util.InputUtilities;

public class StoreManagerActions {

    private final InventoryService inventoryService;

    public StoreManagerActions(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void displayMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Store Manager Menu ---");
            System.out.println("1. Display All Items");
            System.out.println("2. Search Product by Name");
            System.out.println("3. Add New Item");
            System.out.println("4. Remove Product");
            System.out.println("5. Back to Main Menu");

            int choice = InputUtilities.readInt("Select option: ");

            switch (choice) {
                case 1 -> viewProducts();
                case 2 -> {
                    String term = InputUtilities.readString("Enter search term: ");
                    inventoryService.searchProductsByName(term).forEach(System.out::println);
                }
                case 3 -> addNewItem();
                // Handles removing products by ID from inventory
                case 4 -> {
                    int id = InputUtilities.readInt("Enter Product ID to remove: ");
                    inventoryService.removeProductById(id);
                    System.out.println("Item removed if existed.");
                }
                case 5 -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private void viewProducts() {
        System.out.println("\n--- Inventory Items ---");
        var items = inventoryService.getAllInventoryItems();
        if (items.isEmpty()) {
            System.out.println("No products available.");
        } else {
            for (var item : items) {
                System.out.println(item);
            }
        }
    }

    // Handles adding new products to inventory
    private void addNewItem() {
        int id = InputUtilities.readInt("Enter ID: ");
        String name = InputUtilities.readString("Enter Name: ");
        String desc = InputUtilities.readString("Enter Description: ");
        double price = InputUtilities.readDouble("Enter Price: ");
        String cat = InputUtilities.readString("Enter Category: ");
        int qty = InputUtilities.readInt("Enter Quantity: ");

        Product product = new Product.Builder()
                .setId(id)
                .setName(name)
                .setDescription(desc)
                .setPrice(price)
                .setCategory(cat)
                .setDateOfManufacture(LocalDate.now())
                .build();

        inventoryService.addInventoryItem(new InventoryItem(product, qty));
        System.out.println("Product added successfully!");
    }
}