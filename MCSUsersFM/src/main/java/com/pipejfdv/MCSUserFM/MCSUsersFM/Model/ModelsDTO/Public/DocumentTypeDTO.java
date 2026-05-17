package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

/*
* DTO for transferring document type data
*/
@Getter
public class DocumentTypeDTO {
    private final UUID id;
    private final String type;

    /*
    * Creates a DocumentTypeDTO with ID and type
    * @Param id the document type UUID
    * @Param type the document type name
    */
    public DocumentTypeDTO(UUID id, String type){
        this.id = id;
        this.type = type;
    }
}
