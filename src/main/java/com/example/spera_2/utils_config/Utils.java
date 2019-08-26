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
public class Utils {
    
    public Utils() {}
    
    public String SUCCESSFUL_QUERY = "00";
    
    
    private static final String set = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuv";
    public static String buildToken() {
        StringBuilder sb = new StringBuilder()
                ;
        for (int i = 0; i < 32; i++) {
            int charPosition = (int)(Math.random()*set.length())
                    ;
            sb.append(set.charAt(charPosition))
                    ;
        } return sb.toString()
                ;
    }
    
}
