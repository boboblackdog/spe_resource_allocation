/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import com.example.spera_2.utils_config.MongoCompassConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
public class Report {
    
    @Id
    private ObjectId _id;
    private String nik;
    private String report_datetime;
    private String description;
    private String projects_involved;
    
    MongoCompassConnection mcc;
    
    private SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    
    public Report() {}
    public Report(String nik, Date report_datetime, String description, String projects_involved) {
        this.nik = nik;
        this.report_datetime = fmt.format(report_datetime);
        this.description = description;
        this.projects_involved = projects_involved;
    }
    public Report(ObjectId _id, String nik, Date report_datetime, String description, String projects_involved) {
        this._id = _id;
        this.nik = nik;
        this.report_datetime = fmt.format(report_datetime);
        this.description = description;
        this.projects_involved = projects_involved;
    }
    
    public boolean nikVerifiable() {
        try {
            Integer.parseInt(nik);
            return (nik.length() == 8);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    public boolean noneNull() {
        try {
            return  (
                    nik != null && report_datetime != null &&
                    description != null && projects_involved != null
                    );
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return    "nik: " + this.nik + ",%0D%0A"
                + "report_datetime: " + this.report_datetime + ",%0D%0A"
                + "description: " + this.description + ",%0D%0A"
                + "projects_involved: " + this.projects_involved + "%0D%0A";
    }
    
    public void manualInsert() {
        mcc = new MongoCompassConnection();
        mcc.manualInsertCore(
                new Document()
                        .append("nik", this.nik)
                        .append("report_datetime", report_datetime)
                        .append("description", this.description)
                        .append("projects_involved", this.projects_involved)
                , "troops_report");
    }
    
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; }
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getReport_datetime() { return report_datetime; }
    public void setReport_datetime(Date report_datetime) { this.report_datetime = fmt.format(report_datetime); }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getProjects_involved() { return projects_involved; }
    public void setProjects_involved(String projects_involved) { this.projects_involved = projects_involved; }
    
}
