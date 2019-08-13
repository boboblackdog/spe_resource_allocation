/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import com.example.spera_2.models.dashboard_response.*;
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
        /*
        get placement, 
        position and 
        turnover data 
        from DB
        */
    }
}
