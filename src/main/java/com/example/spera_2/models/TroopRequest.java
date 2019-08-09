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
public class TroopRequest {
    
    private String troops;
    private String device_id;
    
    public TroopRequest() {}
    public TroopRequest(String troops, String device_id) {
        this.troops = troops;
        this.device_id = device_id;
    }
    
    public String getTroops() { return troops; }
    public void setTroops(String troops) { this.troops = troops; }
    
    public String getDeviceId() { return device_id; }
    public void setDeviceId(String device_id) { this.device_id = device_id; }
}
