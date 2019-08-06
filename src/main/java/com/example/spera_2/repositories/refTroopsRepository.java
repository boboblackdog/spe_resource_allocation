/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.repositories;

import com.example.spera_2.models.refTroops;
//import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 *
 * @author rakhadjo
 */
//@RepositoryRestResource(collectionResourceRel = "refTroops", path = "spera")
public interface refTroopsRepository extends MongoRepository<refTroops, String>{
    refTroops findByNik(@Param("nik") String nik);
}
