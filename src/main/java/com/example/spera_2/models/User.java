/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import com.example.spera_2.models.requests.UserLoginRequest;
import com.example.spera_2.utils_config.MySQLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author rakhadjo
 */

//@Entity
//@Table(name = "user")
public class User {
    //@Id
    //@GeneratedValue
    private Integer id;
    //@NotBlank
    private String nik;
    //@NotBlank
    private String auth_key;
    //@NotBlank
    private String password_hash;
    
    private String password_reset_token;
    //@NotBlank
    private String email;
    //@NotBlank
    private int status;
    //@NotBlank
    private int created_at;
    //@NotBlank
    private int updated_at;
    
    MySQLConnection connection;
    
    public User() throws SQLException {
        this.connection = new MySQLConnection();
    }
    
    public User(Integer id, String nik, String auth_key, String password_hash, String password_reset_token, String email, int status, int created_at, int updated_at) throws SQLException {
        this.connection = new MySQLConnection();
        this.id = id;
        this.nik = nik;
        this.auth_key = auth_key;
        this.password_hash = password_hash;
        this.password_reset_token = password_reset_token;
        this.email = email;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
    
    public User(UserLoginRequest ulr) throws SQLException {
        this.connection = new MySQLConnection();
        this.nik = ulr.getUsername();
        this.password_hash = ulr.getPassword();
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getAuthKey() {return auth_key; }
    public void setAuthKey(String auth_key) { this.auth_key = auth_key; }
    
    public String getPwHash() { return password_hash; }
    public void setPwHash(String password_hash) { this.password_hash = password_hash; }
    
    public String getPwResetToken() { return password_reset_token; }
    public void setPwResetToken(String password_reset_token) { this.password_reset_token = password_reset_token; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }
    
    public int getCreatedAt() { return created_at; }
    public void setCreatedAt(int created_at) { this.created_at = created_at; }
    
    public int getUpdatedAt() { return updated_at; }
    public void setUpdatedAt(int updated_at) { this.updated_at = updated_at; }
    
    /*
    checks whether user object exists in user table
    */
    public boolean existsInUserTable() throws SQLException {
        String sql = "SELECT * FROM `user` WHERE "
                + "username = ? AND password_hash = ?;";
        PreparedStatement pstm = connection.prepareStmt(sql);
        pstm.setString(1, this.nik);
        pstm.setString(2, this.password_hash);
        ResultSet rslt = pstm.executeQuery();
        while (rslt.next()) {
            if (rslt.getString("username").equals(this.nik) && rslt.getString("password_hash").equals(this.password_hash)) {
                return true;
            }
        } return false;
    }
}
