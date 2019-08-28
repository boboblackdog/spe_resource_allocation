/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.utils_config;

import com.example.spera_2.Spera2Application;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author rakhadjo
 */
public class MongoCompassConnection {
    
    private static Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    
    private static MongoClient client;// = new MongoClient("localhost", 27017);
    private static MongoCredential cred;// = MongoCredential.createCredential("root", "spera", "password".toCharArray());
    private static MongoDatabase db;// = client.getDatabase("spera");
    private static MongoCollection<Document> collection;// = db.getCollection("refTroops");// = db.getCollection("logfiles");
 
    public boolean configFileExists() {
        return new File("mongo_config.xml").exists();
    }
    
    public MongoCompassConnection() {
        try {
            logger.info("reading configuration settings...");
            File XML = new File("mongo_config.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document d = db.parse(XML);
            NodeList list = d.getElementsByTagName("dbconfig");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    this.client = newClient(
                            el.getElementsByTagName("db_host").item(0).getTextContent(), 
                            Integer.parseInt(el.getElementsByTagName("db_port").item(0).getTextContent()));
                    this.cred = newCred(
                            el.getElementsByTagName("db_username").item(0).getTextContent(), 
                            el.getElementsByTagName("db_name").item(0).getTextContent(), 
                            el.getElementsByTagName("db_password").item(0).getTextContent().toCharArray());
                    this.db = this.client.getDatabase(el.getElementsByTagName("db_name").item(0).getTextContent());
                    this.collection = this.db.getCollection("refTroops");
                    logger.info("...read complete, MongoDB instance set");
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
            logger.error("errorMsg: " + e.getMessage());
        }
    }
    
    public void manualInsertCore(Document docx, String collectionName) {
        this.db.getCollection(collectionName).insertOne(docx);
    }
    
    public Document manualNikSearch(String nik) {
        return this.collection.find(eq("nik", nik)).first();
    }
    
    private static MongoClient newClient(String host, int port) throws Exception {
        return new MongoClient(host, port);
    }
    
    private static MongoCredential newCred(String username, String db, char[] arr) {
        return MongoCredential.createCredential(username, db, arr);
    }
    
    public MongoCollection<Document> getColl(String collectionName) {
        return db.getCollection(collectionName);
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
    
    public boolean insertInto(
            Document responseH, Document responseB, 
            Document requestH, Document requestB,
            String collectionName, String nik, String ip,
            Exception ex,
            Timestamp t1, Timestamp t2
    ) {
        try {
            logger.info("log insertion start...");
            MongoCollection<Document> insertCollection = this.db.getCollection(collectionName);
            int isError;
            String exc;
            if (responseB.getString("rc").equals("00")) {
                isError = 0;
                logger.info("not an error");
            } else {
                isError = 1;
                logger.warn("error status");
            }
            if (ex != null) { exc = ex.toString(); } 
            else            { exc = "none"; }
            logger.info("preparing mongo document...");
            Document insertIntoCollection = new Document()
                    .append("client_ip", ip)
                    .append("request_body", requestB)
                    .append("request_header", requestH)
                    .append("response_body", responseB)
                    .append("response_header", responseH)
                    .append("nik", nik)
                    .append("request_datetime", t1)
                    .append("response_datetime", t2)
                    .append("elapsed_time", t2.getTime()-t1.getTime())
                    .append("is_error", isError)
                    .append("exception", exc)
                    ;
            logger.info("...mongo document prepared successfully");
            logger.info("inserting log document...");
            insertCollection.insertOne(insertIntoCollection);
            logger.info("...log insertion success");
            return true;
        } catch (Exception e) {
            logger.error("failed to store log file: " + e.getMessage());
            return false
                    ;
        }
        /*
        try insert here, after method fully works!
        */
        
    }
}
