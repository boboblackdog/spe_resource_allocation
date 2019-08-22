/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class UserLogin {
    
    private String username;
    private String password;
    private String device_id;
    
    public UserLogin() {}
    public UserLogin(String username, String password, String device_id) {
        this.username = username;
        this.password = password;
        this.device_id = device_id;
    }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getDeviceId() { return device_id; }
    public void setDeviceId(String device_id) { this.device_id = device_id; }

    public Document toDocument() {
        return new Document()
                .append("username", username)
                .append("password", password)
                .append("device_id", device_id);
    }
}
