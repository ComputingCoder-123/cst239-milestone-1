package edu.gcu.cst239.kabwe.elijah.milestone5.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services.InventoryService;

/**
 * Service responsible for parsing incoming text commands and delegating
 * inventory operations through InventoryService, returning JSON responses.
 */
public class ProductCommandService {
    private final InventoryService inventoryService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ProductCommandService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Processes a command string and returns the resulting JSON response.
     *
     * @param message the raw command string from the client
     * @return JSON-formatted response string
     */
    public String processMessage(String message) {
        if (message == null || message.isBlank()) {
            return error("Command is required");
        }

        String[] parts = message.trim().split("\\s+", 2);
        String command = parts[0].toUpperCase();

        try {
            return switch (command) {
                case "ALL" -> json(inventoryService.getAllInventoryItems());
                case "GET" -> processGet(parts);
                case "FIND" -> json(inventoryService.searchProductsByName(parts.length > 1 ? parts[1] : ""));
                case "DELETE" -> processDelete(parts);
                case "SORT" -> processSort(parts);
                default -> error("Unknown command: " + command);
            };
        } catch (Exception e) {
            return error(e.getMessage());
        }
    }

    private String processGet(String[] parts) {
        requireLength(parts, 2, "Usage: GET id");
        int id = Integer.parseInt(parts[1]);
        var item = inventoryService.getInventoryItemByProductId(id);
        return item != null ? json(item) : "[]";
    }

    private String processDelete(String[] parts) {
        requireLength(parts, 2, "Usage: DELETE id");
        int id = Integer.parseInt(parts[1]);
        inventoryService.removeProductById(id);
        return json(inventoryService.getAllInventoryItems());
    }

    private String processSort(String[] parts) {
        requireLength(parts, 2, "Usage: SORT NAME or SORT PRICE");
        String criteria = parts[1].toUpperCase();
        return switch (criteria) {
            case "NAME" -> json(inventoryService.getInventoryItemsSortedByNameAscending());
            case "PRICE" -> json(inventoryService.getInventoryItemsSortedByPriceAscending());
            default -> error("Invalid sort criteria. Use NAME or PRICE");
        };
    }

    private void requireLength(String[] parts, int expected, String usage) {
        if (parts.length < expected) {
            throw new IllegalArgumentException(usage);
        }
    }

    private String json(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            return error("JSON serialization error");
        }
    }

    private String error(String message) {
        ObjectNode node = objectMapper.createObjectNode();
        node.put("error", message);
        return node.toString();
    }
}
