/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import java.sql.Timestamp;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
public class user_bearer {
    @Id
    private Integer id;
    private String nik;
    private String bearer_token;
    private String device_id;
    private Timestamp datetime_created;
    private Timestamp datetime_expired;
    
    public user_bearer() {}
    public user_bearer(Integer id, String nik, String bearer_token, String device_id, Timestamp datetime_created, Timestamp datetime_expired) {
        this.nik = nik;
        this.bearer_token = bearer_token;
        this.device_id = device_id;
        this.datetime_created = datetime_created;
        this.datetime_expired = datetime_expired;
    }
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getBearerToken() { return bearer_token; }
    public void setBearerToken(String bearer_token) { this.bearer_token = bearer_token; }
    
    public String getDeviceId() { return device_id; }
    public void setDeviceId(String device_id) { this.device_id = device_id; }
    
    public Timestamp getCreated() { return datetime_created; }
    public void setCreated(Timestamp datetime_created) { this.datetime_created = datetime_created; }
    
    public Timestamp getExpired() { return datetime_expired; }
    public void setExpired(Timestamp datetime_expired) { this.datetime_expired = datetime_expired; }
}
