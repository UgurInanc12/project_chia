module com.example.project_chia {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.project_chia to javafx.fxml;
    exports com.example.project_chia;
}