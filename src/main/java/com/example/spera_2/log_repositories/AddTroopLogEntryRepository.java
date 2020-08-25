/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.log_repositories;

import com.example.spera_2.log.AddTroopLogEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author rakhadjo
 */
public interface AddTroopLogEntryRepository extends MongoRepository<AddTroopLogEntry, String> {
    
}
