package com.pipejfdv.Juegos.MCSJuegos.ComunicatiosMCS;

import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.ChildDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/*
* Communication con MCS-USER-FM
*/
@FeignClient(name = "MCS-USER-FM",
        url = "localhost:8090",
        path = "/funnyMind")
public interface FunnyMindDB {
    /*
    *   Method take data of user
    */
    @GetMapping("/children/getPublic/games/{childrenId}")
    ChildDTO getChildren(@PathVariable UUID childrenId);
}
