/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controllers.logic;

import com.example.spera_2.Spera2Application;
import com.example.spera_2.models.Employee;
import com.example.spera_2.models.User;
import com.example.spera_2.models.UserBearer;
import com.example.spera_2.models.requests.RequestHeaderCustom;
import com.example.spera_2.models.requests.UserLoginRequest;
import com.example.spera_2.models.requests.UserProfileRequest;
import com.example.spera_2.models.responses.ResponseBody;
import com.example.spera_2.utils_config.MongoCompassConnection;
import com.example.spera_2.utils_config.RC;
import com.example.spera_2.utils_config.SperabotHandler;
import java.sql.Timestamp;
import java.util.Date;
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
public class RealUserController {
    
    private final Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    private final MongoCompassConnection mcc = new MongoCompassConnection();
    private final SperabotHandler sbh = new SperabotHandler();
    
    /*
    method used for logging in given user. 
    @RequestHeader String Authentication
    @RequestBody UserLoginRequest ulr
    */
    public ResponseEntity<ResponseBody> loginUser(UserLoginRequest ulr, String Authentication, Employee emp, HttpServletRequest request) throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime() + 25200000);
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        Document respH = new Document().append("x-trace-id", "TBD");
        boolean log_success;
        Exception ex = null;
        sbh.setMethod("loginUser");
        Document data = new Document()
                .append("client_ip", request.getRemoteAddr())
                .append("request_header", new RequestHeaderCustom(Authentication).toString())
                .append("request_body", ulr.toString())
                .append("request_datetime", t1)
                ;
        User user = new User(ulr);
        UserBearer user_bearer = new UserBearer(ulr, Authentication);
        
        try {
            if (ulr.usernameVerifiable()) {
                boolean exists = user.existsInUserTable();
                boolean active = user_bearer.sessionActive();
                if (exists && active) {
                    
                    logger.info("EXISTS AND ACTIVE");
                    user_bearer.extendLoginSession();
                    rb = new ResponseBody(new RC("00"));
                    rb.setData(emp);
                    header.add("token", Authentication);
                    
                } else if (exists && (active == false)) {
                    
                    logger.info("EXISTS BUT NOT ACTIVE");
                    String newToken = user_bearer.insertIntoUserBearer();
                    header.add("new-auth-token", newToken);
                    respH.append("new-auth-token", newToken);
                    rb = new ResponseBody(new RC("00"));
                    rb.setData(emp);
                    
                } else {
                    
                    logger.error("user doesn't exist");
                    rb = new ResponseBody(new RC("10"));
                    sbh.sendError("RC 10", data);
                    
                }
            } else {
                
                logger.error("INVALID REQUEST FORMAT");
                rb = new ResponseBody(new RC("11"));
                sbh.sendError("RC 11", data);
                
            }
        } catch (Exception e) {
            
            logger.error(e.toString());
            logger.error(e.getMessage());
            rb = new ResponseBody(new RC("12"));
            ex = e;
            sbh.sendError("RC 12", data);
            
        }
        log_success = mcc.insertInto(
                respH,                                                      //responseH 
                rb.toDocument(),                                            //responseB, 
                new Document().append("Authentication", Authentication),    //requestH, 
                ulr.toDocument(),                                           //requestB, 
                "log_userlogin",                                            //collectionname
                ulr.getUsername(),                                          //nik
                request.getRemoteAddr(),                                    //sender ip
                ex,                                                         //exception, if applicable
                t1,                                                         //request start timestamp
                new Timestamp(new Date().getTime() + 25200000)              //request end timestamp
        );
        rb.setLog_success(log_success);
        return ResponseEntity.accepted()
                    .headers(header)
                    .body(rb);
    }
    
    
    /*
    method used for obtaining employee data with given NIK
    @RequestHeader String Authentication
    @RequestBody UserProfileRequest upr
    */
    public ResponseEntity<ResponseBody> getUserProfile(UserProfileRequest upr, String Authentication, HttpServletRequest request, Employee emp) throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime() + 25200000);
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        boolean log_success;
        Exception ex = null;
        sbh.setMethod("getUserProfile");
        Document data = new Document()
                .append("client_ip", request.getRemoteAddr())
                .append("request_header", new RequestHeaderCustom(Authentication).toString())
                .append("request_body", upr.toString())
                .append("request_datetime", t1)
                ;
        try {
            if (upr.nikVerifiable()) {
                logger.info("NIK Request Body Verified");
                rb = new ResponseBody(new RC("00"));
                rb.setData(emp);
            } else {
                logger.error("NIK Request Body Wrong Format");
                logger.info("refer to documentation");
                rb = new ResponseBody(new RC("11"));
                sbh.sendError("RC 11", data);
            }
        } catch (Exception e) {
            ex = e;
            rb = new ResponseBody(new RC("10"));
            sbh.sendError("RC 10", data);
        }
        log_success = mcc.insertInto(
                        new Document().append("x-trace-id", "TBD"),                 //responseH 
                        rb.toDocument(),                                            //responseB, 
                        new Document().append("Authentication", Authentication),    //requestH, 
                        upr.toDocument(),                                           //requestB, 
                        "log_userprofile",                                          //collectionname
                        upr.getNik(),                                               //nik
                        request.getRemoteAddr(),                                    //sender ip
                        ex,                                                         //exception, if applicable
                        t1,                                                         //request start timestamp
                        new Timestamp(new Date().getTime() + 25200000)              //request end timestamp
        );
        rb.setLog_success(log_success);
        return ResponseEntity.accepted()
                    .headers(header)
                    .body(rb);
    }
    
}
