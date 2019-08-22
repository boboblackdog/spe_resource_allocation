/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.more_models;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
public class refPositions {
    
    @Id
    public ObjectId _id;
    public String position_id;
    public String position_name;
    
    public refPositions() {}
    public refPositions(ObjectId _id, String position_id, String position_name) {
        this.position_id = position_id;
        this.position_name = position_name;
        this._id = _id;
    }
    
    public String getPositionId() { return position_id; }
    public void setPositionId(String position_id) { this.position_id = position_id; }
    
    public String getPositionName() { return position_name; }
    public void setPositionName(String position_name) { this.position_name = position_name; }
    
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; } 
    
    public Document toDocument() {
        return new Document()
                .append("position_id", position_id)
                .append("position_name", position_name);
    }
}
