/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models.requests;

import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class DashboardRequest {
    
    private String nik_requestor;
    private String year;
    private String device_id;
    
    public DashboardRequest() {}
    public DashboardRequest(String nik_requestor, String device_id, String year) {
        this.nik_requestor = nik_requestor;
        this.year = year;
        this.device_id = device_id;
    }
    
    public String getYear() { return year; }
    public void setYear(String year) { this.year = year; }
    
    public String getDeviceId() { return device_id; }
    public void setDeviceId(String device_id) { this.device_id = device_id; }
    
    public String getNikRequestor() { return nik_requestor; }
    public void setNikRequestor(String nik_requestor) { this.nik_requestor = nik_requestor; }
    
    public Document toDocument() {
        return new Document()
                .append("nik_requestor", nik_requestor)
                .append("year", year)
                .append("device_id", device_id)
                ;
    }
}
