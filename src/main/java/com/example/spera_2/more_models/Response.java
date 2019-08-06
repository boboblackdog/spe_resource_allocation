/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.more_models;

import com.example.spera_2.models.Employee;

/**
 *
 * @author rakhadjo
 */
public class Response {
    
    public String rc;
    public String message;
    public Employee data;
    
    public String getRc() { return rc; }
    public void setRc(String rc) { this.rc = rc; }
    
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    
    public Employee getEmployee() { return data; }
    public void setEmployee(Employee emp) { this.data = emp; }
    
    public Response() {}
    
    public Response(String rc, String message) {
        this.rc = rc;
        this.message = message;
    }
    
    public Response(String rc, String message, Employee emp) {
        this.rc = rc;
        this.message = message;
        this.data = emp;
    }
}
