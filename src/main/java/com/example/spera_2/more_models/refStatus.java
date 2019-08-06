/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.more_models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
public class refStatus {
    @Id
    public ObjectId _id;
    public String status;
    public String status_name;
    
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; } 
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public String getStatusName() { return status_name; }
    public void setStatusName(String status_name) { this.status_name = status_name; }
    
    public refStatus() {}
    
    public refStatus(ObjectId _id, String status, String status_name) {
        this._id = _id;
        this.status = status;
        this.status_name = status_name;
    }
}
