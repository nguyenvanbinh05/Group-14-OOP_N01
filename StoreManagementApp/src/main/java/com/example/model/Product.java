package com.example.model;

public class Product {
    private int productID;
    private String productName;
    private double price;
    private int quantityInStock;

    public Product(int productID, String productName, double price, int quantityInStock) {
        this.productID = productID;
        this.productName = productName;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    // Getter và Setter
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }
}
