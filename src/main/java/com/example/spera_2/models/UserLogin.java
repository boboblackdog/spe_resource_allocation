/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

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
    
    public String getNik() { return username; }
    public void setNik(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getDeviceId() { return device_id; }
    public void setDeviceId(String device_id) { this.device_id = device_id; }
}
