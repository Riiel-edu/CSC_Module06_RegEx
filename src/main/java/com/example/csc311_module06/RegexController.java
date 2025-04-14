package com.example.csc311_module06;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RegexController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}