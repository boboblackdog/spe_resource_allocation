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
public class NikRequest {
    
    private String nik;
    private String device_id;
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getDeviceId() { return device_id; }
    public void setDeviceId(String device_id) { this.device_id = device_id; }
    
    public NikRequest() {}
    public NikRequest(String nik, String device_id) {
        this.nik = nik;
        this.device_id = device_id;
    }
}
