/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controller;

import com.example.spera_2.testconnection.MySQLConnection;
import com.example.spera_2.models.DashboardRequest;
import com.example.spera_2.models.DashboardResponse;
import com.example.spera_2.models.Employee;
import com.example.spera_2.models.NikRequest;
import com.example.spera_2.models.TroopRequest;
import com.example.spera_2.models.UserLogin;
import com.example.spera_2.models.refTroops;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author rakhadjo
 */

public class RealController {
    
    /*
    returns all troop data
    */
    public ResponseEntity<Document> getAllTroops(TroopRequest tr, String Authentication, List<refTroops> li) {
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        try {
            List<Employee> list = new ArrayList<>();
            if (tr.getTroops().equals("get-all")) {
                li.forEach((ref) -> {
                    list.add(new Employee(ref));
                });
            return ResponseEntity.accepted().headers(header).body(
                        (new Document()).append("rc", "00").append("message", "success")
                        .append("role", "admin")
                        .append("menu", "home, troops, account")
                        .append("data", list)
                        .append("auth token", Authentication)
                        .append("built token", buildToken()));
            } else {
                return ResponseEntity.accepted().headers(header).body
                            (new Document().append("rc", "12").append("message", "command undefined"));
        }
        } catch (Exception e) {
            return ResponseEntity.accepted().headers(header).body(
                new Document().append("rc", "11").append("message", "invalid request format"));
        }
        
    }
    
    /*
    returns user profile of a given user
    */
    public ResponseEntity<Document> getUserProfile(NikRequest nr, String Authentication, Employee emp) throws Exception {
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        try {
            Integer.parseInt(nr.getNik());
            if (emp == null) {
                return ResponseEntity.accepted().headers(header).body
                        (new Document().append("rc", "10").append("message", "entry DNE"));
            } else {
                return ResponseEntity.accepted().headers(header).body
                        (new Document().append("rc", "00").append("message", "success")
                        .append("data", emp)
                        .append("auth token", Authentication)
                        .append("built token", buildToken()));
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.accepted().headers(header).body
                    (new Document().append("rc", "11").append("message", "invalid request format"));
        }
    }
    
    /*
    returns dashboard data
    */
    public ResponseEntity<Document> getDashboard(DashboardRequest dr) {
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        return ResponseEntity.accepted().headers(header).body
                (new Document().append("rc", "00").append("message", "request dashboard data success")
                .append("role", "admin")
                .append("menu", "home, troops, account")
                .append("data", 
                        (new DashboardResponse(dr.getYear()))
                ));
    }
    
    /*
    basic welcome page
    */
    public ResponseEntity<Document> welcome() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("/troops/list");
        list.add("/user/login");
        list.add("/user/profile");
        list.add("/dashboard");
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        try {
            Document body = new Document().append("rc", "00").append("message", "welcome to SPERA!")
                .append("links", list)
//                .append("result", storeLogFiles())
                ;
            return ResponseEntity.accepted().headers(header).body(body);
        } catch (Exception e) {
            return ResponseEntity.accepted().headers(header).body(
                    new Document()
                            .append("rc", "00")
                            .append("message", e.getMessage())
            );
        }
        
    }
    
    /*
    user login support
    @RequestHeader String Authentication
    @Valid @RequestBody UserLogin ul
    */
    public ResponseEntity<Document> loginUser(UserLogin ul, String Authentication, refTroops ref) throws Exception {
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        try {
            MySQLConnection conn = new MySQLConnection();
            if (conn.existsInUserTable(ul)) {
                if (conn.sessionActive(ul, Authentication)) {
                    conn.extendLoginSession(ul, Authentication);
                    conn.closeCurrentConnection();
                    return ResponseEntity.accepted().headers(header).body
                            (new Document().append("rc", "00").append("message", "login successful, session extended")
                            .append("role", "admin")
                            .append("menu", "home, troops, account")
                            .append("bearer token", Authentication)
                            .append("data", new Employee(ref)))
                            ;
                } else {
                    String newToken = buildToken();
                    conn.insertIntoUserBearer(ul, newToken);
                    conn.closeCurrentConnection();
                    return ResponseEntity.accepted().headers(header).body
                            (new Document().append("rc", "00").append("message", "login successful, new session started")
                            .append("role", "admin")
                            .append("menu", "home, troops, account")
                            .append("bearer token", newToken)
                            .append("data", new Employee(ref)))
                            ;
                }
            } else {
                conn.closeCurrentConnection();
                return ResponseEntity.accepted().headers(header).body
                        (new Document().append("rc", "10").append("message", "DNE in `users`"));
            }
        } catch (Exception e) {
            return ResponseEntity.accepted().headers(header).body
                    (new Document().append("rc", "11").append("message", "invalid request format")
                            .append("errorMsg", e.getMessage())
                            .append("data login", ul)
                            .append("stacktrace", e.getStackTrace())
                    )
                    ;
        }
    }
    
    /*
    basic constructor
    */
    public RealController() {}
    
    /*
    for building random 32 digit alphanumeric token
    */
    private static final String set = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuv";
    private static String buildToken() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            int charPosition = (int)(Math.random()*set.length());
            sb.append(set.charAt(charPosition));
        } return sb.toString();
    }
}
