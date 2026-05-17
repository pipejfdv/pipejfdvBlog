package com.pipejfdv.MCSAuth.MCSAuth.ComunicationMCS;

import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

/*
* Feign client interface for communicating with the MCSUsersFM microservice.
*/
@FeignClient(name = "MCS-USER-FM",
        url = "localhost:8090",
        path = "/funnyMind")
public interface FunnyMindDB {
    /*
    * Retrieves user credentials from MCSUsersFM by username
    * @Param username String the username to look up
    * @Return UserPassDTO the user credentials data
    */
    @GetMapping("/User/Auth/info/{username}")
    UserPassDTO getCredentialsUser(@PathVariable String username);
}
