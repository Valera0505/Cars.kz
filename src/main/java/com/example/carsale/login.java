package com.example.carsale;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class login {

    @FXML
    private Button login_btn;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Label warning;

    @FXML
    void initialize() {
        warning.setVisible(false);

        login_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (!login_field.getText().trim().equals("") && !password_field.getText().trim().equals("")) {
                warning.setVisible(false);
                String res;
                try {
                    res = Client.api.login(login_field.getText(), password_field.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }

                if (res.equals("success")) {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage primaryStage = (Stage) login_btn.getScene().getWindow();
                    primaryStage.setTitle("Главная");
                    primaryStage.getScene().setRoot(root);
                    primaryStage.show();
                } else {
                    warning.setVisible(true);
                }
            }
        });
    }

}