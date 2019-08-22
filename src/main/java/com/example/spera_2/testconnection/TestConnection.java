/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.testconnection;

import java.sql.SQLException;
import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class TestConnection {
    
    private static MySQLConnection cc;
    private static MongoCompassConnection mc;
    
    public TestConnection() throws SQLException {
        
        cc = new MySQLConnection();
        mc = new MongoCompassConnection();
        
    }
    
    public boolean ccExists() {
        return cc.configFileExists();
    }
    
    public boolean mcExists() {
        return mc.configFileExists();
    }
    
    public Document test() throws SQLException {
        return new Document()
                .append("SQL Connected", cc.testDBConnection())
                .append("MongoDB Connected", mc.testDBConnection())
                ;
    }
}
