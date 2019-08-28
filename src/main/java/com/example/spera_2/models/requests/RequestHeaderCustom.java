/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models.requests;

/**
 *
 * @author rakhadjo
 */
public class RequestHeaderCustom {
    
    public String Authentication;
    
    public RequestHeaderCustom() {}
    public RequestHeaderCustom(String Authentication) { this.Authentication = Authentication; }
    
    @Override
    public String toString() {
        return "Authentication: " + this.Authentication + "%0D%0A";
    }
    
    public String getAuthentication() { return Authentication; }
    public void setAuthentication(String Authentication) { this.Authentication = Authentication; }
}
