/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
//@Document(collection = "refTroops")
public class refTroops {
    
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
    
    public refTroops() {}
    public refTroops(ObjectId _id, String fullname, String nik, String position_id, String grade_id, String email_docotel, String mobile_phone, String status, String gender) {
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
    
    public refTroops(Document docx) {
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
    
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    
    public String getFullname() { return fullname; }
    public void setFullname(String fullName) { this.fullname = fullName; }
    
    public String getNik() { return nik; }
    public void setNik(String n_induk) { this.nik = n_induk; }
    
    public String getPosition_id() { return position_id; }
    public void setPosition_id(String pos_id) { this.position_id = pos_id; }
    
    public String getGrade_id() { return grade_id; }
    public void setGrade_id(String gradeId) { this.grade_id = gradeId; }
    
    public String getEmail_docotel() { return email_docotel; }
    public void setEmail_docotel(String email) { this.email_docotel = email; }
    
    public String getMobile_phone() { return mobile_phone; }
    public void setMobile_phone(String mobile) { this.mobile_phone = mobile; }
    
    public String getStatus() { return status; }
    public void setStatus(String stat) { this.status = stat; }
    
    public String getGender() { return gender; }
    public void setGender(String gend) { this.gender = gend; }
    
    public Document toDocument() {
        return new Document()
                .append("fullname", this.fullname)
                .append("nik", this.nik)
                .append("position_id", this.position_id)
                .append("grade_id", this.grade_id)
                .append("email_docotel", this.email_docotel)
                .append("mobile_phone", this.mobile_phone)
                .append("status", this.status)
                .append("gender", this.gender)
                ;
    }
}
