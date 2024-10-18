package com.example.model;

public class Order {
    private int orderId;
    private int productId;
    private String productName;
    private double price;
    private int quantityInStock;
    private int quantityPurchased;
    private double totalValue;


    public Order(int orderId, int productId, String productName, double price, int quantityInStock, int quantityPurchased, double totalValue) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.quantityPurchased = quantityPurchased;
        this.totalValue = totalValue;
    }


    public Order(int orderId, int productId, String productName, double price, int quantityInStock, int quantityPurchased) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.quantityPurchased = quantityPurchased;
        this.totalValue = price * quantityPurchased;
    }

    // Getter và Setter cho totalValue
    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
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
        this.totalValue = price * quantityPurchased;
    }

    public int getQuantityInStock() {
        return quantityInStock;
    }

    public void setQuantityInStock(int quantityInStock) {
        this.quantityInStock = quantityInStock;
    }

    public int getQuantityPurchased() {
        return quantityPurchased;
    }

    public void setQuantityPurchased(int quantityPurchased) {
        this.quantityPurchased = quantityPurchased;
        this.totalValue = price * quantityPurchased;
    }
}
