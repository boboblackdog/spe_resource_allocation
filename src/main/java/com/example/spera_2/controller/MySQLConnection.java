/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controller;

import com.example.spera_2.models.UserLogin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author rakhadjo
 */
public class MySQLConnection {
    
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    
    //change this to the actual server later on
    private static final String CONN_STRING = "jdbc:mysql://localhost:3306/spera_portal?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    
    private Connection conn;
    
    public MySQLConnection() throws SQLException {
        
        this.conn = DriverManager.getConnection(CONN_STRING, USERNAME, PASSWORD);
        
    }
    
    /*
    provoked to check whether user with given credentials exist in the user table
    PASSWORD PASSED TO BE HASHED WITH LATER LIBRARY
    */
    public boolean existsInUserTable(UserLogin ul) throws SQLException {
        String sql = "SELECT * FROM user;";
        Statement stmt = conn.createStatement();
        ResultSet rslt = stmt.executeQuery(sql);
        while (rslt.next()) {
            if (rslt.getString("nik").equals(ul.getNik()) && rslt.getString("password_hash").equals(ul.getPassword())) {
                return true;
            }
        } return false;
    }
    
    /*
    provoked to check whether user has an active session in the table
    */
    public boolean sessionActive(UserLogin ul, String auth) throws SQLException {
        String sql = "SELECT TIMESTAMPDIFF(MINUTE, datetime_created, NOW()) AS difference FROM user_bearer WHERE "
                + "nik = ? AND bearer_token = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, ul.getNik());
        pstm.setString(2, auth);
        ResultSet rslt = pstm.executeQuery();
        while (rslt.next()) {
            return rslt.getInt("difference") < 30;
        }
        return false;
    }
    
    /*
    provoked when user exists, but there are no open sessions for that user
    */
    public void insertIntoUserBearer(UserLogin ul, String newToken) throws SQLException {
        
        String sql = "INSERT INTO "
                + "`user_bearer` "
                + "(nik, bearer_token, device_id, datetime_created, datetime_expired)"
                + "VALUES "
                + "(?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE));";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, ul.getNik());
        pstm.setString(2, newToken);
        pstm.setString(3, ul.getDeviceId());
        pstm.executeUpdate();
    }
    
    /*
    provoked to extend expiration time when a user logs in within the 30 minute timeframe
    */
    public void extendLoginSession(UserLogin ul, String auth) throws SQLException {
        String sql = "UPDATE `user_bearer` "
                + "SET "
                + "datetime_expired = DATE_ADD(NOW(), INTERVAL 30 MINUTE) "
                + "WHERE "
                + "nik = ? AND bearer_token = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, ul.getNik());
        pstm.setString(2, auth);
        pstm.executeUpdate();
    }
    
    /*
    closes the current connection
    */
    public void closeCurrentConnection() throws SQLException {
        
        this.conn.close();
        
    }
    
}
