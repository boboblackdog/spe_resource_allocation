/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import com.example.spera_2.testconnection.MongoCompassConnection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class Employee {

    private String nik;
    private String fullname;
    private String email;
    private String phone;
    private String position;
    private String grade;
    private String status;

    public Employee() {}

    public Employee(String nik, String fullname, String email, String phone, String position, String grade, String status) {
        this.nik = nik;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.position = position;
        this.grade = grade;
        this.status = status;
    }

    public Employee(refTroops ref) {
        this.nik = ref.getNik();
        this.fullname = ref.getName();
        this.email = ref.getEmail();
        this.phone = ref.getMobile();
        
        MongoCompassConnection mcc = new MongoCompassConnection();
        MongoCollection<Document> refStatusColl = mcc.getColl("refStatus");
        MongoCollection<Document> refGradesColl = mcc.getColl("refGrades");
        MongoCollection<Document> refPositionsColl = mcc.getColl("refPositions");
        
        try {
            String finalStatus = refStatusColl.find(eq("status", ref.getStat())).first().getString("status_name");
            String finalGrade = refGradesColl.find(eq("grade_id", ref.getGradeId())).first().getString("grade_name");
            String finalPosition = refPositionsColl.find(eq("position_id", ref.getPosId())).first().getString("position_name");
            this.status = finalStatus;
            this.grade = finalGrade;
            this.position = finalPosition;
        } catch (Exception e) {
            this.status = this.grade = this.position = "null";
        }
    }
    
    public Employee (Document refTroopsDocument) {
        this.nik = refTroopsDocument.getString("nik");
        this.fullname = refTroopsDocument.getString("fullname");
        this.email = refTroopsDocument.getString("email");
        this.phone = refTroopsDocument.getString("mobile_docotel");
        
        MongoCompassConnection mcc = new MongoCompassConnection();
        MongoCollection<Document> refStatusColl = mcc.getColl("refStatus");
        MongoCollection<Document> refGradesColl = mcc.getColl("refGrades");
        MongoCollection<Document> refPositionsColl = mcc.getColl("refPositions");
        
        try {
            String finalStatus = refStatusColl.find(eq("status", refTroopsDocument.getString("status"))).first().getString("status_name");
            String finalGrade = refGradesColl.find(eq("grade_id", refTroopsDocument.getString("grade_id"))).first().getString("grade_name");
            String finalPosition = refPositionsColl.find(eq("position_id", refTroopsDocument.getString("position_id"))).first().getString("grade_name");
            this.status = finalStatus;
            this.grade = finalGrade;
            this.position = finalPosition;
        } catch (Exception e) {
            this.status = this.grade = this.position = "null";
        }
    }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public String getFullname() { return fullname; }
    public void setFullName(String fullname) { this.fullname = fullname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Document toDocument() {
        return new Document()
                .append("nik", nik)
                .append("fullname", fullname)
                .append("email", email)
                .append("phone", phone)
                .append("position", position)
                .append("grade", grade)
                .append("status", status)
                ;
    }
}
