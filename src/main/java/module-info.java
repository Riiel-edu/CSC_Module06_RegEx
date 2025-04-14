module com.example.csc311_module06 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.csc311_module06 to javafx.fxml;
    exports com.example.csc311_module06;
}