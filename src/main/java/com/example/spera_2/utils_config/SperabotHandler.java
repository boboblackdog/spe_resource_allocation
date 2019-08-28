/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.utils_config;

import com.example.spera_2.Spera2Application;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
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
public class SperabotHandler {
    
    private String LINK = "https://api.telegram.org/bot";
    public String BOT_TOKEN;// = "942047985:AAFoHJ74gT8C2hf1XrO7iU2yWUXw7r6QKsc";
    private String CONN_STRING;// = LINK + BOT_TOKEN;
    private String CURRENT_METHOD = "";
    private String SEND_TO;
    
    private final MongoCompassConnection mcc = new MongoCompassConnection();
    private static final Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    
    public void setMethod(String method) {
        this.CURRENT_METHOD = method;
    }
    
    public SperabotHandler() {
        try {
            File XML = new File("sperabot_config.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            org.w3c.dom.Document d = db.parse(XML);
            NodeList list = d.getElementsByTagName("telegramconfig");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) node;
                    this.BOT_TOKEN = el.getElementsByTagName("bot_token").item(0).getTextContent();
                    this.SEND_TO = el.getElementsByTagName("send_to").item(0).getTextContent();
                    this.CONN_STRING = this.LINK + this.BOT_TOKEN;
                    logger.warn(SEND_TO);
                    logger.warn(BOT_TOKEN);
                }
            }
        } catch (Exception e) {}
    }
    public SperabotHandler(String method) { this.CURRENT_METHOD = method; }
    
    public void sendPost(String error) throws Exception {
        
        String urlString = CONN_STRING + "/sendMessage?chat_id=%s&text=%s";
        String error2 = "";
        
        StringBuilder sb2 = new StringBuilder();
        for (int i = 1; i <= error.length(); i++) {
            if (" ".equals(error.substring(i-1, i))) {
                sb2.append("%20");
            } else {
                sb2.append(error.substring(i-1, i));
            }
        }
        
        error2 = sb2.toString();
        urlString = String.format(urlString, "619993558", error2);
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        
        StringBuilder sb = new StringBuilder();
        InputStream is = new BufferedInputStream(conn.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String inputLine = "";
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        
        String response = sb.toString();
        
    }
    
    public void sendError(String error, Document data) throws Exception {
        
        logger.warn(BOT_TOKEN);
        String urlString = CONN_STRING + "/sendMessage?chat_id=%s&text=%s";
        StringBuilder sb = new StringBuilder();
        String msgProcess = "You have an " + error + " at method: " + this.CURRENT_METHOD + "%0D%0A%0D%0A"
                + "Client IP: " + data.getString("client_ip") + "%0D%0A"
                + "Request Header: " + "%0D%0A{%0D%0A" + data.getString("request_header") + "}%0D%0A"
                + "Request Body: " + "%0D%0A{%0D%0A" + data.getString("request_body") + "}%0D%0A"
                + "Request Datetime: " + data.get("request_datetime").toString()
                ;
        
        for (int i = 1; i <= msgProcess.length(); i++) {
            if (" ".equals(msgProcess.substring(i-1, i))) {
                sb.append("%20");
            } else {
                sb.append(msgProcess.substring(i-1, i));
            }
        }
        String finalMsg = sb.toString();
        urlString = String.format(urlString, "619993558", finalMsg);
        //arvid : 282763932
        //rakha : 619993558
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        
        StringBuilder sb2 = new StringBuilder();
        InputStream is = new BufferedInputStream(conn.getInputStream());
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String inputLine = "";
        while ((inputLine = br.readLine()) != null) {
            sb2.append(inputLine);
        }
        
        String response = sb2.toString();
    }
}
