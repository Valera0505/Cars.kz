package com.example.carsale;

import java.sql.*;
import java.util.ArrayList;

public class DB {
    private final String HOST = "localhost";
    private final String PORT = "3306";
    private final String DB_NAME = "cars_sale";
    private final String LOGIN = "mysql";
    private final String PASS = "mysql";
    private Connection dbConn = null;

    private Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }

    public ArrayList<String> getTasks() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM cars ORDER BY `id` DESC";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<String> tasks = new ArrayList<>();
        while(res.next()) {
            tasks.add("{ \"id\": \"" + res.getString("id") + "\", " + "\"title\": \"" + res.getString("title") + "\", \"car_model\": \"" + res.getString("car_model") + "\", \"price\": \"" + res.getString("price") + "\", \"description\": \"" + res.getString("description") + "\", \"image\": \"" + res.getString("image") + "\" }");
        }
        return tasks;
    }

    public String login(String login, String password) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `users` WHERE `login` LIKE \"" + login + "\" AND `password` LIKE \"" + password + "\"";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        String answer = null;

        if (res.next() == false) {
            answer = "fail";
        } else {
            answer = "success";
        }

//        while(res.next()) {
//            System.out.println(res.wasNull());
//            if(res.wasNull()) {
//                answer = "fail";
//            } else {
//                answer = "success";
//            }
//        }
        return answer;
    }

    public ArrayList<String> findCars(String car_title, String car_model) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM `cars` WHERE `title` LIKE \"%" + car_title+ "%\" AND `car_model` LIKE \"%" + car_model + "%\"";

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        ArrayList<String> tasks = new ArrayList<>();
        while(res.next()) {
            tasks.add("{ \"id\": \"" + res.getString("id") + "\", " + "\"title\": \"" + res.getString("title") + "\", \"car_model\": \"" + res.getString("car_model")  + "\", \"price\": \"" + res.getString("price") + "\", \"description\": \"" + res.getString("description") + "\", \"image\": \"" + res.getString("image") + "\" }");
        }
        return tasks;
    }

    public String getCar(String id) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM cars WHERE `cars`.`id` = " + id;

        Statement statement = getDbConnection().createStatement();
        ResultSet res = statement.executeQuery(sql);

        String carData = null;

        while(res.next())
            carData = ("{ \"id\": \"" + res.getString("id") + "\", " + "\"title\": \"" + res.getString("title") + "\", \"price\": \"" + res.getString("price") + "\", \"description\": \"" + res.getString("description") + "\", \"image\": \"" + res.getString("image") + "\", \"volume\": \"" + res.getString("volume") + "\", \"car_model\": \"" + res.getString("car_model") + "\", \"distance\": \"" + res.getString("distance") + "\", \"car_date\": \"" + res.getString("car_date") + "\", \"phone_number\": \"" + res.getString("phone_number") + "\" }");
        return carData;
    }

    public String setCar(String title, String model, String volume, String price, String date, String distance, String image, String description, String phoneNumber) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `cars`(`title`, `car_model`, `volume`, `price`, `car_date`, `distance`, `image`, `description`, `phone_number`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, title);        prSt.setString(2, model);
        prSt.setInt(3, Integer.parseInt(volume));
        prSt.setInt(4, Integer.parseInt(price));
        prSt.setInt(5, Integer.parseInt(date));
        prSt.setInt(6, Integer.parseInt(distance));
        prSt.setString(7, image);
        prSt.setString(8, description);
        prSt.setString(9, phoneNumber);

        prSt.executeUpdate();

        String carData = "car " + title + " is created!";
        return carData;
    }

    public void insertTask(String task) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO `cars` (title) VALUES (?)";
        PreparedStatement prSt = getDbConnection().prepareStatement(sql);
        prSt.setString(1, task);
        prSt.executeUpdate();
    }
}
