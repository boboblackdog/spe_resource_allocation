/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.spera_2.repositories;

import com.example.spera_2.models.user_bearer;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author rakhadjo
 */

public interface UserBearerRepository extends CrudRepository<user_bearer, Integer>{
    user_bearer findByParams(String nik, String bearer_token, String device_id);
}
