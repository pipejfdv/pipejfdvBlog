package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ChildrenPublicDTO;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;
/*
* Admin DTO for transferring children data including document information
*/
@Getter
public class ChildrenAdminDTO extends ChildrenPublicDTO {
    private String documentType;
    private Long document;

    /*
    * Creates a ChildrenAdminDTO extending ChildrenPublicDTO with document data
    * @Param id the child UUID
    * @Param names the child first name
    * @Param lastName the child last name
    * @Param birthDate the child birth date
    * @Param tceClassification the TCE classification name
    * @Param documentType the document type name
    * @Param document the document number
    */
    public ChildrenAdminDTO(UUID id, String names, String lastName, LocalDate birthDate, String tceClassification, String documentType, Long document) {
        super(id, names, lastName, birthDate, tceClassification);
        this.documentType = documentType;
        this.document = document;
    }
}
