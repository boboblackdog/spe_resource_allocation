/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.repositories;

import com.example.spera_2.models.user;
import com.example.spera_2.models.UserLogin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author rakhadjo
 */
@Repository
public interface UserRepository extends CrudRepository<user, Integer>{
    boolean findByNik(String nik);
}
