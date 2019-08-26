
package com.example.spera_2.controllers.logic;

import com.example.spera_2.Spera2Application;
import com.example.spera_2.models.requests.DashboardRequest;
import com.example.spera_2.models.DashboardResponse;
import com.example.spera_2.models.responses.ResponseBody;
import com.example.spera_2.utils_config.MongoCompassConnection;
import com.example.spera_2.utils_config.RC;
import com.example.spera_2.utils_config.TestConnection;
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
    MongoCompassConnection mcc = new MongoCompassConnection();
    
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
    returns dashboard data
    @RequestHeader String Authentication
    @RequestBody DashboardRequest dashboardRequest
    */
    public ResponseEntity<ResponseBody> getDashboard2(DashboardRequest dashboardRequest, String Authentication, HttpServletRequest request) {
        Timestamp t1 = new Timestamp(new Date().getTime());
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        Exception ex = null;
        boolean log_success;
        try {
            /*
            message: request dashboard data success
            */
            rb = new ResponseBody(new RC("00"));
            rb.setData(new DashboardResponse(dashboardRequest.getYear()));
        } catch (Exception e) {
            /*
            message: request dashboard data failed
            */
            ex = e;
            rb = new ResponseBody(new RC("10"));
        }
        log_success = mcc.insertInto(
                new Document().append("x-trace-id", "TBD"),                 //responseH 
                rb.toDocument(),                                            //responseB, 
                new Document().append("Authentication", Authentication),    //requestH, 
                dashboardRequest.toDocument(),                              //requestB, 
                "log_dashboard",                                            //collectionname
                "null",                                                     //nik
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
            return ResponseEntity.accepted()
                    .headers(header)
                    .body(body);
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
    basic constructor
    */
    public RealController() {}
}