/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import java.sql.Timestamp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author rakhadjo
 */
@Document(collection = "refTroops")
public class Troop {
    
    @Id
    private ObjectId _id;
    private String fullname;
    private String nik;
    private String position_id;
    private String grade_id;
    private String email_docotel;
    private String mobile_phone;
    private String status;
    private String gender;
    private Timestamp datetime_inserted;
    
    public Troop() {}
    public Troop(ObjectId _id, String fullname, String nik, String position_id, String grade_id, String email_docotel, String mobile_phone, String status, String gender) {
        this._id = _id;
        this.fullname = fullname;
        this.nik = nik;
        this.position_id = position_id;
        this.grade_id = grade_id;
        this.email_docotel = email_docotel;
        this.mobile_phone = mobile_phone;
        this.status = status;
        this.gender = gender;
    }
    
    public Troop(org.bson.Document docx) {
        this.fullname = docx.getString("fullname");
        this.nik = docx.getString("nik");
        this.position_id = docx.getString("position_id");
        this.grade_id = docx.getString("grade_id");
        this.email_docotel = docx.getString("email_docotel");
        this.mobile_phone = docx.getString("mobile_phone");
        this.status = docx.getString("status");
        this.gender = docx.getString("gender");
    }
    
    public boolean containsNull() {
        
        return (fullname == null || nik == null ||
                position_id == null || grade_id == null ||
                email_docotel == null || mobile_phone == null ||
                status == null || gender == null
                );
        
    }
    
    public boolean goodRequestBody() {
        
        try {
            return  //nik isn't already in the database
                    (email_docotel.contains("@") && email_docotel.contains(".")) && //use regex later on in the future
                    (mobile_phone.substring(0, 1).equalsIgnoreCase("0")) &&
                    (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female")) &&
                    (Integer.parseInt(position_id) >= 0 && Integer.parseInt(position_id) <= 2) &&
                    (Integer.parseInt(grade_id) >= 0 && Integer.parseInt(grade_id) <= 6) &&
                    (Integer.parseInt(status) >= 0 && Integer.parseInt(status) <= 2)
                    ;
        } catch (Exception e) {
            return false;
        }
        
    }
    
    public ObjectId get_id() { return this._id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    
    public String getFullname() { return this.fullname; }
    public void setFullname(String fullName) { this.fullname = fullName; }
    
    public String getNik() { return this.nik; }
    public void setNik(String n_induk) { this.nik = n_induk; }
    
    public String getPosition_id() { return this.position_id; }
    public void setPosition_id(String pos_id) { this.position_id = pos_id; }
    
    public String getGrade_id() { return this.grade_id; }
    public void setGrade_id(String gradeId) { this.grade_id = gradeId; }
    
    public String getEmail_docotel() { return this.email_docotel; }
    public void setEmail_docotel(String email) { this.email_docotel = email; }
    
    public String getMobile_phone() { return this.mobile_phone; }
    public void setMobile_phone(String mobile) { this.mobile_phone = mobile; }
    
    public String getStatus() { return this.status; }
    public void setStatus(String stat) { this.status = stat; }
    
    public String getGender() { return this.gender; }
    public void setGender(String gend) { this.gender = gend; }
    
    public Timestamp getDatetime_Inserted() { return this.datetime_inserted; }
    public void setDatetime_Inserted(Timestamp datetime_inserted) {this.datetime_inserted = datetime_inserted; }
    
    @Override
    public String toString() {
        return    "fullname: " + this.fullname + ",%0D%0A"
                + "nik: " + this.nik + ",%0D%0A"
                + "position_id: " + this.position_id + ",%0D%0A"
                + "grade_id: " + this.grade_id + ",%0D%0A"
                + "email_docotel: " + this.email_docotel + ",%0D%0A"
                + "mobile_phone: " + this.mobile_phone + ",%0D%0A"
                + "status: " + this.status + ",%0D%0A"
                + "gender: " + this.gender + "%0D%0A"
                ;
    }
    
    public org.bson.Document toDocument() {
        return new org.bson.Document()
                .append("fullname", this.fullname)
                .append("nik", this.nik)
                .append("position_id", this.position_id)
                .append("grade_id", this.grade_id)
                .append("email_docotel", this.email_docotel)
                .append("mobile_phone", this.mobile_phone)
                .append("status", this.status)
                .append("gender", this.gender)
                .append("datetime_inserted", this.datetime_inserted)
                ;
    }
}
