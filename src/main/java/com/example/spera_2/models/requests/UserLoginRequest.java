/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models.requests;

import com.example.spera_2.Spera2Application;
import java.sql.SQLException;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author rakhadjo
 */
public class UserLoginRequest {
    
    private String username; //THIS EQUALS THE NIK
    private String password;
    private String device_id;
    
    Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    /*
    verifies whether username is valid
    */
    public boolean usernameVerifiable() {
        logger.info("verifying NIK...");
        try {
            Integer.parseInt(this.username);
            return this.username.length() == 8;
        } catch (Exception e) {
            logger.error("number format exception");
            return false;
        }
    }
    
    //implement more verifiable methods here
    
    public UserLoginRequest() {}
    public UserLoginRequest(String username, String password, String device_id) throws SQLException {
        this.username = username;
        this.password = password;
        this.device_id = device_id;
    }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getDevice_id() { return device_id; }
    public void setDevice_id(String device_id) { this.device_id = device_id; }
    
    @Override
    public String toString() {
        return    "username: " + this.username + ",%0D%0A"
                + "password: " + this.password + ",%0D%0A"
                + "device_id: " + this.device_id + "%0D%0A"
                ;
    }
    
    public Document toDocument() {
        return new Document()
                .append("username", username)
                .append("password", password)
                .append("device_id", device_id);
    }
}
