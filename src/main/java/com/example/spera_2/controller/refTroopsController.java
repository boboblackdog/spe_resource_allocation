/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controller;

import com.example.spera_2.models.Employee;
import com.example.spera_2.models.NikRequest;
import com.example.spera_2.models.TroopRequest;
import com.example.spera_2.models.UserLogin;
import com.example.spera_2.models.refTroops;
import com.example.spera_2.repositories.refTroopsRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rakhadjo
 */
@RestController
@RequestMapping("/api")
public class refTroopsController {
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/spera_portal?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    
    @Autowired
    private refTroopsRepository repo;
    
    /*
    for building random 32 digit alphanumeric token
    */
    private static final String set = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuv";
    public static String buildToken() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int charPosition = (int)(Math.random()*set.length());
            sb.append(set.charAt(charPosition));
        } return sb.toString();
    }
    
    /*
    provoked when user exists, but there are no open sessions for that user
    */
//    public static void insertIntoUserBearer() throws SQLException{
//        Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
//        Statement statement = connection.createStatement();
//        String sql = "INSERT INTO `user_bearer` (nik, )";
//    }
    
    /*
    provoked to provide
    */
    public static boolean existsInUserTable(UserLogin ul) throws SQLException {
        Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        String sql = "SELECT * FROM user;";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next()) {
            if (resultSet.getString("nik").equals(ul.getNik()) && resultSet.getString("password_hash").equals(ul.getPassword())) {
                return true;
            }
        } return false;
    }
    
    public static boolean sessionActive(UserLogin ul, String auth) throws SQLException {
        Connection connection = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        Statement statement = connection.createStatement();
        String sql4 = "SELECT TIMESTAMPDIFF(MINUTE, datetime_created, NOW()) AS difference FROM user_bearer WHERE "
                + "nik = " + ul.getNik() + " AND "
                + "bearer_token = " + "'"+ auth + "'"
                + ";";
        ResultSet resultSet4 = statement.executeQuery(sql4);
        while (resultSet4.next()) {
            if (resultSet4.getInt("difference") < 30) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    
    /*
    method used to obtain all troop information
    */
    @RequestMapping(value = "/troops/list", method = RequestMethod.POST)
    public Document getTroopsList(@Valid @RequestBody TroopRequest tr, @RequestHeader String Authentication) throws Exception {
        try {
            if (tr.getTroops().equals("get-all")) {
                List<Employee> list = new ArrayList<>();
                for (refTroops ref : repo.findAll()) {
                    list.add(new Employee(ref));
                }
                return (new Document()).append("rc", "00").append("message", "success")
                        .append("role", "admin")
                        .append("menu", "home, troops, account")
                        .append("data", list)
                        .append("auth token", Authentication)
                        .append("built token", buildToken());
            } else {
                return (new Document()).append("rc", "12").append("message", "command undefined");
            }
        } catch (Exception e) {
            return (new Document()).append("rc", "11").append("message", "invalid request format");
        }   
    }
    
    /*
    method to be used (later on after developemnt) in conjunction with VERIFY and BUILDTOKEN methods (both defined above)
    */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Document loginUser(@Valid @RequestBody UserLogin ul, @RequestHeader String Authentication) throws Exception {
        
        try {
            if (existsInUserTable(ul)) {
                if (sessionActive(ul, Authentication)) {
                    return (new Document()).append("rc", "00").append("message", "login successful1")
                            .append("role", "admin")
                            .append("menu", "home, troops, account")
                            .append("bearer token", Authentication)
                            //.append("data", (new Employee(repo.findByNik(ul.getNik()))))
                            ;
                } else {
                    String newToken = buildToken();
                    
                    return (new Document()).append("rc", "00").append("message", "login successful2")
                            .append("role", "admin")
                            .append("menu", "home, troops, account")
                            .append("bearer token", newToken)
                            //.append("data", (new Employee(repo.findByNik(ul.getNik()))))
                            ;
                }  
            }
            return new Document().append("rc", "10").append("message", "failed");
            /*
            if the user data exists in the user table
                return successful login document
            else
                return failed login document
            */
            
        } catch (Exception e) {
            return (new Document().append("rc", "11").append("message", "invalid request format").append("errorMsg", e.getMessage()));
        }
    }
    /*
    method used to obtain logged in user's information
    */
    @RequestMapping(value = "/user/user-info", method = RequestMethod.POST)
    public Document searchByNik(@Valid @RequestBody NikRequest nr, @RequestHeader String Authentication) throws Exception {
        try {
            Integer.parseInt(nr.getNik());
            if (repo.findByNik(nr.getNik()) == null) {
                return (new Document()).append("rc", "10").append("message", "entry DNE");
            } else {
                return (new Document()).append("rc", "00").append("message", "success")
                        .append("data", (new Employee(
                                repo.findByNik(nr.getNik()))))
                        .append("auth token", Authentication)
                        .append("built token", buildToken());
            }
        } catch (NumberFormatException e) {
            return (new Document()).append("rc", "11").append("message", "invalid request format");
        } 
    }

}
