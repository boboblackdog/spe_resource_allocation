/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.more_models;

import java.security.Timestamp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
public class configs {
    @Id
    ObjectId _id;
    public String app_fullname;
    public String app_alias;
    public String app_creator;
    public String app_creator_alias;
    public Timestamp inserted_datetime;
    
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    
    public String getAppFullname() { return app_fullname; }
    public void setAppFullname(String app_fullname) { this.app_fullname = app_fullname; }
    
    public String getAppAlias() { return app_alias; }
    public void setAppAlias(String app_alias) { this.app_alias = app_alias; }
    
    public String getAppCreator() { return app_creator; }
    public void setAppCreator(String app_creator) { this.app_creator = app_creator; }
    
    public String getAppCreatorAlias() { return app_creator_alias; }
    public void setAppCreatorAlias(String app_creator_alias) { this.app_creator_alias = app_creator_alias; }
    
    public Timestamp getInsertedDatetime() { return inserted_datetime; }
    public void setInsertedDatetime(Timestamp inserted_datetime) { this.inserted_datetime = inserted_datetime; }
    
    public configs() {}
    
    public configs(
            ObjectId _id, 
            String app_fullname, 
            String app_alias, 
            String app_creator, 
            String app_creator_alias, 
            Timestamp inserted_datetime) {
        this._id = _id;
        this.app_fullname = app_fullname;
        this.app_alias = app_alias;
        this.app_creator = app_creator;
        this.app_creator_alias = app_creator_alias;
        this.inserted_datetime = inserted_datetime;
    }
    
}
