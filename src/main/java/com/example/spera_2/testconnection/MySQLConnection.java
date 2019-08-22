/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.testconnection;

import com.example.spera_2.Spera2Application;
import com.example.spera_2.models.UserLogin;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author rakhadjo
 */
public class MySQLConnection {
    
    private static Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    
    private static String USERNAME;
    private static String PASSWORD;
    private static String HOST_PORT;
    //change this to the actual server later on
    private static String SERVER; // = "jdbc:mysql://" + HOST_PORT;
    private static final String SETTINGS = "/spera_portal?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
    private static String CONN_STRING; // = SERVER + SETTINGS;
    
    private Connection conn;
    
    public boolean configFileExists() {
        return new File("mysql_config.xml").exists();
    }
    
    public MySQLConnection() throws SQLException {
        
        try {
            logger.info("reading configuration settings...");
            File XML = new File("mysql_config.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(XML);
            NodeList list = d.getElementsByTagName("dbconfig");
            for (int i = 0; i < list.getLength(); i++) {
            Node n = list.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element el = (Element) n;
                    this.USERNAME = el.getElementsByTagName("db_username").item(0).getTextContent();
                    this.PASSWORD = el.getElementsByTagName("db_password").item(0).getTextContent();
                    this.HOST_PORT = el.getElementsByTagName("db_host").item(0).getTextContent() + ":" + el.getElementsByTagName("db_port").item(0).getTextContent();
                    this.SERVER = "jdbc:mysql://" + this.HOST_PORT;
                    this.CONN_STRING = this.SERVER + this.SETTINGS;
                }
            }
            this.conn = DriverManager.getConnection(this.CONN_STRING, this.USERNAME, this.PASSWORD);
            logger.info("...read complete, MySQL instance set");
        } 
        catch (Exception e) {
            logger.error(e.toString());
            logger.error("errorMsg: " + e.getMessage());
        }
        
    }
    
    /*
    provoked to check whether user with given credentials exist in the user table
    PASSWORD PASSED TO BE HASHED WITH LATER LIBRARY
    */
    public boolean existsInUserTable(UserLogin ul) throws SQLException {
        String sql = "SELECT * FROM `user`;";
        Statement stmt = conn.createStatement();
        ResultSet rslt = stmt.executeQuery(sql);
        while (rslt.next()) {
            if (rslt.getString("username").equals(ul.getUsername()) && rslt.getString("password_hash").equals(ul.getPassword())) {
                return true;
            }
        } return false;
    }
    
    /*
    provoked to check whether user has an active session in the table
    */
    public boolean sessionActive(UserLogin ul, String auth) throws SQLException {
        String sql = "SELECT TIMESTAMPDIFF(MINUTE, datetime_created, NOW()) AS difference FROM `user_bearer` WHERE "
                + "nik = ? AND bearer_token = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, ul.getUsername());
        pstm.setString(2, auth);
        ResultSet rslt = pstm.executeQuery();
        while (rslt.next()) {
            return rslt.getInt("difference") < 30;
        }
        return false;
    }
    
    /*
    provoked when user exists, but there are no open sessions for that user
    */
    public void insertIntoUserBearer(UserLogin ul, String newToken) throws SQLException {
        
        String sql = "INSERT INTO "
                + "`user_bearer` "
                + "(nik, bearer_token, device_id, datetime_created, datetime_expired)"
                + "VALUES "
                + "(?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE));";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, ul.getUsername());
        pstm.setString(2, newToken);
        pstm.setString(3, ul.getDeviceId());
        pstm.executeUpdate();
    }
    
    /*
    provoked to extend expiration time when a user logs in within the 30 minute timeframe
    */
    public void extendLoginSession(UserLogin ul, String auth) throws SQLException {
        String sql = "UPDATE `user_bearer` "
                + "SET "
                + "datetime_expired = DATE_ADD(NOW(), INTERVAL 30 MINUTE) "
                + "WHERE "
                + "nik = ? AND bearer_token = ?;";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, ul.getUsername());
        pstm.setString(2, auth);
        pstm.executeUpdate();
    }
    
    /*
    closes the current connection
    */
    public void closeCurrentConnection() throws SQLException {
        
        this.conn.close();
        
    }
    
    public boolean testDBConnection() throws SQLException {
        String sql = "SELECT * FROM `user`;";
        Statement stmt = conn.createStatement();
        ResultSet rslt = stmt.executeQuery(sql);
        while (rslt.next()) {
            return !rslt.equals("null");
        } return false;
    }
    
}
