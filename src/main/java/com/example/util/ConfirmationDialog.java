package com.example.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmationDialog {

    // Hàm xác nhận hành động
    public static boolean confirmAction(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
        ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());

        alert.getButtonTypes().setAll(okButton, cancelButton);

        // Hiển thị cửa sổ và đợi người dùng phản hồi
        alert.showAndWait();

        // Trả về true nếu người dùng chọn OK
        return alert.getResult() == okButton;
    }
}
