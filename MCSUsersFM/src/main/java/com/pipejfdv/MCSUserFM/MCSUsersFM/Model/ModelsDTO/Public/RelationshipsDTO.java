package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

/*
* Public DTO for transferring relationship type data
*/
@Getter
public class RelationshipsDTO {
    private UUID id;
    private String relationship;

    /*
    * Creates a RelationshipsDTO with ID and relationship name
    * @Param id the relationship UUID
    * @Param relationship the relationship name
    */
    public RelationshipsDTO(UUID id, String relationship) {
        this.id = id;
        this.relationship = relationship;
    }
}
