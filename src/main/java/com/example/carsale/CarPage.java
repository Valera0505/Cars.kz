package com.example.carsale;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CarPage {

    @FXML
    private Button back_link;

    @FXML
    private Button call_owner;

    @FXML
    private Label car_date;

    @FXML
    private Label car_description;

    @FXML
    private Label car_dist;

    @FXML
    private Label car_model;

    @FXML
    private Label car_name;

    @FXML
    private Pane car_photo;

    @FXML
    private Label car_price;

    @FXML
    private Label car_title;

    @FXML
    private Label car_volume;

    @FXML
    private Label phone_label;

    @FXML
    private ScrollPane scroll_pane;


    @FXML
    void initialize() {
        back_link.getStyleClass().add("back_link");
        call_owner.getStyleClass().add("back_link");

        phone_label.setVisible(false);

        call_owner.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            phone_label.setVisible(true);
        });

        back_link.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage primaryStage = (Stage) back_link.getScene().getWindow();
            primaryStage.setTitle("Главная");
            primaryStage.getScene().setRoot(root);
            primaryStage.show();
        });
    }

    void loadInfo(String id) throws IOException, ClassNotFoundException, ParseException {
        String car = Client.api.getCar(id);

        Object obj = new JSONParser().parse(car);
        JSONObject jo = (JSONObject) obj;

        String title = (String) jo.get("title");
        car_model.setText(title);
        car_title.setText(title);

        String car_model_info = (String) jo.get("car_model");
        car_name.setText(car_model_info);

        String volume = (String) jo.get("volume");
        car_volume.setText(volume + " л.");

        String distance = (String) jo.get("distance");
        car_dist.setText(distance + " тыс. км");

        String car_date_info = (String) jo.get("car_date");
        car_date.setText(car_date_info + " год");

        String description = (String) jo.get("description");
        car_description.setText(description);

        String price = (String) jo.get("price");
        car_price.setText(price + " млн. тенге");

        String phone_number = (String) jo.get("phone_number");
        phone_label.setText(phone_number);

        String imageUrl = (String) jo.get("image");
        car_photo.setBackground(
                new Background(new BackgroundImage(
                        new Image(imageUrl, 316, 200, false, true),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        BackgroundSize.DEFAULT)));
    }
}