/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

/**
 *
 * @author rakhadjo
 */
public class NikRequest {
    private String nik;
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public NikRequest() {}
    public NikRequest(String nik) { this.nik = nik; }
}
