/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.more_controllers;

import com.example.spera_2.models.Employee;
import com.example.spera_2.more_repositories.EmployeeRepository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;

import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rakhadjo
 */

@RequestMapping("/emp_control")
public class EmployeeController {
    @Autowired
    public EmployeeRepository empRepo;
    
    private static MongoClient client = new MongoClient("localhost", 27017);
    private static MongoCredential cred = MongoCredential.createCredential("root", "spera", "password".toCharArray());
    private static MongoDatabase database = client.getDatabase("spera");
    
    private static MongoCollection<Document> refTroopsColl = database.getCollection("refTroops");
    private static MongoCollection<Document> refStatusColl = database.getCollection("refStatus");
    private static MongoCollection<Document> refGradesColl = database.getCollection("refGrades");
    private static MongoCollection<Document> refPositionsColl = database.getCollection("refPositions");
    
    
    @RequestMapping(value = "/{nik}", method = RequestMethod.GET)
    public Employee searchByNik(@PathVariable("nik") String nik) {
        //Document temp = new Document();
        if (!refTroopsColl.find(eq("nik", nik)).equals(null)) {
            String gradeId = refTroopsColl.find(eq("nik", nik)).first().getString("grade_id");
            String finalGrade = refGradesColl.find(eq("grade_id", gradeId)).first().getString("grade_name");
            
            String status = refTroopsColl.find(eq("nik", nik)).first().getString("status");
            String finalStatus = refStatusColl.find(eq("status", status)).first().getString("status_name");
            String positionId = refTroopsColl.find(eq("nik", nik)).first().getString("position_id");
            String finalPosition = refPositionsColl.find(eq("position_id", positionId)).first().getString("position_name");
            
//            temp
//                    .append("nik", refTroopsColl.find(eq("nik", nik)).first().getString("nik"))
//                    .append("nik", refTroopsColl.find(eq("nik", nik)).first().getString("nik"))
//                    ;
        return new Employee(
                refTroopsColl.find(eq("nik", nik)).first().getString("nik"),
                refTroopsColl.find(eq("nik", nik)).first().getString("fullname"),
                refTroopsColl.find(eq("nik", nik)).first().getString("email_docotel"),
                refTroopsColl.find(eq("nik", nik)).first().getString("mobile_phone"),
                finalPosition,
                finalGrade,
                finalStatus
        );
        } else {
            return null;
        }
    }
}
