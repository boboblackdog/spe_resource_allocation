/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.log;

import java.sql.Timestamp;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
public class UserProfileLogEntry {
    @Id
    private ObjectId _id;
    private String client_ip;
    private org.bson.Document request_body;
    private org.bson.Document request_header;
    private org.bson.Document response_body;
    private org.bson.Document response_header;
    private String nik;
    private Timestamp request_datetime;
    private Timestamp response_datetime;
    private long elapsed_time;
    private boolean is_error;
    private Exception exception;

    public UserProfileLogEntry() {
    }
    
    public org.bson.Document toJSON() {
        return new org.bson.Document()
                .append("_id", this._id)
                .append("client_ip", this.client_ip)
                .append("request_body", this.request_body)
                .append("request_header", this.request_header)
                .append("response_body", this.response_body)
                .append("response_header", this.response_header)
                .append("nik", this.nik)
                .append("request_datetime", this.request_datetime)
                .append("response_datetime", this.response_datetime)
                .append("elapsed_time", this.elapsed_time)
                .append("is_error", this.is_error)
                .append("exception", this.exception);
    }

    public ObjectId getId() {
        return _id;
    }

    public void setId(ObjectId _id) {
        this._id = _id;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public org.bson.Document getRequest_body() {
        return request_body;
    }

    public void setRequest_body(org.bson.Document request_body) {
        this.request_body = request_body;
    }

    public org.bson.Document getRequest_header() {
        return request_header;
    }

    public void setRequest_header(org.bson.Document request_header) {
        this.request_header = request_header;
    }

    public org.bson.Document getResponse_body() {
        return response_body;
    }

    public void setResponse_body(org.bson.Document response_body) {
        this.response_body = response_body;
    }

    public org.bson.Document getResponse_header() {
        return response_header;
    }

    public void setResponse_header(org.bson.Document response_header) {
        this.response_header = response_header;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Timestamp getRequest_datetime() {
        return request_datetime;
    }

    public void setRequest_datetime(Timestamp request_datetime) {
        this.request_datetime = request_datetime;
    }

    public Timestamp getResponse_datetime() {
        return response_datetime;
    }

    public void setResponse_datetime(Timestamp response_datetime) {
        this.response_datetime = response_datetime;
    }

    public long getElapsed_time() {
        return elapsed_time;
    }

    public void setElapsed_time(long elapsed_time) {
        this.elapsed_time = elapsed_time;
    }

    public boolean isIs_error() {
        return is_error;
    }

    public void setIs_error(boolean is_error) {
        this.is_error = is_error;
    }
    
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
