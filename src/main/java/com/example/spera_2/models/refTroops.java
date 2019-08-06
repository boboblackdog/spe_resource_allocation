/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
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
    public refTroops(ObjectId _id, String fullName, String n_induk, String pos_id, String grade_id, String email, String mobile, String stat, String gend) {
        this._id = _id;
        this.fullname = fullName;
        this.nik = n_induk;
        this.position_id = pos_id;
        this.grade_id = grade_id;
        this.email_docotel = email;
        this.mobile_phone = mobile;
        this.status = stat;
        this.gender = gend;
    }
    
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    
    public String getName() { return fullname; }
    public void setName(String fullName) { this.fullname = fullName; }
    
    public String getNik() { return nik; }
    public void setNik(String n_induk) { this.nik = n_induk; }
    
    public String getPosId() { return position_id; }
    public void setPosId(String pos_id) { this.position_id = pos_id; }
    
    public String getGradeId() { return grade_id; }
    public void setGradeId(String gradeId) { this.grade_id = gradeId; }
    
    public String getEmail() { return email_docotel; }
    public void setEmail(String email) { this.email_docotel = email; }
    
    public String getMobile() { return mobile_phone; }
    public void setMobile(String mobile) { this.mobile_phone = mobile; }
    
    public String getStat() { return status; }
    public void setStat(String stat) { this.status = stat; }
    
    public String getGender() { return gender; }
    public void setGender(String gend) { this.gender = gend; }
}
