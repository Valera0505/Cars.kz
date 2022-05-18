package com.example.carsale;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class TcpApi implements Api {
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public TcpApi() throws IOException {
        out = new ObjectOutputStream(Client.socket.getOutputStream());
        in = new ObjectInputStream(Client.socket.getInputStream());
    }

    @Override
    public ArrayList<String> getCars(String text) throws IOException, ClassNotFoundException {
        out.writeObject("{ \"route\": \"" + text + "\" }");
        return (ArrayList<String>) in.readObject();
    }

    public String getCar(String res) throws IOException, ClassNotFoundException {
        out.writeObject("{ \"route\": \"getCar\", " + "\"data\": \"" + res + "\"}");
        return (String) in.readObject();
    }

    public ArrayList<String> findCars(String car_title, String car_model) throws IOException, ClassNotFoundException {
        out.writeObject("{ \"route\": \"findCar\", " + "\"car_title\": \"" + car_title + "\", \"car_model\": \"" + car_model + "\" }");
        return (ArrayList<String>) in.readObject();
    }

    public String setCar(String title, String model, String volume, String price, String date, String distance, String image, String description, String phoneNumber) throws IOException, ClassNotFoundException {
        out.writeObject("{ \"route\": \"setCar\", " + "\"car_title\": \"" + title + "\", \"car_model\": \"" + model + "\", \"car_volume\": \"" + volume + "\", \"car_price\": \"" + price + "\", \"car_date\": \"" + date + "\", \"car_distance\": \"" + distance + "\", \"car_image\": \"" + image + "\", \"car_description\": \"" + description + "\", \"car_phone_number\": \"" + phoneNumber + "\" }");
        return (String) in.readObject();
    }

    @Override
    public String login(String login, String password) throws IOException, ClassNotFoundException {
        out.writeObject("{ \"route\": \"login\", " + "\"userLogin\": \"" + login + "\", \"password\": \"" + password + "\" }");
        return (String) in.readObject();
    }
}