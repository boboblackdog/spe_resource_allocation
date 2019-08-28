/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.more_models;

import com.example.spera_2.utils_config.MongoCompassConnection;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 *
 * @author rakhadjo
 */
public class refGrades {
    @Id
    public ObjectId _id;
    public String grade_id;
    public String grade_romawi;
    public String grade_name;
    
    public refGrades() {}
    public refGrades(ObjectId _id, String grade_id, String grade_romawi, String grade_name) {
        this._id = _id;
        this.grade_id = grade_id;
        this.grade_romawi = grade_romawi;
        this.grade_name = grade_name;
    }
    
    public Document toDocument() {
        return new Document()
                .append("grade_id", grade_id)
                .append("grade_romawi", grade_romawi)
                .append("grade_name", grade_name);
    }
    
    public void manualGet() {
        MongoCompassConnection mcc = new MongoCompassConnection();
        MongoCollection<Document> gradesCollection = mcc.getColl("refGrades");
        gradesCollection.find();
    }
    
    public ObjectId get_id() { return _id; }
    public void set_id(ObjectId _id) { this._id = _id; } 
    
    public String getGradeId() { return grade_id; }
    public void setGradeId(String grade_id) { this.grade_id = grade_id; }
    
    public String getGradeRomawi() { return grade_romawi; }
    public void setGradeRomawi(String grade_romawi) { this.grade_romawi = grade_romawi; }
    
    public String getGradeName() { return grade_name; }
    public void setGradeName(String gradeName) { this.grade_name = gradeName; }
}
