/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import com.example.spera_2.utils_config.MongoCompassConnection;
import com.mongodb.client.MongoCollection;
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
    
    public Employee(String nik) {
        this.nik = nik;
        
        MongoCompassConnection mcc = new MongoCompassConnection();
        Document refTroopsDocument = mcc.manualNikSearch(nik);
        this.fullname = refTroopsDocument.getString("fullname");
        this.email = refTroopsDocument.getString("email_docotel");
        this.phone = refTroopsDocument.getString("mobile_phone");
        
        MongoCollection<Document> refStatusColl = mcc.getColl("refStatus");
        MongoCollection<Document> refGradesColl = mcc.getColl("refGrades");
        MongoCollection<Document> refPositionsColl = mcc.getColl("refPositions");
        try {
            String finalStatus = refStatusColl.find(eq("status", refTroopsDocument.getString("status"))).first().getString("status_name");
            String finalGrade = refGradesColl.find(eq("grade_id", refTroopsDocument.getString("grade_id"))).first().getString("grade_name");
            String finalPosition = refPositionsColl.find(eq("position_id", refTroopsDocument.getString("position_id"))).first().getString("position_name");
            this.status = finalStatus;
            this.grade = finalGrade;
            this.position = finalPosition;
        } catch (Exception e) {
            this.status = this.grade = this.position = "null";
        }
        
    }

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
        this.fullname = ref.getFullname();
        this.email = ref.getEmail_docotel();
        this.phone = ref.getMobile_phone();
        
        MongoCompassConnection mcc = new MongoCompassConnection();
        MongoCollection<Document> refStatusColl = mcc.getColl("refStatus");
        MongoCollection<Document> refGradesColl = mcc.getColl("refGrades");
        MongoCollection<Document> refPositionsColl = mcc.getColl("refPositions");
        
        try {
            String finalStatus = refStatusColl.find(eq("status", ref.getStatus())).first().getString("status_name");
            String finalGrade = refGradesColl.find(eq("grade_id", ref.getGrade_id())).first().getString("grade_name");
            String finalPosition = refPositionsColl.find(eq("position_id", ref.getPosition_id())).first().getString("position_name");
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
