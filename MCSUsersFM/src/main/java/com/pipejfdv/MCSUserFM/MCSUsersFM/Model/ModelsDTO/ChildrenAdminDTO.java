package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO;

import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;
@Getter
public class ChildrenAdminDTO extends ChildrenPublicDTO{
    private String classificationTCE;
    private String documentType;

    public ChildrenAdminDTO(UUID id, String names, String lastName, LocalDate birthDate, String tceClassification, String documentType, String classificationTCE) {
        super(id, names, lastName, birthDate, tceClassification);
        this.documentType = documentType;
        this.classificationTCE = classificationTCE;
    }
}
