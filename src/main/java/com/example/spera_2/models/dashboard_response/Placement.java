/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models.dashboard_response;

/**
 *
 * @author rakhadjo
 */
public class Placement {
    
    public String jakarta;
    public String yogyakarta;
    public String internship;
    
    public Placement() {}
    public Placement(String jakarta, String yogyakarta, String internship) {
        this.jakarta = jakarta;
        this.yogyakarta = yogyakarta;
        this.internship = internship;
    }
    
    public String getJakarta() { return jakarta; }
    public void setJakarta(String jakarta) { this.jakarta = jakarta; }
    
    public String getYogyakarta() { return yogyakarta; }
    public void setYogyakarta(String yogyakarta) { this.yogyakarta = yogyakarta; }
    
    public String getInternship() { return internship; }
    public void setInternship(String internship) { this.internship = internship; }
}
