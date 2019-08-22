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
public class Turnover {
    
    private String jan;
    private String feb;
    private String mar;
    private String apr;
    private String may;
    private String jun;
    private String jul;
    private String aug;
    private String sep;
    private String oct;
    private String nov;
    private String dec;
    
    public Turnover() {}

    public Document toDocument() {
        return new Document()
                .append("jan", jan)
                .append("feb", feb)
                .append("mar", mar)
                .append("apr", apr)
                .append("may", may)
                .append("jun", jun)
                .append("jul", jul)
                .append("aug", aug)
                .append("sep", sep)
                .append("oct", oct)
                .append("nov", nov)
                .append("dec", dec)
                ;
    }
    
    
}
