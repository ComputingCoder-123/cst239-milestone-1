package edu.gcu.cst239.kabwe.elijah.lab1.milestone_app.models;

import java.time.LocalDate;
import java.util.Objects;

public class Product {
    private final int id;
    private final String name;
    private final String description;
    private final LocalDate dateOfManufacture;
    private final double price;
    private final String category;

    private Product(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.description = builder.description;
        this.dateOfManufacture = builder.dateOfManufacture;
        this.price = builder.price;
        this.category = builder.category;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public LocalDate getDateOfManufacture() { return dateOfManufacture; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name='" + name + '\'' + ", price=$" + price + ", category='" + category + '\'' + '}';
    }

    public static class Builder {
        private int id;
        private String name;
        private String description = "";
        private LocalDate dateOfManufacture;
        private double price;
        private String category = "no-category";

        public Builder setId(int id) { this.id = id; return this; }
        public Builder setName(String name) { this.name = name; return this; }
        public Builder setDescription(String description) { this.description = description; return this; }
        public Builder setDateOfManufacture(LocalDate dateOfManufacture) { this.dateOfManufacture = dateOfManufacture; return this; }
        public Builder setPrice(double price) { this.price = price; return this; }
        public Builder setCategory(String category) { this.category = category; return this; }

        public Product build() {
            if (id <= 0) throw new IllegalArgumentException("ID must be > 0");
            if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be blank");
            if (dateOfManufacture == null) throw new IllegalArgumentException("Date of manufacture required");
            if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
            if (category == null || category.isBlank()) this.category = "no-category";
            return new Product(this);
        }
    }
}