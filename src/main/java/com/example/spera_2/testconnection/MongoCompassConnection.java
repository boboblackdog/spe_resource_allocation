/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.testconnection;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author rakhadjo
 */
public class MongoCompassConnection {
    
    private static final MongoClient client = new MongoClient("localhost", 27017);
    private static final MongoCredential cred = MongoCredential.createCredential("root", "spera", "password".toCharArray());
    private static final MongoDatabase db = client.getDatabase("spera");
    private static final MongoCollection collection = db.getCollection("logfiles");
    
    public MongoCompassConnection() {}
    
    public Document insertIntoLogfiles(File fileName) {
        try {
            collection.insertOne(
                    new Document()
                        .append("logfile", fileName)
            );
            return new Document()
                        .append("status", "success")
                        .append("message", "-");
        } catch (Exception e) {
            return new Document()
                        .append("status", "failed")
                        .append("message", e.getMessage());
        }
    }
    
    public boolean testDBConnection() {
        
        FindIterable<Document> fi = collection.find();
        List<Object> list = new ArrayList<>();
        Iterator it = fi.iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        return (collection.countDocuments() != 0) && !list.isEmpty();
        
    }
}
