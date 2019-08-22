/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import com.example.spera_2.models.dashboard_response.*;
import org.bson.Document;
/**
 *
 * @author rakhadjo
 */
public class DashboardResponse {
    
    public String year;
    public String total_troops;
    
    public Placement placement;
    public Position position;
    public Turnover turnover;
    
    public DashboardResponse() {}
    public DashboardResponse(String year) {
        this.year = year;
        this.placement = new Placement();
        this.position = new Position();
        this.turnover = new Turnover();
        /*
        get placement, 
        position and 
        turnover data 
        from DB
        */
    }
    public Document toDocument() {
        return new Document()
                .append("year", year)
                .append("total_troops", total_troops)
                .append("placement", placement.toDocument())
                .append("position", position.toDocument())
                .append("turnover", turnover.toDocument())
                ;
    }
}
