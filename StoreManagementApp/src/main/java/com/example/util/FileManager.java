package com.example.util;

import com.example.model.Product;
import com.example.model.Order;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    // Ghi danh sách sản phẩm vào file
    public static void saveProducts(List<Product> products, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Product product : products) {
                writer.write(product.getProductID() + "," +
                        product.getProductName() + "," +
                        product.getPrice() + "," +
                        product.getQuantityInStock());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Product> loadProducts(String filePath) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(",");
                int productId = Integer.parseInt(productData[0]);
                String productName = productData[1];
                double price = Double.parseDouble(productData[2]);
                int quantityInStock = Integer.parseInt(productData[3]);

                // Tạo đtg Product và thêm vào danh sách
                Product product = new Product(productId, productName, price, quantityInStock);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Phương thức lưu danh sách đơn hàng vào file
    public static void saveOrders(List<Order> orders, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Order order : orders) {
                writer.write(order.getOrderId() + "," +
                        order.getProductId() + "," +
                        order.getProductName() + "," +
                        order.getPrice() + "," +
                        order.getQuantityInStock() + "," +
                        order.getQuantityPurchased() + "," +  // Thêm dấu phẩy cho trường tổng giá trị
                        order.getTotalValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Phương thức đọc danh sách đơn hàng từ file
    public static List<Order> loadOrders(String filePath) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] orderData = line.split(",");
                if (orderData.length == 7) { // Kiểm tra số lượng trường
                    int orderId = Integer.parseInt(orderData[0]);
                    int productId = Integer.parseInt(orderData[1]);
                    String productName = orderData[2];
                    double price = Double.parseDouble(orderData[3]);
                    int quantityInStock = Integer.parseInt(orderData[4]);
                    int quantityPurchased = Integer.parseInt(orderData[5]);
                    double totalValue = Double.parseDouble(orderData[6]);

                    // Tạo đối tượng Order và thêm vào danh sách
                    Order order = new Order(orderId, productId, productName, price, quantityInStock, quantityPurchased, totalValue);
                    orders.add(order);
                } else {
                    System.out.println("Dữ liệu không hợp lệ: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng số: " + e.getMessage());
        }
        return orders;
    }
}
