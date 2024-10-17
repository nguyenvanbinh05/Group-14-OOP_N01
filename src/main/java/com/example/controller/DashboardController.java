package com.example.controller;

import com.example.model.Product;
import com.example.util.FileManager;
import com.example.util.ConfirmationDialog;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.List;

public class DashboardController {
    @FXML
    private TableView<Product> productTable;

    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableColumn<Product, Integer> productQuantityColumn; // Cột số lượng

    @FXML
    private TextField productIdField;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField productPriceField;
    @FXML
    private TextField productQuantityField;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void initialize() {
        // Khởi tạo bảng sản phẩm
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantityInStock"));

        loadProducts();

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

    private void loadProducts() {
        String filePath = "data/products.txt";
        List<Product> products = FileManager.loadProducts(filePath);
        productTable.getItems().addAll(products); // Thêm sản phẩm vào bảng
    }

    @FXML
    public void addProduct() {
        if (ConfirmationDialog.confirmAction("Xác Nhận", "Bạn có chắc chắn muốn thêm sản phẩm này không?")) {
            try {
                int productId = Integer.parseInt(productIdField.getText());
                String productName = productNameField.getText();
                double productPrice = Double.parseDouble(productPriceField.getText());
                int productQuantity = Integer.parseInt(productQuantityField.getText());

                // Kiểm tra số lượng tồn kho không được âm
                if (productQuantity < 0) {
                    showAlert("Lỗi", "Số lượng tồn kho không được âm.");
                    return;
                }

                // Kiểm tra xem mã sản phẩm đã tồn tại chưa
                for (Product product : productTable.getItems()) {
                    if (product.getProductID() == productId) {
                        showAlert("Lỗi", "Mã sản phẩm đã tồn tại. Vui lòng nhập mã khác.");
                        return;
                    }
                }

                // Thêm sản phẩm vào bảng
                Product newProduct = new Product(productId, productName, productPrice, productQuantity);
                productTable.getItems().add(newProduct);

                // Lưu sản phẩm vào file
                saveAllProducts();
                showSuccessAlert("Sản phẩm đã được thêm thành công.");
            } catch (NumberFormatException e) {
                showAlert("Lỗi", "Vui lòng nhập đúng định dạng.");
            }
        }
    }


    @FXML
    public void editProduct() {
        if (ConfirmationDialog.confirmAction("Xác Nhận", "Bạn có chắc chắn muốn sửa sản phẩm này không?")) {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                try {
                    int productId = Integer.parseInt(productIdField.getText());
                    String productName = productNameField.getText();
                    double productPrice = Double.parseDouble(productPriceField.getText());
                    int productQuantity = Integer.parseInt(productQuantityField.getText());

                    // Kiểm tra số lượng tồn kho không được âm
                    if (productQuantity < 0) {
                        showAlert("Lỗi", "Số lượng tồn kho không được âm.");
                        return;
                    }

                    // Kiểm tra xem mã sản phẩm mới có bị trùng không
                    for (Product product : productTable.getItems()) {
                        if (product.getProductID() == productId && product != selectedProduct) {
                            showAlert("Lỗi", "Mã sản phẩm đã tồn tại. Vui lòng nhập mã khác.");
                            return;
                        }
                    }

                    // Cập nhật thông tin sản phẩm
                    selectedProduct.setProductID(productId);
                    selectedProduct.setProductName(productName);
                    selectedProduct.setPrice(productPrice);
                    selectedProduct.setQuantityInStock(productQuantity);

                    // Lưu sản phẩm vào file
                    saveAllProducts();
                    productTable.refresh();
                    showSuccessAlert("Sản phẩm đã được sửa thành công.");
                } catch (NumberFormatException e) {
                    showAlert("Lỗi", "Thông tin nhập không đúng định dạng.");
                }
            } else {
                showAlert("Lỗi", "Vui lòng chọn sản phẩm để sửa.");
            }
        }
    }

    private void saveAllProducts() {
        FileManager.saveProducts(productTable.getItems(), "data/products.txt");
    }

    @FXML
    public void deleteProduct() {
        if (ConfirmationDialog.confirmAction("Xác Nhận", "Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
            Product selectedProduct = productTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                productTable.getItems().remove(selectedProduct);
                saveAllProducts();
                showSuccessAlert("Sản phẩm đã được xóa thành công.");
            } else {
                showAlert("Lỗi", "Vui lòng chọn sản phẩm để xóa.");
            }
        }
    }

    @FXML
    public void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);

            // Đặt lại scene cho màn hình đăng nhập
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Đăng Nhập");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void goToOrderManagement() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Order.fxml"));
            Parent root = loader.load();

            // Lấy OrderController và truyền primaryStage
            OrderController orderController = loader.getController();
            orderController.setPrimaryStage(primaryStage);

            // Chuyển cảnh sang giao diện quản lý đơn hàng
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Quản Lý Đơn Hàng");
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
