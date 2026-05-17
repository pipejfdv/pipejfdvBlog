package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.GuardianPublicDTO;
import lombok.Getter;

import java.util.UUID;

/*
* Admin DTO for transferring guardian data including account and document info
*/
@Getter
public class GuardianAdminDTO extends GuardianPublicDTO {
    private String username;
    private String accountType;
    private String document;
    private String documentType;

    /*
    * Creates a GuardianAdminDTO extending GuardianPublicDTO with admin fields
    * @Param id the guardian UUID
    * @Param name the guardian first name
    * @Param lastname the guardian last name
    * @Param phone the phone number
    * @Param biography a short biography
    * @Param email the email address
    * @Param username the associated username
    * @Param accountType the account type name
    * @Param document the document number
    * @Param documentType the document type name
    */
    public GuardianAdminDTO(UUID id, String name, String lastname, String phone, String biography, String email,
                            String username, String accountType, String document, String documentType) {
        super(id, name, lastname, phone, biography, email);
        this.username = username;
        this.accountType = accountType;
        this.document = document;
        this.documentType = documentType;
    }
}
