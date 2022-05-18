package com.example.carsale;

import java.io.IOException;
import java.util.ArrayList;

public interface Api {
    ArrayList<String> getCars(String text) throws IOException, ClassNotFoundException;

    String getCar(String res) throws IOException, ClassNotFoundException;

    ArrayList<String> findCars(String car_title, String car_model) throws IOException, ClassNotFoundException;

    String setCar(String title, String model, String volume, String price, String date, String distance, String image, String description, String phoneNumber) throws IOException, ClassNotFoundException;

    String login(String login, String password) throws IOException, ClassNotFoundException;
}