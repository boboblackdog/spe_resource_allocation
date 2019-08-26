/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import com.example.spera_2.Spera2Application;
import com.example.spera_2.models.requests.UserLoginRequest;
import com.example.spera_2.utils_config.MySQLConnection;
import com.example.spera_2.utils_config.Utils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author rakhadjo
 */
public class UserBearer {
    
    Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    
    private Integer id;
    private String nik;
    private String bearer_token;
    private String device_id;
    private Timestamp datetime_created;
    private Timestamp datetime_expired;
    MySQLConnection connection;
    
    public UserBearer() throws SQLException { 
        this.connection = new MySQLConnection();
    }
    
    public UserBearer(UserLoginRequest ulr, String Authentication) throws SQLException {
        this.nik = ulr.getUsername();
        this.bearer_token = Authentication;
        this.device_id = ulr.getDevice_id();
        this.connection = new MySQLConnection();
    }
    public UserBearer(Integer id, String nik, String bearer_token, String device_id, Timestamp datetime_created, Timestamp datetime_expired) throws SQLException {
        this.id = id;
        this.nik = nik;
        this.bearer_token = bearer_token;
        this.device_id = device_id;
        this.datetime_created = datetime_created;
        this.datetime_expired = datetime_expired;
        this.connection = new MySQLConnection();
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getBearer_token() { return bearer_token; }
    public void setBearer_token(String bearer_token) { this.bearer_token = bearer_token; }
    
    public String getDevice_id() { return device_id; }
    public void setDevice_id(String device_id) { this.device_id = device_id; }
    
    public Timestamp getDatetime_created() { return datetime_created; }
    public void setDatetime_created(Timestamp datetime_created) { this.datetime_created = datetime_created; }
    
    public Timestamp getDatetime_expired() { return datetime_expired; }
    public void setDatetime_Expired(Timestamp datetime_expired) { this.datetime_expired = datetime_expired; }
    
    public String insertIntoUserBearer() throws SQLException {
        logger.info("Building new bearer token...");
        String newToken = Utils.buildToken();
        logger.info("...bearer token generated");
        String sql = "INSERT INTO "
                + "`user_bearer` "
                + "(nik, bearer_token, device_id, datetime_created, datetime_expired)"
                + "VALUES "
                + "(?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE));";
        logger.info("preparing statement...");
        PreparedStatement pstm = this.connection.prepareStmt(sql);
        logger.info("...statement prepared");
        logger.info("setting values...");
        pstm.setString(1, this.nik);
        pstm.setString(2, newToken);
        pstm.setString(3, this.device_id);
        pstm.executeUpdate();
        logger.info("insert to `user_bearer` table success");
        return newToken;
    }
    
    public void extendLoginSession() throws SQLException {
        String sql = "UPDATE "
                + "`user_bearer` "
                + "SET "
                + "datetime_expired = DATE_ADD(NOW(), INTERVAL 30 MINUTE) "
                + "WHERE "
                + "nik = ? AND bearer_token = ?;";
        PreparedStatement pstm = this.connection.prepareStmt(sql);
        pstm.setString(1, this.nik);
        pstm.setString(2, this.bearer_token);
        pstm.executeUpdate();
    }
    
    public boolean sessionActive() throws SQLException {
        String sql = "SELECT "
                + "TIMESTAMPDIFF(MINUTE, datetime_created, NOW()) "
                + "AS difference "
                + "FROM `user_bearer` "
                + "WHERE "
                + "nik = ? AND bearer_token = ?;";
        PreparedStatement pstm = this.connection.prepareStmt(sql);
        logger.info("this.nik: " + this.nik );
        logger.info("this.bearer_token: " + this.bearer_token);
        pstm.setString(1, this.nik);
        pstm.setString(2, this.bearer_token);
        ResultSet rslt = pstm.executeQuery();
        while (rslt.next()) {
            return rslt.getInt("difference") < 30;
        }
        return false;
    }
}
