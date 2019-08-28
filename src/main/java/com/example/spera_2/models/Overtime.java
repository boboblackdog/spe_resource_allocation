/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.models;

import com.example.spera_2.utils_config.MongoCompassConnection;
import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class Overtime {
    
    private String nik;
    private String start_datetime;
    private String end_datetime;
    private String project;
    private String task_description;
    private String supervisor_nik;
    private String pmo_nik;
    
    private MongoCompassConnection mcc;
    
    /*
    inserts manually into overtime request
    */
    public void manualInsert() {
        mcc = new MongoCompassConnection();
        mcc.manualInsertCore(
                new Document()
                        .append("nik", this.nik)
                        .append("start_datetime", start_datetime)
                        .append("end_datetime", end_datetime)
                        .append("project", project)
                        .append("task_description", task_description)
                        .append("supervisor_nik", supervisor_nik)
                        .append("pmo_nik", pmo_nik)
                , "overtime");
    }
    
    /*
    verifies whether all three NIKs are valid
    */
    public boolean nikVerifiable() {
        try {
            Integer.parseInt(nik); Integer.parseInt(supervisor_nik); Integer.parseInt(pmo_nik);
            return (
                    (nik != supervisor_nik && nik != pmo_nik && pmo_nik != supervisor_nik) &&
                    nik.length() == 8 && pmo_nik.length() == 8 && supervisor_nik.length() == 8
                    );
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return    "nik: " + this.nik + ",%0D%0A" 
                + "start_datetime: " + this.start_datetime + ",%0D%0A"
                + "end_datetime: " + this.end_datetime + ",%0D%0A"
                + "project: " + this.project + ",%0D%0A"
                + "task_description: " + this.task_description + ",%0D%0A"
                + "supervisor_nik: " + this.supervisor_nik + ",%0D%0A"
                + "pmo_nik: " + this.pmo_nik + "%0D%0A"
                ; 
    }
    
    /*
    converts object to bson document
    */
    public Document toDocument() {
        return new Document()
                .append("nik", nik)
                .append("start_datetime", start_datetime)
                .append("end_datetime", end_datetime)
                .append("project", project)
                .append("task_description", task_description)
                .append("supervisor_nik", supervisor_nik)
                .append("pmo_nik", pmo_nik);
    }
    
    public Overtime() {}
    public Overtime(String nik, String start_datetime, String end_datetime, String project, String task_description, String supervisor_nik, String pmo_nik) {
        this.nik = nik;
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.project = project;
        this.task_description = task_description;
        this.supervisor_nik = supervisor_nik;
        this.pmo_nik = pmo_nik;
    }
    
    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }
    
    public String getStart_datetime() { return start_datetime; }
    public void setStart_datetime(String start_datetime) { this.start_datetime = start_datetime; }
    
    public String getEnd_datetime() { return end_datetime; }
    public void setEnd_datetime(String end_datetime) { this.end_datetime = end_datetime; }
    
    public String getProject() { return project; }
    public void setProject(String project) { this.project = project; }
    
    public String getTask_description() { return task_description; }
    public void setTask_description(String task_description) { this.task_description = task_description; }
    
    public String getSupervisor_nik() { return supervisor_nik; }
    public void setSupervisor_nik(String supervisor_nik) { this.supervisor_nik = supervisor_nik; }
    
    public String getPmo_nik() { return pmo_nik; }
    public void setPmo_nik(String pmo_nik) { this.pmo_nik = pmo_nik; }
    
}
