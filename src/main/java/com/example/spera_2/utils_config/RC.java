/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.utils_config;

/**
 *
 * @author rakhadjo
 */
public class RC {
    
    private String CODE;
    private String MESSAGE;
    private String[] msgArr = 
    {
    "success",                              //00
    "please fill in all the blanks",        //01
    "troops request != \"get all \"",       //02
    "",                                     //03
    "",                                     //04
    "",                                     //05
    "",                                     //06
    "",                                     //07
    "",                                     //08
    "",                                     //09
    "data doesn't exist",                   //10
    "invalid request format",               //11
    "undefined error"                       //12                   
    };
    
    public RC() {}
    public RC(String CODE) {
        this.CODE = CODE;
        this.MESSAGE = msgArr[Integer.parseInt(CODE)];
    }
    public RC(String CODE, String MESSAGE) {
        this.CODE = CODE;
        this.MESSAGE = MESSAGE;
    }
    
    public String getCODE() { return CODE; }
    public void setCODE(String CODE) { this.CODE = CODE; }
    
    public String getMESSAGE() { return MESSAGE; }
    public void setMESSAGE(String MESSAGE) { this.MESSAGE = MESSAGE; }
    
}
