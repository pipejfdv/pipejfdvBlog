package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TceClassificationDTO {
    private UUID id;
    private String name;

    public TceClassificationDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
