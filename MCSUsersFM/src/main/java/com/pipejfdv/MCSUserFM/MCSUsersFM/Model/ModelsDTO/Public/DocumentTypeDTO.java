package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

@Getter
public class DocumentTypeDTO {
    private final UUID id;
    private final String type;

    public DocumentTypeDTO(UUID id, String type){
        this.id = id;
        this.type = type;
    }
}
