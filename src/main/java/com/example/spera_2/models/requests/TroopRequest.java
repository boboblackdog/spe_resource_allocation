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
    
    public String getDevice_id() { return device_id; }
    public void setDevice_id(String device_id) { this.device_id = device_id; }
    
//    public String getNikRequestor() { return nik_requestor; }
//    public void setNikRequestor(String nik_requestor) { this.nik_requestor = nik_requestor; }

    @Override
    public String toString() {
        return    "troops: " + this.troops + ",%0D%0A"
                + "device_id: " + this.device_id + "%0D%0A"
                ;
    }
    
    public Document toDocument() {
        return new Document()
//                .append("nik_requestor", nik_requestor)
                .append("troops", troops)
                .append("device_id", device_id)
                ;
    }
}
