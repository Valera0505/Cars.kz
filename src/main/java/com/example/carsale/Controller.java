package com.example.carsale;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private VBox all_tasks;
    @FXML
    private Button to_add_car;

    @FXML
    private Button to_find_car;

    @FXML
    void initialize() {
        to_add_car.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("add-car-view.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent parent = fxmlLoader.getRoot();
            Stage stage = (Stage) to_add_car.getScene().getWindow();
            stage.setTitle("Добавить машину");
            stage.getScene().setRoot(parent);
            stage.show();
        });

        to_find_car.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("find-car-view.fxml"));

            try {
                fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent parent = fxmlLoader.getRoot();
            Stage stage = (Stage) to_find_car.getScene().getWindow();
            stage.setTitle("Найти машину");
            stage.getScene().setRoot(parent);
            stage.show();
        });

        // Метод для подгрузки данных внутрь программы
        loadInfo();
    }

    void loadInfo() {
        try {
             all_tasks.getStyleClass().add("car-inner");

             // Получаем все данные из базы данных
             ArrayList<String> tasks = Client.api.getCars("getCars");
             JSONParser myParser = new JSONParser();

             for (String task : tasks) {
                 Object obj = myParser.parse(task);
                 JSONObject jo = (JSONObject) obj;
                 String id = (String) jo.get("id");
                 String title = (String) jo.get("title");
                 String car_model = (String) jo.get("car_model");
                 String price = (String) jo.get("price");
                 String description = (String) jo.get("description");
                 String image = (String) jo.get("image");

                 Label carTitle = new Label();
                 carTitle.setWrapText(true);
                 carTitle.getStyleClass().add("car-title");

                 Label carModel = new Label();
                 carModel.setWrapText(true);
                 carModel.setText(car_model);

                 carTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
                     FXMLLoader fxmlLoader = new FXMLLoader();
                     fxmlLoader.setLocation(getClass().getResource("car-page-view.fxml"));

                     try {
                         fxmlLoader.load();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }

                     Parent parent = fxmlLoader.getRoot();
                     Stage stage = (Stage) carTitle.getScene().getWindow();
                     stage.setTitle(title);
                     stage.getScene().setRoot(parent);
                     CarPage controllerCarPage = fxmlLoader.getController(); //получаем контроллер для второй формы
                     try {
                         controllerCarPage.loadInfo(id); // передаем необходимые параметры
                     } catch (IOException e) {
                         throw new RuntimeException(e);
                     } catch (ClassNotFoundException e) {
                         throw new RuntimeException(e);
                     } catch (ParseException e) {
                         throw new RuntimeException(e);
                     }
                     stage.show();
                 });

                 carTitle.setText(title);

                 Label carDesc = new Label();
                 carDesc.setWrapText(true);
                 carDesc.getStyleClass().add("car-desc");
                 carDesc.setText(description);

                 Label carPrice = new Label();
                 carPrice.setWrapText(true);
                 carPrice.getStyleClass().add("car-price");
                 carPrice.setText(price + "млн. тенге");

                 ImageView carImage = new ImageView();
                 carImage.setImage(new Image(image, 160, 105, false, false));

                 VBox pane = new VBox();
                 pane.getStyleClass().add("car-info");
                 pane.getChildren().add(carTitle);
                 pane.getChildren().add(carModel);
                 pane.getChildren().add(carDesc);
                 pane.getChildren().add(carPrice);


                 GridPane gridpane = new GridPane();
                 gridpane.add(carImage, 1, 0);
                 gridpane.add(pane, 2, 0);

                 all_tasks.getChildren().add(gridpane);
             }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}