package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services.CustomerActions;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services.InventoryManager;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services.InventoryService;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services.StoreManagerActions;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.util.InputUtilities;

public class StartupRunner {
// Final Milestone 2 Build: Application testing and setup complete
    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryManager();
        StoreManagerActions managerActions = new StoreManagerActions(inventoryService);
        CustomerActions customerActions = new CustomerActions();
        System.out.println("Welcome to the Storefront Application!");
        boolean running = true;
        while (running) {
            System.out.println("Developer: Elijah Kabwe");
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. Customer Options");
            System.out.println("2. Store Manager Options");
            System.out.println("3. Exit");
            int choice = InputUtilities.readInt("Select role: ");

            switch (choice) {
                case 1 -> customerActions.displayMenu();
                case 2 -> managerActions.displayMenu();
                case 3 -> {
                    running = false;
                    System.out.println("Goodbye!");
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}