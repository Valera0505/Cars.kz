package com.example.carsale;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddCarController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Button add_task;

    @FXML
    private TextField car_date;

    @FXML
    private TextArea car_description;

    @FXML
    private TextField car_distance;

    @FXML
    private TextField car_model;

    @FXML
    private TextField car_price;

    @FXML
    private ChoiceBox<String> car_title;

    @FXML
    private TextField car_volume;

    @FXML
    private Button to_cars_list;

    @FXML
    private TextField car_image;

    @FXML
    private TextField car_phone_number;


    @FXML
    void initialize() {
        add_task.getStyleClass().add("back_link");
        to_cars_list.getStyleClass().add("back_link");
        car_title.setItems(FXCollections.observableArrayList("Audi", "BMW", "KIA"));

        add_task.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(!car_title.getValue().trim().equals("") && !car_model.getText().trim().equals("") && !car_volume.getText().trim().equals("") && !car_price.getText().trim().equals("") && !car_date.getText().trim().equals("") && !car_distance.getText().trim().equals("") && !car_image.getText().trim().equals("") && !car_description.getText().trim().equals("")&& !car_phone_number.getText().trim().equals("")) {

                try {
                    String res = Client.api.setCar(car_title.getValue(), car_model.getText(), car_volume.getText(),  car_price.getText(), car_date.getText(),  car_distance.getText(), car_image.getText(), car_description.getText(), car_phone_number.getText());
                    System.out.println(res);

                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage primaryStage = (Stage) to_cars_list.getScene().getWindow();
                    primaryStage.setTitle("Главная");
                    primaryStage.getScene().setRoot(root);
                    primaryStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Bad");
            }
        });

        to_cars_list.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage primaryStage = (Stage) to_cars_list.getScene().getWindow();
            primaryStage.setTitle("Главная");
            primaryStage.getScene().setRoot(root);
            primaryStage.show();
        });
    }
}
