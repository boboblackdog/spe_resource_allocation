/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 *
 * @author rakhadjo
 */

@Entity
@Table(name = "user")
public class user {
    @Id
    @GeneratedValue
    private Integer id;
    @NotBlank
    private String nik;
    @NotBlank
    private String auth_key;
    @NotBlank
    private String password_hash;
    
    private String password_reset_token;
    @NotBlank
    private String email;
    @NotBlank
    private int status;
    @NotBlank
    private int created_at;
    @NotBlank
    private int updated_at;
    
    public user() {}
    public user(Integer id, String nik, String auth_key, String password_hash, String password_reset_token, String email, int status, int created_at, int updated_at) {
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
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getAuthKey() {return auth_key; }
    public void setAuthKey(String auth_key) { this.auth_key = auth_key; }
    
    public String getHashPw() { return password_hash; }
    public void setHashPw(String password_hash) { this.password_hash = password_hash; }
    
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
}
