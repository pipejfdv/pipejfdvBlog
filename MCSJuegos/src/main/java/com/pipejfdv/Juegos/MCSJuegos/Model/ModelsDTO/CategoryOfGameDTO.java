package com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO;

import lombok.Getter;

import java.util.UUID;

/*
* DTO for transferring game category data between layers
* Contains category ID, name, and description
*/
@Getter
public class CategoryOfGameDTO {
    private UUID id;
    private String name;
    private String description;

    public CategoryOfGameDTO(UUID id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
