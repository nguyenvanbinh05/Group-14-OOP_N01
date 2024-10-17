package com.example.controller;

import com.example.model.Order;
import com.example.model.Product;
import com.example.util.ConfirmationDialog;
import com.example.util.FileManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.List;
import javafx.scene.control.cell.PropertyValueFactory;



public class OrderController {
    @FXML
    private TableView<Order> orderTable;
    @FXML
    private TableColumn<Order, Integer> orderIdColumn;
    @FXML
    private TableColumn<Order, Integer> orderProductIdColumn;
    @FXML
    private TableColumn<Order, String> orderProductNameColumn;
    @FXML
    private TableColumn<Order, Double> orderProductPriceColumn;
    @FXML
    private TableColumn<Order, Integer> orderProductQuantityColumn;
    @FXML
    private TableColumn<Order, Integer> quantityPurchasedColumn;
    @FXML
    private TableColumn<Order, Double> totalValueColumn;

    @FXML
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productQuantityColumn;

    @FXML
    private TextField orderIdField;
    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productQuantityField;
    @FXML
    private TextField quantityPurchasedField;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        // Khởi tạo cột bảng đơn hàng
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        orderProductIdColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        orderProductNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        orderProductPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        orderProductQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));
        quantityPurchasedColumn.setCellValueFactory(new PropertyValueFactory<>("quantityPurchased"));
        totalValueColumn.setCellValueFactory(new PropertyValueFactory<>("totalValue"));

        // Khởi tạo cột bảng sản phẩm
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));

        // Nạp dữ liệu từ file
        loadOrders(); // Nạp đơn hàng
        loadProducts(); // Nạp sản phẩm



    // Sự kiện khi chọn đơn hàng
        orderTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                orderIdField.setText(String.valueOf(newValue.getOrderId()));
                productIdField.setText(String.valueOf(newValue.getProductId()));
                productNameField.setText(newValue.getProductName());
                productPriceField.setText(String.valueOf(newValue.getPrice()));
                productQuantityField.setText(String.valueOf(newValue.getQuantityInStock()));
                quantityPurchasedField.setText(String.valueOf(newValue.getQuantityPurchased()));
                // Loại bỏ dòng này vì không còn orderDate
            }
        });

        // Sự kiện khi chọn sản phẩm
        productTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productIdField.setText(String.valueOf(newValue.getProductID()));
                productNameField.setText(newValue.getProductName());
                productPriceField.setText(String.valueOf(newValue.getPrice()));
                productQuantityField.setText(String.valueOf(newValue.getQuantityInStock()));
            }
        });
    }

    private void loadOrders() {
        List<Order> orders = FileManager.loadOrders("data/orders.txt");
        orderTable.getItems().addAll(orders); // Thêm đơn hàng vào bảng
    }

    private void loadProducts() {
        List<Product> products = FileManager.loadProducts("data/products.txt");
        productTable.getItems().addAll(products); // Thêm sản phẩm vào bảng
    }

    @FXML
    public void addOrder() {
        if (ConfirmationDialog.confirmAction("Xác Nhận", "Bạn có chắc chắn muốn thêm đơn hàng này không?")) {
            try {
                int orderId = Integer.parseInt(orderIdField.getText());

                for (Order order : orderTable.getItems()) {
                    if (order.getOrderId() == orderId) {
                        showAlert("Lỗi", "Mã đơn hàng đã tồn tại. Vui lòng nhập mã khác.");
                        return;
                    }
                }

                int productId = Integer.parseInt(productIdField.getText());
                String productName = productNameField.getText();
                double productPrice = Double.parseDouble(productPriceField.getText());
                int productQuantity = Integer.parseInt(productQuantityField.getText());
                int quantityPurchased = Integer.parseInt(quantityPurchasedField.getText());

                if (quantityPurchased > productQuantity) {
                    showAlert("Lỗi", "Số lượng mua không được lớn hơn số lượng tồn kho.");
                    return;
                }

                for (Product product : productTable.getItems()) {
                    if (product.getProductID() == productId) {
                        product.setQuantityInStock(product.getQuantityInStock() - quantityPurchased);
                        productTable.refresh();
                        break;
                    }
                }

                Order newOrder = new Order(orderId, productId, productName, productPrice, productQuantity, quantityPurchased);
                orderTable.getItems().add(newOrder);

                saveAllOrders();
                showSuccessAlert("Đơn hàng đã được thêm thành công.");
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Vui lòng nhập đúng định dạng.");
            }
        }
    }

    @FXML
    public void editOrder() {
        if (ConfirmationDialog.confirmAction("Xác Nhận", "Bạn có chắc chắn muốn sửa đơn hàng này không?")) {
            Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                try {
                    int productId = Integer.parseInt(productIdField.getText());
                    int oldQuantityPurchased = selectedOrder.getQuantityPurchased();
                    int newQuantityPurchased = Integer.parseInt(quantityPurchasedField.getText());

                    for (Product product : productTable.getItems()) {
                        if (product.getProductID() == productId) {
                            product.setQuantityInStock(product.getQuantityInStock() + oldQuantityPurchased);

                            if (newQuantityPurchased > product.getQuantityInStock()) {
                                showAlert("Lỗi", "Số lượng mua không được lớn hơn số lượng tồn kho.");
                                return;
                            }

                            product.setQuantityInStock(product.getQuantityInStock() - newQuantityPurchased);
                            productTable.refresh();
                            break;
                        }
                    }

                    selectedOrder.setProductId(productId);
                    selectedOrder.setProductName(productNameField.getText());
                    selectedOrder.setPrice(Double.parseDouble(productPriceField.getText()));
                    selectedOrder.setQuantityInStock(Integer.parseInt(productQuantityField.getText()));
                    selectedOrder.setQuantityPurchased(newQuantityPurchased);
                    selectedOrder.setTotalValue(selectedOrder.getPrice() * newQuantityPurchased);

                    saveAllOrders();
                    orderTable.refresh();
                    showSuccessAlert("Đơn hàng đã được sửa thành công.");
                } catch (NumberFormatException e) {
                    showAlert("Lỗi", "Thông tin nhập không đúng định dạng.");
                }
            } else {
                showAlert("Lỗi", "Vui lòng chọn đơn hàng để sửa.");
            }
        }
    }

    @FXML
    public void deleteOrder() {
        if (ConfirmationDialog.confirmAction("Xác Nhận", "Bạn có chắc chắn muốn xóa đơn hàng này không?")) {
            Order selectedOrder = orderTable.getSelectionModel().getSelectedItem();
            if (selectedOrder != null) {
                orderTable.getItems().remove(selectedOrder);
                saveAllOrders();
                showSuccessAlert("Đơn hàng đã được xóa thành công.");
            } else {
                showAlert("Lỗi", "Vui lòng chọn đơn hàng để xóa.");
            }
        }
    }

    private void saveAllOrders() {
        FileManager.saveOrders(orderTable.getItems(), "data/orders.txt");
        FileManager.saveProducts(productTable.getItems(), "data/products.txt"); // Lưu cả sản phẩm
    }

    @FXML
    public void backToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
            Parent root = loader.load();

            DashboardController dashboardController = loader.getController();
            dashboardController.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Dashboard");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thành Công");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
