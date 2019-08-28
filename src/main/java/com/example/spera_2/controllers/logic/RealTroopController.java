/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controllers.logic;

import com.example.spera_2.Spera2Application;
import com.example.spera_2.models.Employee;
import com.example.spera_2.models.Overtime;
import com.example.spera_2.models.Report;
import com.example.spera_2.models.refTroops;
import com.example.spera_2.models.requests.RequestHeaderCustom;
import com.example.spera_2.models.requests.TroopRequest;
import com.example.spera_2.models.responses.ResponseBody;
import com.example.spera_2.more_models.refGrades;
import com.example.spera_2.more_models.refPositions;
import com.example.spera_2.utils_config.MongoCompassConnection;
import com.example.spera_2.utils_config.RC;
import com.example.spera_2.utils_config.SperabotHandler;
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
public class RealTroopController {
    
    Logger logger = LoggerFactory.getLogger(Spera2Application.class);
    
    MongoCompassConnection mcc = new MongoCompassConnection();
    
    private static final SperabotHandler sbh = new SperabotHandler();
    
    /*
    @RequestHeader String Authentication
    @RequestBody Overtime overtime
    */
    public ResponseEntity<ResponseBody> addOvertime(String Authentication, Overtime overtime, HttpServletRequest request) throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime() + 25200000);
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        boolean log_success;
        Exception ex = null;
        Document data = new Document()
                .append("client_ip", request.getRemoteAddr())
                .append("request_header", new RequestHeaderCustom(Authentication).toString())
                .append("request_body", overtime.toString())
                .append("request_datetime", t1)
                ;
        sbh.setMethod("addOvertime");
        try {
            if (overtime.nikVerifiable()) {
                rb = new ResponseBody(new RC("00"));
                overtime.manualInsert();
            } else {
                rb = new ResponseBody(new RC("10"));
                sbh.sendError("RC 10", data);
            }
        } catch (Exception e) {
            rb = new ResponseBody(new RC("11"));
            sbh.sendError("RC 11", data);
        }
        return ResponseEntity.accepted()
                .headers(header)
                .body(rb);
    }
    /*
    @RequestHeader String Authentication
    */
    public ResponseEntity<ResponseBody> addReport(String Authentication, Report rr, HttpServletRequest request) throws Exception {
        logger.info("requesting");
        Timestamp t1 = new Timestamp(new Date().getTime() + 25200000);
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        boolean log_success;
        Exception ex = null;
        Document data = new Document()
                .append("client_ip", request.getRemoteAddr())
                .append("request_header", new RequestHeaderCustom(Authentication).toString())
                .append("request_body", rr.toString())
                .append("request_datetime", t1)
                ;
        sbh.setMethod("addReport");
        try {
            logger.info("adding...");
            if (rr.nikVerifiable() && rr.noneNull()) {
                rb = new ResponseBody(new RC("00"));
                rr.manualInsert();
            } else {
                rb = new ResponseBody(new RC("10"));
                sbh.sendError("RC 10", data);
            }
        } catch (Exception e) {
            rb = new ResponseBody(new RC("12"));
            ex = e;
            sbh.sendError("RC 12", data);
        }
        return ResponseEntity.accepted()
                .headers(header)
                .body(rb);
    }

    /*
    @RequestHeader String Authentication
    */
    public ResponseEntity<ResponseBody> getPositions(String Authentication, List<refPositions> li, HttpServletRequest request) throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime() + 25200000);
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        boolean log_success;
        Exception ex = null;
        Document data = new Document()
                .append("client_ip", request.getRemoteAddr())
                .append("request_header", new RequestHeaderCustom(Authentication).toString())
                .append("request_body", "null")
                .append("request_datetime", t1)
                ;
        sbh.setMethod("getPositions");
        try {
            List<Document> list = new ArrayList<>();
            li.forEach((ref) -> {
                list.add(ref.toDocument());
            });
            rb = new ResponseBody(new RC("00"));
            rb.setData(list);
        } catch (Exception e) {
            ex = e;
            rb = new ResponseBody(new RC("10"));
            sbh.sendError("RC 10", data);
        }
        log_success = mcc.insertInto(
                new Document().append("x-trace-id", "TBD"),                 //responseH 
                rb.toDocument(),                                            //responseB, 
                new Document().append("Authentication", Authentication),    //requestH, 
                new Document(),                                             //requestB, 
                "log_getpositions",                                         //collectionname
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
    @RequestHeader String Authentication
    */
    public ResponseEntity<ResponseBody> getGrades(String Authentication, List<refGrades> li, HttpServletRequest request) throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime() + 25200000);
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        boolean log_success;
        Exception ex = null;
        Document data = new Document()
                .append("client_ip", request.getRemoteAddr())
                .append("request_header", new RequestHeaderCustom(Authentication).toString())
                .append("request_body", "null")
                .append("request_datetime", t1)
                ;
        sbh.setMethod("getGrades");
        try {
            List<Document> list = new ArrayList<>();
            li.forEach((ref) -> {
                list.add(ref.toDocument());
            });
            rb = new ResponseBody();
            rb.setData(list);
        } catch (Exception e) {
            ex = e;
            rb = new ResponseBody("10", "error");
            sbh.sendError("RC 10", data);
        }
        log_success = mcc.insertInto(
                new Document().append("x-trace-id", "TBD"),                 //responseH 
                rb.toDocument(),                                            //responseB, 
                new Document().append("Authentication", Authentication),    //requestH, 
                new Document(),                                             //requestB, 
                "log_getgrades",                                            //collectionname
                "null",                                                     //nik
                request.getRemoteAddr(),                                    //sender ip
                ex,                                                         //exception, if applicable
                t1,                                                         //request start timestamp
                new Timestamp(new Date().getTime() + 25200000)              //request end timestamp
        );
        rb.setLog_success(log_success);
        return ResponseEntity.accepted()
                    .headers(header)
                    .body(rb)
                    ;
    }
    
    /*
    @RequestHeader String Authentication
    @RequestBody refTroops ref
    */
    public ResponseEntity<ResponseBody> addTroops(String Authentication, refTroops ref, HttpServletRequest request) throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime() + 25200000);
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        boolean log_success;
        Exception ex = null;
        Document data = new Document()
                .append("client_ip", request.getRemoteAddr())
                .append("request_header", new RequestHeaderCustom(Authentication).toString())
                .append("request_body", ref.toString())
                .append("request_datetime", t1)
                ;
        sbh.setMethod("addTroops");
        if (!ref.containsNull()) {
            try {
                rb = new ResponseBody(new RC("00"));
            } catch (Exception e) {
                ex = e;
                rb = new ResponseBody(new RC("10"));
                sbh.sendError("RC 10", data);
            }
        } else {
            rb = new ResponseBody(new RC("11"));
            sbh.sendError("RC 11", data);
        }
        rb.setData(ref.toDocument());
        log_success = mcc.insertInto(
                new Document().append("x-trace-id", "TBD"),                 //responseH 
                rb.toDocument(),                                            //responseB, 
                new Document().append("Authentication", Authentication),    //requestH, 
                ref.toDocument(),                                           //requestB, 
                "log_addtroops",                                            //collectionname
                ref.getNik(),                                               //nik
                request.getRemoteAddr(),                                    //sender ip
                ex,                                                         //exception, if applicable
                t1,                                                         //request start timestamp
                new Timestamp(new Date().getTime() + 25200000)              //request end timestamp
        );
        rb.setLog_success(log_success);
        return ResponseEntity.accepted()
                       .headers(header)
                       .body(rb)
                       ;
    }
    
    /*
    @RequestHeader String Authentication
    @RequestBody TroopRequest troopRequest
    */
    public ResponseEntity<ResponseBody> getAllTroops(TroopRequest troopRequest, String Authentication, List<refTroops> li, HttpServletRequest request) throws Exception {
        Timestamp t1 = new Timestamp(new Date().getTime() + 25200000);
        HttpHeaders header = new HttpHeaders();
        header.add("x-trace-id", "TBD");
        ResponseBody rb;
        boolean log_success;
        Exception ex = null;
        sbh.setMethod("getAllTroops");
        Document data = new Document()
                .append("client_ip", request.getRemoteAddr())
                .append("request_header", new RequestHeaderCustom(Authentication).toString())
                .append("request_body", troopRequest.toString())
                .append("request_datetime", t1)
                ;
        try {
            List<Document> list = new ArrayList<>();
            if (troopRequest.getTroops().equals("get-all")) {
                li.forEach((ref) -> {
                    list.add(new Employee(ref).toDocument());
                });
                rb = new ResponseBody(new RC("00"));
                rb.setData(list);
            } else {
                rb = new ResponseBody(new RC("02"));
                sbh.sendError("RC 02", data);
                /*
                message: troops request != \"get all \"
                */
            }
        } catch (Exception e) {
            rb = new ResponseBody(new RC("10"));
            sbh.sendError("RC 10", data);
            /*
            message: get all troops failed
            */
        }
        log_success = mcc.insertInto(
                new Document().append("x-trace-id", "TBD"),                 //responseH 
                rb.toDocument(),                                            //responseB, 
                new Document().append("Authentication", Authentication),    //requestH, 
                new Document(),                                             //requestB, 
                "log_alltroops",                                            //collectionname
                "null",                                                     //nik
                request.getRemoteAddr(),                                    //sender ip
                ex,                                                         //exception, if applicable
                t1,                                                         //request start timestamp
                new Timestamp(new Date().getTime() + 25200000)              //request end timestamp
        );
        rb.setLog_success(log_success);
        return ResponseEntity.accepted()
                        .headers(header)
                        .body(rb)
                        ;
    }
    
}
