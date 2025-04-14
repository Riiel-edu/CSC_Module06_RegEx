package com.example.csc311_module06;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexApplication extends Application {

    static boolean checkFName, checkLName, checkDate, checkEmail, checkZip = false;

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane root = new AnchorPane();
        FXMLLoader fxmlLoader = new FXMLLoader(RegexApplication.class.getResource("register-view.fxml"));
        root.getChildren().add(fxmlLoader.load());
        registerSetup(root, stage);

        Scene scene = new Scene(root, 500, 650);
        stage.setTitle("Register Here!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }


    /**
     * Sets up the interactive fields of the register page.
     * @param pane The AnchorPane that the register page is on.
     * @param stage The Stage the register page is set on.
     */
    public static void registerSetup(AnchorPane pane, Stage stage) {

        Button signUp = new Button();
        signUp.setLayoutX(70); signUp.setLayoutY(495); signUp.setPrefWidth(360); signUp.setPrefHeight(45);
        signUp.setDisable(true);
        signUp.setText("Register");
        pane.getChildren().add(signUp);
        signUp.setOnAction(e-> {
            FXMLLoader fxmlLoader = new FXMLLoader(RegexApplication.class.getResource("landing-page.fxml"));

            try {
                Scene scene = new Scene(fxmlLoader.load(), 600, 450);
                stage.setScene(scene);
                stage.setResizable(false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        TextField firstNameField = new TextField();
        firstNameField.setLayoutX(70); firstNameField.setLayoutY(120); firstNameField.setPrefWidth(360); firstNameField.setPrefHeight(30);
        firstNameField.setPromptText("FIRST-NAME");
        pane.getChildren().add(firstNameField);

        Label nameErrorLabel = new Label();
        nameErrorLabel.setLayoutX(70); nameErrorLabel.setLayoutY(150);
        nameErrorLabel.setText("Minimum of 2 characters and maximum of 25 characters");
        nameErrorLabel.setTextFill(Color.RED);
        pane.getChildren().add(nameErrorLabel);
        nameErrorLabel.setOpacity(0);
        firstNameField.focusedProperty().addListener(e -> {
            if(!checkNameConstraint(firstNameField.getText())) {
                checkFName = false;
                nameErrorLabel.setOpacity(1);
            } else {
                checkFName = true;
                nameErrorLabel.setOpacity(0);
            }

            signUp.setDisable(!(checkFName && checkLName && checkDate && checkEmail && checkZip));
        });

        TextField lastNameField = new TextField();
        lastNameField.setLayoutX(70); lastNameField.setLayoutY(195); lastNameField.setPrefWidth(360); lastNameField.setPrefHeight(30);
        lastNameField.setPromptText("LAST-NAME");
        pane.getChildren().add(lastNameField);

        Label lastNameErrorLabel = new Label();
        lastNameErrorLabel.setLayoutX(70); lastNameErrorLabel.setLayoutY(225);
        lastNameErrorLabel.setText("Minimum of 2 characters and maximum of 25 characters");
        lastNameErrorLabel.setTextFill(Color.RED);
        pane.getChildren().add(lastNameErrorLabel);
        lastNameErrorLabel.setOpacity(0);
        lastNameField.focusedProperty().addListener(e -> {
            if(!checkNameConstraint(lastNameField.getText())) {
                checkLName = false;
                lastNameErrorLabel.setOpacity(1);
            } else {
                checkLName = true;
                lastNameErrorLabel.setOpacity(0);
            }

            signUp.setDisable(!(checkFName && checkLName && checkDate && checkEmail && checkZip));
        });

        TextField dateField = new TextField();
        dateField.setLayoutX(70); dateField.setLayoutY(270); dateField.setPrefWidth(360); dateField.setPrefHeight(30);
        dateField.setPromptText("DATE-OF-BIRTH");
        pane.getChildren().add(dateField);

        Label dateLabel = new Label();
        dateLabel.setLayoutX(70); dateLabel.setLayoutY(300);
        dateLabel.setText("Please use MM/DD/YYYY format");
        dateLabel.setTextFill(Color.RED);
        pane.getChildren().add(dateLabel);
        dateLabel.setOpacity(0);
        dateField.focusedProperty().addListener(e -> {
            if(!checkDateConstraint(dateField.getText())) {
                checkDate = false;
                dateLabel.setOpacity(1);
            } else {
                checkDate = true;
                dateLabel.setOpacity(0);
            }

            signUp.setDisable(!(checkFName && checkLName && checkDate && checkEmail && checkZip));
        });

        TextField emailField = new TextField();
        emailField.setLayoutX(70); emailField.setLayoutY(345); emailField.setPrefWidth(360); emailField.setPrefHeight(30);
        emailField.setPromptText("EMAIL");
        pane.getChildren().add(emailField);

        Label emailLabel = new Label();
        emailLabel.setLayoutX(70); emailLabel.setLayoutY(375);
        emailLabel.setText("Only Farmingdale valid email addresses are accepted");
        emailLabel.setTextFill(Color.RED);
        pane.getChildren().add(emailLabel);
        emailLabel.setOpacity(0);
        emailField.focusedProperty().addListener(e -> {
            if(!checkEmailConstraint(emailField.getText())) {
                checkEmail = false;
                emailLabel.setOpacity(1);
            } else {
                checkEmail = true;
                emailLabel.setOpacity(0);
            }

            signUp.setDisable(!(checkFName && checkLName && checkDate && checkEmail && checkZip));
        });

        TextField zipField = new TextField();
        zipField.setLayoutX(70); zipField.setLayoutY(420); zipField.setPrefWidth(360); zipField.setPrefHeight(30);
        zipField.setPromptText("ZIP-CODE");
        pane.getChildren().add(zipField);

        Label zipLabel = new Label();
        zipLabel.setLayoutX(70); zipLabel.setLayoutY(450);
        zipLabel.setText("Must be a 5 digit number");
        zipLabel.setTextFill(Color.RED);
        pane.getChildren().add(zipLabel);
        zipLabel.setOpacity(0);
        zipField.focusedProperty().addListener(e -> {
            if(!checkZipConstraint(zipField.getText())) {
                checkZip = false;
                zipLabel.setOpacity(1);
            } else {
                checkZip = true;
                zipLabel.setOpacity(0);
            }

            signUp.setDisable(!(checkFName && checkLName && checkDate && checkEmail && checkZip));
        });
    }

    /**
     * Checks whether the Name constraint "[a-zA-Z]{2,25}" is met.
     * @param text The text being checked.
     * @return True if the text matches the constraint, otherwise false.
     */
    public static boolean checkNameConstraint(String text) {
        String pattern = "[a-zA-Z]{2,25}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * Checks whether the Name constraint "[01][0-9]/[0123][0-9]/[0-9]{4}" is met.
     * @param text The text being checked.
     * @return True if the text matches the constraint, otherwise false.
     */
    public static boolean checkDateConstraint(String text) {
        String pattern = "[01][0-9]/[0123][0-9]/[0-9]{4}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * Checks whether the Email constraint "\\w+@farmingdale.edu" is met.
     * @param text The text being checked.
     * @return True if the text matches the constraint, otherwise false.
     */
    public static boolean checkEmailConstraint(String text) {
        String pattern = "\\w+@farmingdale.edu";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * Checks whether the ZIP constraint "[0-9]{5}" is met.
     * @param text The text being checked
     * @return True if the text matches the constraint, otherwise false
     */
    public static boolean checkZipConstraint(String text) {
        String pattern = "[0-9]{5}";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    public static void main(String[] args) {
        launch();
    }
}