package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

@Getter
public class GuardianPublicDTO {
    private UUID id;
    private String name;
    private String lastname;
    private String phone;
    private String biography;
    private String email;

    public GuardianPublicDTO(UUID id, String name, String lastname, String phone, String biography, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.biography = biography;
        this.email = email;
    }
}
