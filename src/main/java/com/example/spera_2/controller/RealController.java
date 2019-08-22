
package com.example.spera_2.controller;

import com.example.spera_2.Spera2Application;
import com.example.spera_2.testconnection.MySQLConnection;
import com.example.spera_2.models.requests.DashboardRequest;
import com.example.spera_2.models.DashboardResponse;
import com.example.spera_2.models.Employee;
import com.example.spera_2.models.requests.NikRequest;
import com.example.spera_2.models.UserLogin;
import com.example.spera_2.models.refTroops;
import com.example.spera_2.more_models.refGrades;
import com.example.spera_2.more_models.refPositions;
import com.example.spera_2.testconnection.MongoCompassConnection;
import com.example.spera_2.testconnection.TestConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author rakhadjo
 */

public class RealController {
    
    private static final Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    private MongoCompassConnection mcc = new MongoCompassConnection();
    
    public Document testConnection() {
        try {
            logger.info("initiating connection sequence")
                    ;
            TestConnection tc = new TestConnection()
                    ;
            logger.info("connection test successful")
                    ;
            return new Document("test results", tc.test())
                    ;
        } catch (Exception e) {
            logger.error("error: " + e.getMessage() + ". Try again")
                    ;
            return new Document()
                    .append("result", "unable to can")
                    .append("error", e.getMessage())
                    ;
        }  
    }
    
    /*
    returns all troop data
    */
    public ResponseEntity<Document> getAllTroops(Document tr, String Authentication, List<refTroops> li, HttpServletRequest request) {
        Timestamp t1 = new Timestamp(
                new Date()
                        .getTime()
        );
        logger.info("Request to Get All Troops")
                ;
        HttpHeaders header = new HttpHeaders()
                ;
        header.add("x-trace-id", "TBD")
                ;
        Document requestHeader = new Document()
                .append("Authentication", Authentication)
                ;
        Document requestBody = tr
                ;
        try {
            logger.info("trying to get all troops...")
                    ;
            List<Document> list = new ArrayList<>()
                    ;
            if (tr.getString("troops").equals("get-all")) {
                li.forEach((ref) -> {
                    list.add(new Employee(ref).toDocument());
                });
            Document responseHeader = new Document()
                    .append("x-trace-id", "TBD")
                    ;
            Document responseBody = new Document()
                    .append("rc", "00")
                    .append("message", "success")
                    .append("role", "admin")
                    .append("menu", "home, troops, account")
                    .append("data", list)
                    .append("auth_token", Authentication)
                    .append("built_token", buildToken())
                    ;
            //insert method here
            boolean log_success = mcc.insertInto(
                    responseHeader, responseBody, requestHeader, requestBody,  
                    "log_alltroops", tr.getString("nik_requestor"), request.getRemoteAddr(),
                    t1, new Timestamp(new Date().getTime())
            );
            logger.info("Successfully obtained all troops data")
                    ;
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_success", log_success))
                    ;
            } else {
                logger.error("Unsuccessful. Troops request != \"get-all\"")
                        ;
                Document responseHeader = new Document()
                        .append("x-trace-id", "TBD")
                        ;
                Document responseBody = new Document()
                        .append("rc", "12")
                        .append("message", "command undefined")
                        ;
                //insert method here
                boolean logstatus = mcc.insertInto(
                    responseHeader, responseBody, requestHeader, requestBody,  
                    "log_alltroops", tr.getString("nik_requestor"), request.getRemoteAddr(),
                    t1, new Timestamp(new Date().getTime())
                );
                return ResponseEntity.accepted()
                        .headers(header)
                        .body(responseBody.append("log_status", logstatus))
                        ;
        }
        } catch (Exception e) {
            logger.error("failed to obtain troop data: " + e.getMessage())
                    ;
            Document responseHeader = new Document()
                    .append("x-trace-id", "TBD")
                    ;
            Document responseBody = new Document()
                    .append("rc", "11")
                    .append("message", "invalid request format")
                    ;
            //insert method here
            boolean log_success = mcc.insertInto(
                    responseHeader, responseBody, requestHeader, requestBody,  
                    "log_alltroops", tr.getString("nik_requestor"), request.getRemoteAddr(),
                    t1, new Timestamp(new Date().getTime())
            );
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_success", log_success))
                    ;
        }
        
    }
    
