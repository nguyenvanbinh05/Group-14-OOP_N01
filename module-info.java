module com.example.StoreManagementApp {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.controller to javafx.fxml; // Mở gói controller
    opens com.example to javafx.fxml; // Mở gói chứa các tệp FXML
}
