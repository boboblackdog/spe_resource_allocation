/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controller;

import com.example.spera_2.models.requests.DashboardRequest;
import com.example.spera_2.models.Employee;
import com.example.spera_2.models.requests.NikRequest;
import com.example.spera_2.models.UserLogin;
import com.example.spera_2.models.refTroops;
import com.example.spera_2.repositories.refGradesRepository;
import com.example.spera_2.repositories.refPositionsRepository;
import com.example.spera_2.repositories.refTroopsRepository;
import java.sql.Timestamp;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
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
    public refTroopsRepository refTroopsRepo;
   
    @Autowired
    private refPositionsRepository refPositionsRepo;
    
    @Autowired
    private refGradesRepository refGradesRepo;
    
    private final RealController real = new RealController();
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Document test() {
        return real.testConnection();
    }
    
    /*
    basic api homepage, just shows a welcome page. 
    interactable links can be added if needed
    */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Document> welcome() throws Exception {

        return real.welcome();
        
    }
    
    /*
    method used to obtain all troop information
    */
    @RequestMapping(value = "/troops/list", method = RequestMethod.POST)
    public ResponseEntity<Document> getAllTroops(@Valid @RequestBody Document tr, @RequestHeader String Authentication, HttpServletRequest request) throws Exception {
        
        return real.getAllTroops(tr, Authentication, refTroopsRepo.findAll(), request);
        
    }
    
    /*
    method to be used (later on after development) in conjunction with EXISTS..., INSERTINTOUSERBEARER and BUILDTOKEN methods (all defined above)
    */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<Document> loginUser(@Valid @RequestBody UserLogin ul, @RequestHeader String Authentication, HttpServletRequest request) throws Exception {
        
        return real.loginUser(ul, Authentication, refTroopsRepo.findByNik(ul.getUsername()), request);
        
    }
    /*
    method used to obtain logged in user's information
    */
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public ResponseEntity<Document> getUserProfile(@Valid @RequestBody NikRequest nr, @RequestHeader String Authentication, HttpServletRequest request) throws Exception {
        
        return real.getUserProfile(nr, Authentication, new Employee(refTroopsRepo.findByNik(nr.getNik())), request);
        
    }
    
    /*
    method used to obtain basic dashboard data, future details TBD
    */
    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public ResponseEntity<Document> getDashboard(@Valid @RequestBody DashboardRequest dr, HttpServletRequest request) { //@RequestHeader String Authentication

        return real.getDashboard(dr, request);
        
    }
    
    /*
    method used to get positions from db
    */
    @RequestMapping(value = "/troops/positions", method = RequestMethod.GET)
    public ResponseEntity<Document> getPositions(String Authentication, HttpServletRequest request) {
        
        return real.getPositions(Authentication, refPositionsRepo.findAll() , request);
        
    }
    
    /*
    method used to get grades from db
    */
    @RequestMapping(value = "/troops/grades", method = RequestMethod.GET)
    public ResponseEntity<Document> getGrades(String Authentication, HttpServletRequest request) {
        
        return real.getGrades(Authentication, refGradesRepo.findAll(), request);
        
    }
    
    /*
    method used to insert new troop
    */
    @RequestMapping(value = "/troops/add", method = RequestMethod.POST)
    public ResponseEntity<Document> insertTroops(String Authentication, @Valid @RequestBody Document ref, HttpServletRequest request) {
        
        if (!new refTroops(ref).containsNull()) {
            refTroopsRepo.save(new refTroops(ref.append("datetime_inserted", new Timestamp(new Date().getTime()))));
        }
        return real.insertTroops(Authentication, ref, request, new refTroops(ref).containsNull());
        
    }
}
