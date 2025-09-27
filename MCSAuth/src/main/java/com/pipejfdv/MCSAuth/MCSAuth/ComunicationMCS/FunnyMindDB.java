package com.pipejfdv.MCSAuth.MCSAuth.ComunicationMCS;

import com.pipejfdv.MCSAuth.MCSAuth.Models.ModelsDTO.UserPassDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

/*
*   This interface connect with MCS-USER-FM
*/
@FeignClient(name = "MCS-USER-FM", url = "localhost:8090/funnyMind")
public interface FunnyMindDB {
    /*
    *   Method take data of user
    */
    @GetMapping("/User/Auth/info/{username}")
    UserPassDTO getCredentialsUser(@PathVariable String username);
}