    /*
    returns user profile of a given user
    */
    public ResponseEntity<Document> getUserProfile(NikRequest nr, String Authentication, Employee emp, HttpServletRequest request) throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime())
                ;
        logger.info("Request to Get Current User Profile made")
                ;
        HttpHeaders header = new HttpHeaders()
                ;
        header.add("x-trace-id", "TBD")
                ;
        Document requestBody = nr.toDocument()
                ;
        Document requestHeader = new Document()
                .append("Authentication", Authentication)
                ;
        try {
            logger.info("obtaining current user information")
                    ;
            Integer.parseInt(nr.getNik())
                    ;
            if (emp == null) {
                logger.error("no such employee found")
                        ;
                Document responseHeader = new Document()
                        .append("x-trace-id", "TBD")
                        ;
                Document responseBody = new Document()
                        .append("rc", "10")
                        .append("message", "entry DNE")
                        ;
                //insert method here
                boolean log_success = mcc.insertInto(
                        responseHeader, responseBody, requestHeader, requestBody,
                        "log_userprofile", nr.getNik(), request.getRemoteAddr(),
                        t1, new Timestamp(new Date().getTime())
                );
                return ResponseEntity.accepted()
                        .headers(header)
                        .body(responseBody.append("log_success", log_success))
                        ;
            } else {
                logger.info("successfully retrieved employee data")
                        ;
                Document responseHeader = new Document().append("x-trace-id", "TBD")
                        ;
                Document responseBody = new Document().append("rc", "00").append("message", "success")
                        .append("data", emp.toDocument())
                        .append("auth_token", Authentication)
                        .append("built_token", buildToken())
                        ;
                //insert method here
                boolean log_success = mcc.insertInto(
                        responseHeader, responseBody, requestHeader, requestBody,
                        "log_userprofile", nr.getNik(), request.getRemoteAddr(),
                        t1, new Timestamp(new Date().getTime())
                );
                return ResponseEntity.accepted()
                        .headers(header)
                        .body(responseBody.append("log_success", log_success))
                        ;
            }
        } catch (NumberFormatException e) {
            logger.error("Number Format Exception. Try again")
                    ;
            Document responseHeader = new Document()
                    .append("x-trace-id", "TBD")
                    ;
            Document responseBody = new Document()
                    .append("rc","11")
                    .append("message", "invalid request format");
            //insert method here
            boolean log_success = mcc.insertInto(
                    responseHeader, responseBody, requestHeader, requestBody,
                    "log_userprofile", nr.getNik(), request.getRemoteAddr(),
                    t1, new Timestamp(new Date().getTime())
            );
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_success", log_success));
        }
    }
    
    /*
    returns dashboard data
    */
    public ResponseEntity<Document> getDashboard(DashboardRequest dr, HttpServletRequest request) {
        Timestamp t1 = new Timestamp(new Date().getTime())
                ;
        HttpHeaders header = new HttpHeaders()
                ;
        header.add("x-trace-id", "TBD")
                ;
        Document requestHeader = new Document()
                .append("Authentication", null)
                ;
        Document requestBody = new Document()
                .append("nik_requester", dr.getNikRequestor())
                .append("year", dr.getYear())
                .append("device_id",dr.getDeviceId())
                ;
        try {
            logger.info("attempting to retrieve dashboard data...")
                    ;
            Document responseHeader = new Document()
                    .append("x-trace-id", "TBD")
                    ;
            Document responseBody = (new Document()
                    .append("rc", "00")
                    .append("message", "request dashboard data success")
                    .append("role", "admin")
                    .append("menu", "home, troops, account")
                    .append("data", 
                            new DashboardResponse(dr.getYear()).toDocument())
                    );
            boolean log_success = mcc.insertInto(
                    responseHeader, responseBody, requestHeader, requestBody, 
                    "log_dashboard", dr.getNikRequestor(), request.getRemoteAddr(),
                    t1, new Timestamp(new Date().getTime())
            );
            logger.info("retrieve dashboard data success")
                    ;
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_success", log_success))
                    ;
        } catch (Exception e) {
            logger.error(e.getMessage() + ". Try again")
                    ;
            Document responseHeader = new Document()
                    .append("x-trace-id", "TBD")
                    ;
            Document responseBody = new Document()
                    .append("rc", "11")
                    .append("message", e.getMessage())
                    ;
            boolean log_success = mcc.insertInto(
                    responseHeader, responseBody, requestHeader, requestBody, 
                    "log_dashboard", dr.getNikRequestor(), request.getRemoteAddr(),
                    t1, new Timestamp(new Date().getTime())
            );
            //insert method here
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_success", log_success))
                    ;
        }
        
    }
    
    /*
    basic welcome page
    */
    public ResponseEntity<Document> welcome() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("/troops/list");
        list.add("/user/login");
        list.add("/user/profile");
        list.add("/dashboard");
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        Document responseHeader = new Document().append("x-trace-id", "TBD");
        logger.info("setting welcome page");
        try {
            logger.info("welcome page loading success");
            Document body = new Document().append("rc", "00").append("message", "welcome to SPERA!")
                    .append("links", list)
                    ;
            return ResponseEntity.accepted().headers(header).body(body);
        } catch (Exception e) {
            logger.error(e.getMessage() + ". Try again");
            return ResponseEntity.accepted().headers(header).body(
                    new Document()
                            .append("rc", "00")
                            .append("message", e.getMessage())
            );
        }
        
    }
    
    /*
    user login support
    @RequestHeader String Authentication
    @Valid @RequestBody UserLogin ul
    */
    public ResponseEntity<Document> loginUser(UserLogin ul, String Authentication, refTroops ref, HttpServletRequest request) throws Exception {
//        Timestamp t1 = new Timestamp(
//                new Date()
//                        .getTime()
//        );
        HttpHeaders header = new HttpHeaders()
                ;
        header.add("x-trace-id", "TBD")
                ;
        Document responseHeader = new Document()
                .append("x-trace-id", "TBD")
                ;
        Document requestHeader  = new Document()
                .append("Authentication", Authentication)
                ;
        Document requestBody = ul.toDocument()
                ;
        logger.info("initializing user login process")
                ;
        logger.info("connecting to mysql...")
                ;
        try {
            logger.info("...successfully established mysql connection")
                    ;
            MySQLConnection conn = new MySQLConnection()
                    ;
            logger.info("checking user tables...")
                    ;
            if (conn.existsInUserTable(ul)) {
                logger.info("...user with given credentials exists")
                        ;
                logger.info("checking activity tables...")
                        ;
                if (conn.sessionActive(ul, Authentication)) {
                    logger.info("...user's login is active")
                            ;
                    conn.extendLoginSession(ul, Authentication)
                            ;
                    conn.closeCurrentConnection()
                            ;
                    logger.info("successfully logged in and extended expiration login session")
                            ;
                    Document responseBody = new Document()
                            .append("rc", "00")
                            .append("message", "login successful, session extended")
                            .append("role", "admin")
                            .append("menu", "home, troops, account")
                            .append("bearer_token", Authentication)
                            .append("data", new Employee(ref).toDocument())
                            ;
//                    boolean log_success = mcc.insertInto(
//                            responseHeader, responseBody, requestHeader, requestBody, 
//                            "log_userlogin", ul.getUsername(), request.getRemoteAddr(),
//                            t1, new Timestamp(new Date().getTime()))
//                            ;
                    return ResponseEntity.accepted()
                            .headers(header)
                            .body(responseBody.append("log_success", ""))
                            ;
                } else {
                    logger.warn("latest session expired. creating new login bearer token")
                            ;
                    String newToken = buildToken()
                            ;
                    logger.info("creating new session...")
                            ;
                    conn.insertIntoUserBearer(ul, newToken)
                            ;
                    conn.closeCurrentConnection()
                            ;
                    logger.info("create new login session success...")
                            ;
                    Document responseBody = new Document()
                            .append("rc", "00")
                            .append("message", "login successful, new session started")
                            .append("role", "admin")
                            .append("menu", "home, troops, account")
                            .append("bearer_token", newToken)
                            .append("data", new Employee(ref))
                            ;
//                    boolean log_success = mcc.insertInto(
//                            responseHeader, responseBody, requestHeader, requestBody, 
//                            "log_userlogin", ul.getUsername(), request.getRemoteAddr(),
//                            t1, new Timestamp(new Date().getTime()))
//                            ;
                    return ResponseEntity.accepted()
                            .headers(header)
                            .body(responseBody.append("log_success", ""))
                            ;
                }
            } else {
                logger.error("user with given credentials does not exist. Try again")
                        ;
                conn.closeCurrentConnection()
                        ;
                Document responseBody = new Document()
                        .append("rc", "10")
                        .append("message", "DNE in `users`")
                        ;
//                boolean log_success = mcc.insertInto(
//                            responseHeader, responseBody, requestHeader, requestBody, 
//                            "log_userlogin", ul.getUsername(), request.getRemoteAddr(),
//                            t1, new Timestamp(new Date().getTime())
//                );
                return ResponseEntity.accepted()
                        .headers(header)
                        .body(responseBody.append("log_success", ""))
                        ;
            }
        } catch (Exception e) {
            logger.error(e.getMessage() + ". Try again")
                    ;
            Document responseBody = new Document()
                    .append("rc", "11")
                    .append("message", "invalid request format")
                    .append("errorMsg", e.getMessage())
                    .append("data login", ul)
                    .append("stacktrace", e.getStackTrace())
                    ;
//            boolean log_success = mcc.insertInto(
//                            responseHeader, responseBody, requestHeader, requestBody, 
//                            "log_userlogin", ul.getUsername(), request.getRemoteAddr(),
//                            t1, new Timestamp(new Date().getTime())
//            );
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_success", ""))
                    ;
        }
    }
    
    public ResponseEntity<Document> getPositions(String Authentication, List<refPositions> li, HttpServletRequest request) {
        Timestamp t1 = new Timestamp(
                new Date()
                        .getTime()
        );
        Document requestHeader = new Document()
                .append("Authentication", Authentication);
        Document responseHeader = new Document()
                    .append("x-trace-id", "TBD")
                    ;
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        try {
            List<Document> list = new ArrayList<>();
            li.forEach((ref) -> {
                list.add(ref.toDocument());
            });
            Document responseBody = new Document()
                    .append("data", list)
                    ;
            //insert method here
            boolean log_status = mcc.insertInto(
                        responseHeader, responseBody, requestHeader, null,
                        "log_getpositions", null, request.getRemoteAddr(),
                        t1, new Timestamp(new Date().getTime())
                );
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_status", log_status))
                    ;
        } catch (Exception e) {
            Document responseBody = new Document()
                    .append("result", e.toString())
                    .append("errorMsg", e.getMessage());
            boolean log_status = mcc.insertInto(
                        responseHeader, responseBody, requestHeader, null,
                        "log_getpositions", null, request.getRemoteAddr(),
                        t1, new Timestamp(new Date().getTime())
                );
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_status", log_status))
                    ;
        }
        
    }
    
    public ResponseEntity<Document> getGrades(String Authentication, List<refGrades> li, HttpServletRequest request) {
        Timestamp t1 = new Timestamp(new Date().getTime())
                ;
        HttpHeaders header = new HttpHeaders()
                ;
        header.add("x-trace-id", "TBD")
                ;
        Document responseHeader = new Document()
                .append("x-trace-id", "TBD")
                ;
        Document requestHeader = new Document()
                .append("Authentication", Authentication)
                ;
        
        try {
            List<Document> list = new ArrayList<>();
            li.forEach((ref) -> {
                list.add(ref.toDocument());
            });
            Document responseBody = new Document()
                    .append("data", list);
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody)
                    ;
        } catch (Exception e) {
            Document responseBody = new Document()
                    .append("result", e.toString())
                    .append("errorMsg", e.getMessage())
                    ;
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody)
                    ;
        }
    }
    
    /*
    add new troop to refTroops collection
    */
    public ResponseEntity<Document> insertTroops(String Authentication, Document ref, HttpServletRequest request, boolean nullExists) {
        Timestamp t1 = new Timestamp(
                new Date()
                        .getTime()
        );
        logger.info("method to create new trooper object")
                ;
        HttpHeaders header = new HttpHeaders()
                ;
        header.add("x-trace-id", "TBD")
                ;
        Document responseHeader = new Document()
                .append("x-trace-id", "TBD")
                ;
        Document requestHeader = new Document()
                .append("Authentication", Authentication)
                ;
        Document requestBody = ref
                ;
        logger.info("checking if all spaces are filled...")
                ;
        if (!new refTroops(ref).containsNull()) {
            logger.info("...ok, no spaces left blank")
                    ;
            try {
                logger.info("executing new insertion...")
                        ;
                //insert to refTroops here
                Document responseBody = new Document()
                        .append("rc", "00")
                        .append("message", "add troop successful")
                        .append("role", "admin")
                        .append("menu", "home, troops, account")
                        ;
                //insert to logs here
                logger.info("attempting to log results...")
                        ;
                boolean log_status = mcc.insertInto(
                        responseHeader, responseBody, requestHeader, requestBody, 
                        "log_addtroops", ref.getString("nik"), request.getRemoteAddr(), 
                        t1, new Timestamp(new Date().getTime())
                );
                logger.info("...successfully stored log data")
                        ;
                logger.info("insertion success")
                        ;
                return ResponseEntity.accepted()
                        .headers(header)
                        .body(responseBody.append("log_status", log_status))
                        ;
            } catch (Exception e) {
                logger.error("add troop unsuccessful")
                        ;
                logger.error("error: " + e.getMessage())
                        ;
                Document responseBody = new Document()
                        .append("rc", "10")
                        .append("message", "add troop unsuccessful")
                        .append("error_msg", e.toString())
                        .append("role", "admin")
                        .append("menu", "home, troops, account")
                        ;
                //insert to logs here
                logger.info("attempting to log results...")
                        ;
                boolean log_status = mcc.insertInto(
                        responseHeader, responseBody, requestHeader, requestBody, 
                        "log_addtroops", ref.getString("nik"), request.getRemoteAddr(), 
                        t1, new Timestamp(new Date().getTime())
                );
                logger.info("...successfully stored log data")
                        ;
                return ResponseEntity.accepted()
                        .headers(header)
                        .body(responseBody.append("log_status", log_status))
                        ;
            }
        } else {
            logger.error("some blanks are left empty, try again")
                    ;
            Document responseBody = new Document()
                    .append("rc", "11")
                    .append("message", "please fill every blank")
                    .append("role", "admin")
                    .append("menu", "home, troops, account")
                    ;
            boolean log_status = mcc.insertInto(
                    responseHeader, responseBody, requestHeader, requestBody, 
                    "log_addtroops", ref.getString("nik"), request.getRemoteAddr(), 
                    t1, new Timestamp(new Date().getTime())
            );
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(responseBody.append("log_status", log_status))
                    ;
        }
    }
    
    /*
    basic constructor
    */
    public RealController() {}
    
    /*
    for building random 32 digit alphanumeric token
    */
    private static final String set = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890abcdefghijklmnopqrstuv";
    private static String buildToken() {
        StringBuilder sb = new StringBuilder()
                ;
        for (int i = 0; i < 32; i++) {
            int charPosition = (int)(Math.random()*set.length())
                    ;
            sb.append(set.charAt(charPosition))
                    ;
        } return sb.toString()
                ;
    }
}