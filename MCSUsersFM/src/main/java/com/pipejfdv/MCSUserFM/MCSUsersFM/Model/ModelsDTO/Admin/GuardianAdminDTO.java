package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.GuardianPublicDTO;
import lombok.Getter;

import java.util.UUID;

@Getter
public class GuardianAdminDTO extends GuardianPublicDTO {
    private String username;
    private String accountType;
    private String document;
    private String documentType;

    public GuardianAdminDTO(UUID id, String name, String lastname, String phone, String biography, String email,
                            String username, String accountType, String document, String documentType) {
        super(id, name, lastname, phone, biography, email);
        this.username = username;
        this.accountType = accountType;
        this.document = document;
        this.documentType = documentType;
    }
}
