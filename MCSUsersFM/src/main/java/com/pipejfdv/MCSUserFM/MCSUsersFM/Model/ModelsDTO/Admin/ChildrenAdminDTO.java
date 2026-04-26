package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ChildrenPublicDTO;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;
@Getter
public class ChildrenAdminDTO extends ChildrenPublicDTO {
    private String documentType;
    private Long document;

    public ChildrenAdminDTO(UUID id, String names, String lastName, LocalDate birthDate, String tceClassification, String documentType, Long document) {
        super(id, names, lastName, birthDate, tceClassification);
        this.documentType = documentType;
        this.document = document;
    }
}
