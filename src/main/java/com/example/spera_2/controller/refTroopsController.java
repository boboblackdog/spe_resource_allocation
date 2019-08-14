/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controller;

import com.example.spera_2.models.DashboardRequest;
import com.example.spera_2.models.Employee;
import com.example.spera_2.models.NikRequest;
import com.example.spera_2.models.TroopRequest;
import com.example.spera_2.models.UserLogin;
import com.example.spera_2.repositories.refTroopsRepository;
import javax.validation.Valid;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    
    private final RealController real = new RealController();
    
    /*
    basic api homepage, just shows a welcome page. 
    interactable links can be added if needed
    */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Document> welcome() {

        return real.welcome();
        
    }
    
    /*
    method used to obtain all troop information
    */
    @RequestMapping(value = "/troops/list", method = RequestMethod.POST)
    public ResponseEntity<Document> getAllTroops(@Valid @RequestBody TroopRequest tr, @RequestHeader String Authentication) throws Exception {
        
        return real.getAllTroops(tr, Authentication, repo.findAll());
        
    }
    
    /*
    method to be used (later on after development) in conjunction with EXISTS..., INSERTINTOUSERBEARER and BUILDTOKEN methods (all defined above)
    */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<Document> loginUser(@Valid @RequestBody UserLogin ul, @RequestHeader String Authentication) throws Exception {
        
        return real.loginUser(ul, Authentication, repo.findByNik(ul.getNik()));
        
    }
    /*
    method used to obtain logged in user's information
    */
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public ResponseEntity<Document> getUserProfile(@Valid @RequestBody NikRequest nr, @RequestHeader String Authentication) throws Exception {
        
        return real.getUserProfile(nr, Authentication, new Employee(repo.findByNik(nr.getNik())));
        
    }
    
    /*
    method used to obtain basic dashboard data, future details TBD
    */
    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public ResponseEntity<Document> getDashboard(@Valid @RequestBody DashboardRequest dr) { //@RequestHeader String Authentication

        return real.getDashboard(dr);
        
    }

}
