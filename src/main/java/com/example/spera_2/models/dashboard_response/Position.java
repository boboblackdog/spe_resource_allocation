/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models.dashboard_response;

import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class Position {
    
    private String software_engineer;
    private String android_mobile_dev;
    private String ios_mobile_dev;
    private String systems_analyst;
    
    public Position() {}
    
    public String getSENG() { return software_engineer; }
    public void setSENG(String software_engineer) { this.software_engineer = software_engineer; }
    
    public String getAMD() { return android_mobile_dev; }
    public void setAMD(String android_mobile_dev) { this.android_mobile_dev = android_mobile_dev; }
    
    public String getIMD() { return ios_mobile_dev; }
    public void setIMD(String ios_mobile_dev) { this.ios_mobile_dev = ios_mobile_dev; }
    
    public String getSA() { return systems_analyst; }
    public void setSA(String systems_analyst) { this.systems_analyst = systems_analyst; }

    public Document toDocument() {
        return new Document()
                .append("software_engineer", software_engineer)
                .append("android_mobile_dev", android_mobile_dev)
                .append("ios_mobile_dev", ios_mobile_dev)
                .append("systems_analyst", systems_analyst)
                ;
    }
}
