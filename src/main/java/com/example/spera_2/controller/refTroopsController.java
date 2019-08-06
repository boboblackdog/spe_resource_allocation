/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.controller;

import com.example.spera_2.models.Employee;
import com.example.spera_2.models.NikRequest;
import com.example.spera_2.models.refTroops;
import com.example.spera_2.repositories.refTroopsRepository;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author rakhadjo
 */
@RestController
@RequestMapping("/user/user-info/")
public class refTroopsController {

    @Autowired
    private refTroopsRepository repo;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<Employee> getAllTroops() {
        List<Employee> list = new ArrayList<>();
        for (refTroops ref : repo.findAll()) {
            list.add(new Employee(ref, true));
        }
        return list;
    }

    @RequestMapping(value = "/{nik}", method = RequestMethod.GET)
    public Document getByNik(@PathVariable("nik") String nik) throws Exception {

        try {
            Integer.parseInt(nik);
            if (repo.findByNik(nik) == null) {
                return (new Document()).append("rc", "10").append("message", "entry DNE");
            } else {
                return (new Document()).append("rc", "00").append("message", "success")
                        .append("data", (new Employee(repo.findByNik(nik), false)));
            }
        } catch (Exception e) {
            return (new Document()).append("rc", "11").append("message", "invalid request format");
        }
    }
    
    /*
    IN DEVELOPMENT: 
    JSON OBJECT DIRECT RETURN (to stop using Document Object)
    */
//    @RequestMapping(value = "/jsonobject", method = RequestMethod.POST)
//    public String returnByNik(@Valid @RequestBody NikRequest nr) throws Exception {
//        try {
//            Integer.parseInt(nr.getNik());
//            if (repo.findByNik(nr.getNik())==null) {
//                return ((new JSONObject()).put("rc", "10").put("message", "JSON entry DNE")).toString();
//            } else {
//                return ((new JSONObject()).put("rc", "00").put("message", "success").put("data", 
//                        (new Employee(repo.findByNik(nr.getNik()), false ))
//                        )).toString();
//            }
//        } catch (Exception e) {
//            return ((new JSONObject()).put("rc", "11").put("message", "JSON Incorrect format")).toString();
//        }
//    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Document searchByNik(@Valid @RequestBody NikRequest nr) throws Exception {
        try {
            Integer.parseInt(nr.getNik());
            if (repo.findByNik(nr.getNik()) == null) {
                return (new Document()).append("rc", "10").append("message", "entry DNE");
            } else {
                return (new Document()).append("rc", "00").append("message", "success")
                        .append("data", (new Employee(
                                repo.findByNik(nr.getNik()), false)));
            }
        } catch (Exception e) {
            return (new Document()).append("rc", "11").append("message", "invalid request format");
        }
    }
}
