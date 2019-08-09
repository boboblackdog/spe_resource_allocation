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
    for making sure the given data exists on login, verified by checking mysql tables
    */
    public static void verify(String username, String password, String token) throws SQLException { 
        Connection conn = DriverManager.getConnection("localhost:3306", "root", "password");
        String sql = "SELECT * FROM user WHERE username=?, password=?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, username);
        pstm.setString(2, password);
        ResultSet rslt = pstm.executeQuery();
        
        if (rslt.getString("username") != null && rslt.getString("password") != null) {
            /*
            if token still valid, 
                continue login session with given token
            else 
                create a new login session
            */
        } else {
            //access denied
        }
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
                        .append("data", list);
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
            Integer.parseInt(ul.getNik());
            if (repo.findByNik(ul.getNik()) != null) { //(and the password is equal to the one in the provided database TABLE USER AUTH KEY
                return (new Document()).append("rc", "00").append("message", "login successful")
                        .append("role", "admin")
                        .append("menu", "home, troops, account")
                        .append("data", (new Employee(repo.findByNik(ul.getNik()))))
                        .append("token", Authentication);
            } 
            return (new Document()).append("rc", "12").append("message", "command undefined");
        } catch (NumberFormatException e) {
            return (new Document()).append("rc", "11").append("message", "invalid request format");
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
                                repo.findByNik(nr.getNik()))));
            }
        } catch (NumberFormatException e) {
            return (new Document()).append("rc", "11").append("message", "invalid request format");
        } 
    }
    
    /*
    IN DEVELOPMENT: 
    JSON OBJECT DIRECT RETURN (to stop using Document Object)
    */
//    @RequestMapping(value = "/jsonobject", method = RequestMethod.POST)
//    public String returnByNik(@Valid @RequestBody NikRequest nr) throws Exception {
//        try {
//            Integer.parseInt(nr.getNik());
//            if (repo.findByNik(nr.getNik())==null) {
//                return ((new JSONObject()).put("rc", "10").put("message", "JSON entry DNE")).toString();
//            } else {
//                return ((new JSONObject()).put("rc", "00").put("message", "success").put("data", 
//                        (new Employee(repo.findByNik(nr.getNik()), false ))
//                        )).toString();
//            }
//        } catch (Exception e) {
//            return ((new JSONObject()).put("rc", "11").put("message", "JSON Incorrect format")).toString();
//        }
//    }

}
