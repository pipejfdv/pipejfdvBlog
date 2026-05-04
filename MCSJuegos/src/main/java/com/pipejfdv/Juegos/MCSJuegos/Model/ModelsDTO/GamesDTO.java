package com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GamesDTO {
    private UUID id;
    private String name;

    public GamesDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
