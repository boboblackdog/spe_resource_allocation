/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

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

    public Employee(refTroops ref, boolean hardcode) {
        this.nik = ref.getNik();
        this.fullname = ref.getName();
        this.email = ref.getEmail();
        this.phone = ref.getMobile();
        if (hardcode) {
            //position
            switch (Integer.parseInt(ref.getPosId())) {
                case 1: this.position = "Mobile Android Developer"; break;
                case 2: this.position = "Mobile iOS Developer"; break;
                default: this.position = "Software Engineer"; break;
            }
            //grade
            switch (Integer.parseInt(ref.getGradeId())) {
                case 1: this.grade = "middle"; break;
                case 2: this.grade = "middle"; break;
                case 3: this.grade = "middle"; break;
                case 4: this.grade = "senior"; break;
                case 5: this.grade = "supervisor"; break;
                case 6: this.grade = "specialist"; break;
                case 7: this.grade = "head of supervisor"; break;
                default: this.grade = "junior"; break;
            }
            //status
            switch (Integer.parseInt(ref.getStat())) {
                case 1: this.status = "active"; break;
                case 2: this.status = "resigned"; break;
                default: this.status = "waiting"; break;
            }
        } else {
            
            MongoClient client = new MongoClient("localhost", 27017);
            MongoDatabase database = client.getDatabase("spera");
            
            MongoCollection<Document> refStatusColl = database.getCollection("refStatus");
            MongoCollection<Document> refGradesColl = database.getCollection("refGrades");
            MongoCollection<Document> refPositionsColl = database.getCollection("refPositions");
            
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
}
