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
public class DashboardRequest {
    
    private String year;
    private String device_id;
    
    public DashboardRequest() {}
    public DashboardRequest(String year, String device_id) {
        this.year = year;
        this.device_id = device_id;
    }
    
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    
    public String getDeviceId() { return device_id; }
    public void setDeviceId(String device_id) { this.device_id = device_id; }
}
