package com.example.carsale;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server extends Thread {
    ServerSocket server = null;
    Socket client = null;
    DB db = null;

    public Server()
    {
        try {
            server = new ServerSocket(1400);
            System.out.println("Starting the Server");
            start();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void run()
    {
        while(true)
        {
            try {
                client = server.accept();

                System.out.println("Client is connected");

                ObjectOutputStream outputStream = new ObjectOutputStream(client.getOutputStream());

                ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());

                String message;

                while (!client.isClosed()){
                    message = (String)inputStream.readObject();
                    db = new DB();
                    ArrayList<String> data = null;

                    Object obj = new JSONParser().parse(message);
                    JSONObject jo = (JSONObject) obj;
                    String route = (String) jo.get("route");

                    if (route.equalsIgnoreCase("getCars")) {
                        data = getCars();
                        outputStream.writeObject(data);
                    }

                    if (route.equalsIgnoreCase("getCar")) {
                        String dataId = (String) jo.get("data");
                        String carData = getCar(dataId);
                        outputStream.writeObject(carData);
                    }

                    if (route.equalsIgnoreCase("findCar")) {
                        String car_title = (String) jo.get("car_title");
                        String car_model = (String) jo.get("car_model");

                        data = findCar(car_title, car_model);
                        outputStream.writeObject(data);
                    }

                    if (route.equalsIgnoreCase("login")) {
                        String login = (String) jo.get("userLogin");
                        String password = (String) jo.get("password");

                        String res = login(login, password);
                        outputStream.writeObject(res);
                    }

                    if (route.equalsIgnoreCase("setCar")) {
                        String car_title = (String) jo.get("car_title");
                        String car_model = (String) jo.get("car_model");
                        String car_volume = (String) jo.get("car_volume");
                        String car_price = (String) jo.get("car_price");
                        String car_date = (String) jo.get("car_date");
                        String car_distance = (String) jo.get("car_distance");
                        String car_image = (String) jo.get("car_image");
                        String car_description = (String) jo.get("car_description");
                        String car_phone_number = (String) jo.get("car_phone_number");

                        String carData = setCar(car_title, car_model, car_volume, car_price, car_date, car_distance, car_image, car_description, car_phone_number);

                        outputStream.writeObject(carData);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args)
    {
        new Server();
    }

    ArrayList<String> getCars() {
        try {
            ArrayList<String> tasks = db.getTasks();
            System.out.println(tasks);
            return tasks;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    ArrayList<String> findCar(String car_title, String car_model) {
        try {
            ArrayList<String> cars = db.findCars(car_title, car_model);
            System.out.println(cars);
            return cars;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    String getCar(String data) throws SQLException, ClassNotFoundException {
        System.out.println(db.getCar(data));
        return db.getCar(data);
    }

    String login(String login, String password) throws SQLException, ClassNotFoundException {
        System.out.println(db.login(login, password));
        return db.login(login, password);
    }

    String setCar(String title, String model, String volume, String price, String date, String distance, String image, String description, String phoneNumber) throws SQLException, ClassNotFoundException {
        return db.setCar(title, model, volume, price, date, distance, image, description, phoneNumber);
    }
}
