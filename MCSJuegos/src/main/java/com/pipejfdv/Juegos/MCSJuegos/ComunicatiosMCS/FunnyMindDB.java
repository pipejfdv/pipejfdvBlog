package com.pipejfdv.Juegos.MCSJuegos.ComunicatiosMCS;

import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.ChildDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/*
* Feign client for communicating with MCS-USER-FM microservice
* Fetches child user data from the User FM service
*/
@FeignClient(name = "MCS-USER-FM",
        url = "localhost:8090",
        path = "/funnyMind")
public interface FunnyMindDB {
    /*
	* Fetches a child's public data by ID from the User FM service
	* @Param childrenId UUID of the child
	* @Return ChildDTO containing the child's basic information
	*/
    @GetMapping("/children/getPublic/games/{childrenId}")
    ChildDTO getChildren(@PathVariable UUID childrenId);
}
