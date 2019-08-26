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
public class DashboardResponse {
    
    public String year;
    public String total_troops;
    
    public Document Placement;
    public Document Position;
    public Document Turnover;
    
    public DashboardResponse() {}
    public DashboardResponse(String year) {
        this.year = year;
        /*
        get placement, 
        position and 
        turnover data 
        from DB
        */
        this.Placement = new Document()
                .append("jakarta", null)
                .append("yogyakarta", null)
                .append("internship", null);
        this.Position = new Document()
                .append("software_engineer", null)
                .append("android_mobile_dev", null)
                .append("ios_mobile_dev", null)
                .append("systems_analyst", null);
        this.Turnover = new Document()
                .append("jan", null)
                .append("feb", null)
                .append("mar", null)
                .append("apr", null)
                .append("may", null)
                .append("jun", null)
                .append("jul", null)
                .append("aug", null)
                .append("sep", null)
                .append("oct", null)
                .append("nov", null)
                .append("dec", null);
        
    }
    
    public void replace(String entity, String key, String num) {
        if (entity.equalsIgnoreCase("placement")) {
            Placement.replace(key, num);
        } else if (entity.equalsIgnoreCase("position")) {
            Position.replace(key, num);
        } else if (entity.equalsIgnoreCase("turnover")){
            Turnover.replace(key, num);
        }
    }
    
    public Document toDocument() {
        return new Document()
                .append("year", year)
                .append("total_troops", total_troops)
                .append("placement", Placement)
                .append("position", Position)
                .append("turnover", Turnover)
                ;
    }
}
