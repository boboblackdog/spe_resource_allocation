/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models.responses;

import com.example.spera_2.utils_config.RC;
import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class DashboardResponse extends ResponseBody {
    
    private Document data;
    
    public DashboardResponse(RC r) {
        super(r);
    }
}
