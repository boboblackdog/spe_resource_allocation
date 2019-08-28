/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controllers;

import com.example.spera_2.Spera2Application;
import com.example.spera_2.controllers.logic.RealController;
import com.example.spera_2.controllers.logic.RealTroopController;
import com.example.spera_2.controllers.logic.RealUserController;
import com.example.spera_2.models.Employee;
import com.example.spera_2.models.Overtime;
import com.example.spera_2.models.Report;
import com.example.spera_2.models.requests.UserProfileRequest;
import com.example.spera_2.models.refTroops;
import com.example.spera_2.models.requests.DashboardRequest;
import com.example.spera_2.models.requests.TroopRequest;
import com.example.spera_2.models.requests.UserLoginRequest;
import com.example.spera_2.models.responses.ResponseBody;
import com.example.spera_2.repositories.ReportRepository;
import com.example.spera_2.repositories.refGradesRepository;
import com.example.spera_2.repositories.refPositionsRepository;
import com.example.spera_2.repositories.refTroopsRepository;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class Controller {
    
    @Autowired
    public refTroopsRepository refTroopsRepo;
   
    @Autowired
    private refPositionsRepository refPositionsRepo;
    
    @Autowired
    private refGradesRepository refGradesRepo;
    
    @Autowired
    private ReportRepository reportRepo;
    
    private final RealController realController = new RealController();
    private final RealTroopController realTroopController = new RealTroopController();
    private final RealUserController realUserController = new RealUserController();
    private final Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public Document test() {
        return realController.testConnection();
    }
    
    /*
    basic api homepage, just shows a welcome page. 
    interactable links can be added if needed
    */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<Document> welcome() throws Exception {
        
        return realController.welcome();
        
    }
    
    /*
    method to be used (later on after development) in conjunction with EXISTS..., INSERTINTOUSERBEARER and BUILDTOKEN methods (all defined above)
    */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> loginUser(@Valid @RequestBody UserLoginRequest ul, @RequestHeader String Authentication, HttpServletRequest request) throws Exception {
        
        if (ul.usernameVerifiable()) {
            return realUserController.loginUser(ul, Authentication, new Employee(ul.getUsername()), request);
        } return realUserController.loginUser(ul, Authentication, null, request);
        //return real.loginUser(ul, Authentication, refTroopsRepo.findByNik(ul.getUsername()), request);
        
    }
    /*
    method used to obtain logged in user's information
    */
    @RequestMapping(value = "/user/profile", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> getUserProfile(@Valid @RequestBody UserProfileRequest upr, @RequestHeader String Authentication, HttpServletRequest request) throws Exception {
        
        if (upr.nikVerifiable()) {
            return realUserController.getUserProfile(upr, Authentication, request, new Employee(upr.getNik()));
        } return realUserController.getUserProfile(upr, Authentication, request, null);
        
    }
    
    /*
    method used to obtain basic dashboard data, future details TBD
    */
    @RequestMapping(value = "/dashboard", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> getDashboard2(@Valid @RequestBody DashboardRequest dr, @RequestHeader String Authentication, HttpServletRequest request) { //@RequestHeader String Authentication

        return realController.getDashboard2(dr, Authentication, request);
        
    }
    
    /*
    method used to get positions from db
    */
    @RequestMapping(value = "/troops/positions", method = RequestMethod.GET)
    public ResponseEntity<ResponseBody> getPositions(@RequestHeader String Authentication, HttpServletRequest request) throws Exception {
        /*
        verification method required. request data manually if needed. 
        */
        return realTroopController.getPositions(Authentication, refPositionsRepo.findAll() , request);
        
    }
    
    /*
    method used to get grades from db
    */
    @RequestMapping(value = "/troops/grades", method = RequestMethod.GET)
    public ResponseEntity<ResponseBody> getGrades(@RequestHeader String Authentication, HttpServletRequest request) throws Exception {
        /*
        verification method required. request data manually if needed. 
        */
        return realTroopController.getGrades(Authentication, refGradesRepo.findAll(), request);
        
    }
    
    /*
    method used to obtain all troop information
    */
    @RequestMapping(value = "/troops/list", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> getAllTroops(@Valid @RequestBody TroopRequest tr, @RequestHeader String Authentication, HttpServletRequest request) throws Exception {
        /*
        verification method required. request data manually if needed. 
        */
        return realTroopController.getAllTroops(tr, Authentication, refTroopsRepo.findAll(), request);
        
    }
    
    /*
    method used to insert new troop
    */
    @RequestMapping(value = "/troops/add", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> addTroops(@RequestHeader String Authentication, @Valid @RequestBody refTroops ref, HttpServletRequest request) throws Exception {
        
        if (!ref.containsNull()) {
            refTroopsRepo.save(ref);
        }
        return realTroopController.addTroops(Authentication, ref, request);
        
    }
    
    /*
    method used to insert new report
    */
    @RequestMapping(value = "/troops/report", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> addReport(@RequestHeader String Authentication, @Valid @RequestBody Report report, HttpServletRequest request) throws Exception {
        
        return realTroopController.addReport(Authentication, report, request);
        
    }
    
    /*
    method used to add overtime
    */
    @RequestMapping(value = "/troops/ot", method = RequestMethod.POST)
    public ResponseEntity<ResponseBody> addOvertime(@RequestHeader String Authentication, @Valid @RequestBody Overtime overtime, HttpServletRequest request) throws Exception {
        
        return realTroopController.addOvertime(Authentication, overtime, request);
        
    }
}
