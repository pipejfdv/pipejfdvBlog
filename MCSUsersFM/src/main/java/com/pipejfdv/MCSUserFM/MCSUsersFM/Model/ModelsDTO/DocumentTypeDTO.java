package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO;

import lombok.Getter;

@Getter
public class DocumentTypeDTO {
    private final String nameDocumentType;

    public DocumentTypeDTO(String name){
        this.nameDocumentType = name;
    }
}
