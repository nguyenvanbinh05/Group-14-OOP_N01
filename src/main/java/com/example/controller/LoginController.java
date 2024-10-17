package com.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void handleLogin() {
        try {
            if (validateLogin(usernameField.getText(), passwordField.getText())) {
                // Chuyển hướng đến giao diện dashboard sau khi đăng nhập thành công
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Dashboard.fxml"));
                Parent root = loader.load();
                DashboardController dashboardController = loader.getController();
                dashboardController.setPrimaryStage(primaryStage);

                primaryStage.setScene(new Scene(root));
                primaryStage.setTitle("Dashboard");
                primaryStage.show();
            } else {
                showAlert("Đăng Nhập Thất Bại", "Tên đăng nhập hoặc mật khẩu không chính xác.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean validateLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Lỗi", "Tên đăng nhập và mật khẩu không được để trống.");
            return false;
        }

        // Đọc thông tin từ file (data/users.txt) để xác thực
        try (BufferedReader reader = new BufferedReader(new FileReader("data/users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split("\\s+");
                if (userData[0].equals(username) && userData[1].equals(password)) {
                    return true; // Đăng nhập thành công
                }
            }
        } catch (IOException e) {
            showAlert("Lỗi", "Không thể đọc dữ liệu đăng nhập.");
            e.printStackTrace();
        }

        // Nếu không tìm thấy tài khoản hợp lệ
        showAlert("Đăng Nhập Thất Bại", "Tên đăng nhập hoặc mật khẩu không chính xác.");
        return false;
    }

    @FXML
    public void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Register.fxml"));
            Parent root = loader.load();
            RegisterController registerController = loader.getController();
            registerController.setPrimaryStage(primaryStage);

            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Đăng Ký");
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
}
