package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RelationshipsDTO {
    private UUID id;
    private String relationship;

    public RelationshipsDTO(UUID id, String relationship) {
        this.id = id;
        this.relationship = relationship;
    }
}
