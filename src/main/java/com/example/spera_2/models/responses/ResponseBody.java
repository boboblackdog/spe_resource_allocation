/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models.responses;

import com.example.spera_2.utils_config.RC;
import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class ResponseBody {
    
    private String rc;
    private String message;
    private String action;
    
    //CHANGE THESE TO PRIVATE ONCE MODIFIABLE
    public String role;
    public String menu;
    //CHANGE THESE TO PRIVATE ONCE MODIFIABLE
    
    private Object data;
    private boolean log_success;
    
    public ResponseBody() {
        this.rc = "00";
        this.message = "success";
        this.role = "admin";
        this.menu = "home, troops, account";
    }
    
    public ResponseBody(RC responseCode) {
        this.rc = responseCode.getCODE();
        this.message = responseCode.getMESSAGE();
        this.role = "admin";
        this.menu = "home, troops, account";
    }
    
    public ResponseBody(String rc, String message) {
        this.rc = rc;
        this.message = message;
        this.role = "admin";
        this.menu = "home, troops, account";
        this.data = new Document();
    }
    
    public ResponseBody(String rc, String message, Document data) {
        this.rc = rc;
        this.message = message;
        this.role = "admin";
        this.menu = "home, troops, account";
        this.data = data;
    }
    
    public String getRC() { return this.rc; }
    public void setRC(String rc) { this.rc = rc; }
    
    public String getMessage() { return this.message; }
    public void setMessage(String message) { this.message = message; }
    
    public String getAction() { return this.action; }
    public void setAction(String action) { this.action = action; }
    
    public boolean getLog_success() { return log_success; }
    public void setLog_success(boolean log_success) { this.log_success = log_success; }
    
    //public void append(String key, Object value) { this.data.append(key, value); }

    public void setData(Object data) { this.data = data; }
    public Object getData() { return this.data; }
    
    public Document toDocument() {
        Document responseBody = new Document()
                .append("rc", this.rc)
                .append("message", this.message)
                .append("role", this.role)
                .append("menu", this.menu)
                ;
        if (this.data != null) {
            try {
                responseBody.append("data", (Document) this.data);
            } catch (Exception e) {
                responseBody.append("data", "casting object in development...");
            }
        }
        return responseBody;
    }
    
}


