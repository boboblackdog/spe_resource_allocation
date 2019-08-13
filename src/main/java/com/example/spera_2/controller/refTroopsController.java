/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controller;

import com.example.spera_2.models.DashboardRequest;
import com.example.spera_2.models.DashboardResponse;
import com.example.spera_2.models.Employee;
import com.example.spera_2.models.NikRequest;
import com.example.spera_2.models.TroopRequest;
import com.example.spera_2.models.UserLogin;
import com.example.spera_2.repositories.refTroopsRepository;
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
    method used to obtain all troop information
    */
    @RequestMapping(value = "/troops/list", method = RequestMethod.POST)
    public Document getTroopsList(@Valid @RequestBody TroopRequest tr, @RequestHeader String Authentication) throws Exception {
        try {
            if (tr.getTroops().equals("get-all")) {
                List<Employee> list = new ArrayList<>();
                repo.findAll().forEach((ref) -> {
                    list.add(new Employee(ref));
                });
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
    method to be used (later on after development) in conjunction with EXISTS..., INSERTINTOUSERBEARER and BUILDTOKEN methods (all defined above)
    */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public Document loginUser(@Valid @RequestBody UserLogin ul, @RequestHeader String Authentication) throws Exception {
        
        try {
            MySQLConnection obj = new MySQLConnection();
            if (obj.existsInUserTable(ul)) {
                if (obj.sessionActive(ul, Authentication)) {
                    obj.extendLoginSession(ul, Authentication);
                    obj.closeCurrentConnection();
                    return (new Document()).append("rc", "00").append("message", "login successful, session extended")
                            .append("role", "admin")
                            .append("menu", "home, troops, account")
                            .append("bearer token", Authentication)
                            .append("data", (new Employee(repo.findByNik(ul.getNik()))))
                            ;
                } else {
                    String newToken = buildToken();
                    obj.insertIntoUserBearer(ul, newToken);
                    obj.closeCurrentConnection();
                    return (new Document()).append("rc", "00").append("message", "login successful, new session started")
                            .append("role", "admin")
                            .append("menu", "home, troops, account")
                            .append("bearer token", newToken)
                            .append("data", (new Employee(repo.findByNik(ul.getNik()))))
                            ;
                }  
            } else {
                obj.closeCurrentConnection();
                return new Document().append("rc", "10").append("message", "DNE in `users`");
            } 
        } catch (Exception e) {
            return (new Document()).append("rc", "11").append("message", "invalid request format")
                    .append("errorMsg", e.getMessage())
                    .append("data login", ul);
        }
    }
    /*
    method used to obtain logged in user's information
    */
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
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
    
    /*
    method used to obtain basic dashboard data, future details TBD
    */
    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public Document getDashboard(@Valid @RequestBody DashboardRequest dr) { //@RequestHeader String Authentication
        /*
        reads the year and the authentication from the header
        for the most part this is currently hard-coded to return 
        desired values according to the provided documentation
        */
        return (new Document()).append("rc", "00").append("message", "request dashboard data success")
                .append("role", "admin")
                .append("menu", "home, troops, account")
                .append("data", 
                        (new DashboardResponse(dr.getYear()))
                );
    }

}
