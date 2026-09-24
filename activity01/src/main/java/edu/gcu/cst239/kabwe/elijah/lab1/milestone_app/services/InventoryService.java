package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.services;

import java.util.List;

import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.InventoryItem;
import edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models.Product;

public interface InventoryService {
    List getAllInventoryItems();
    InventoryItem getInventoryItemByProductId(int id);
    List searchProductsByName(String term);
    List searchProductsByDescription(String term);
    void addInventoryItem(InventoryItem item);
    void updateProduct(Product product);
    void updateQuantity(int productId, int newQuantity);
    void removeProductById(int productId);
}
    

