package com.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;

import javafx.fxml.FXMLLoader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class RegisterController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        // Kiểm tra thông tin đăng ký
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi nhập liệu", "Tên đăng nhập và mật khẩu không được bỏ trống.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert("Lỗi nhập liệu", "Mật khẩu xác nhận không khớp.");
            return;
        }

        if (isUsernameTaken(username)) {
            showAlert("Lỗi nhập liệu", "Tên đăng nhập đã tồn tại. Vui lòng chọn tên khác.");
            return;
        }

        // Lưu thông tin người dùng vào file theo định dạng username password
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/users.txt", true))) {
            writer.write(username + " " + password);  // Ghi dữ liệu vào file
            writer.newLine();
            showAlert("Thành công", "Đăng ký thành công!");

            returnToLogin();  // Sau khi đăng ký thành công, quay lại trang đăng nhập
            clearFields();
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể lưu thông tin người dùng.");
        }
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isUsernameTaken(String username) {
        // Kiểm tra xem tên người dùng đã tồn tại trong file hay chưa
        try (BufferedReader reader = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\s+");
                if (userData[0].equals(username)) {
                    return true;  // Nếu tìm thấy tên đăng nhập trùng
                }
            }
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể đọc dữ liệu tài khoản.");
        }
        return false;  // Không tìm thấy tên đăng nhập trùng
    }

    private void returnToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            Parent root = loader.load();

            LoginController loginController = loader.getController();
            loginController.setPrimaryStage(primaryStage);  // Truyền Stage

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Đăng Nhập");
            primaryStage.show();
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể tải trang đăng nhập.");
        }
    }
}
