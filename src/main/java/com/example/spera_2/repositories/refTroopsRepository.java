/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.repositories;

import com.example.spera_2.models.refTroops;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rakhadjo
 */
public interface refTroopsRepository extends MongoRepository<refTroops, String>{
    refTroops findByNik(@Param("nik") String nik);
}
