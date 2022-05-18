package com.example.carsale;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.ArrayList;

public class FindCar {

    @FXML
    private VBox car_list;

    @FXML
    private Button find_btn;

    @FXML
    private TextField model_field;

    @FXML
    private ChoiceBox<String> title_field;

    @FXML
    void initialize() {
        title_field.setItems(FXCollections.observableArrayList("Audi", "BMW", "KIA", "Lada", "Mini Countryman", "Toyota", "Daewoo"));

        find_btn.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            car_list.getChildren().clear();
            if(!title_field.getValue().trim().equals("")) {
                ArrayList<String> res;
                try {
                    res = Client.api.findCars(title_field.getValue(), model_field.getText());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
                JSONParser myParser = new JSONParser();
                for (String car : res) {
                    Object obj = null;
                    try {
                        obj = myParser.parse(car);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
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

                    carTitle.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent newMouseEvent) -> {
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("car-page-view.fxml"));

                        try {
                            fxmlLoader.load();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Parent parent = fxmlLoader.getRoot();
                        Stage stage = (Stage) find_btn.getScene().getWindow();
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

                    car_list.getChildren().add(gridpane);
                }
            } else {
                System.out.println("Bad req!");
            }
        });
    }
}

