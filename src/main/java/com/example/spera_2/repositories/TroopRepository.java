/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.repositories;

import com.example.spera_2.models.Troop;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author rakhadjo
 */
public interface TroopRepository extends MongoRepository<Troop, String> {
    Troop findByNik(@Param("nik") String nik);
}
