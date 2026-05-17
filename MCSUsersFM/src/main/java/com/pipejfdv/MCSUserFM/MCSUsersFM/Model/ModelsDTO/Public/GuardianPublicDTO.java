package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

/*
* Public DTO for transferring guardian data without sensitive account fields
*/
@Getter
public class GuardianPublicDTO {
    private UUID id;
    private String name;
    private String lastname;
    private String phone;
    private String biography;
    private String email;

    /*
    * Creates a GuardianPublicDTO with basic contact information
    * @Param id the guardian UUID
    * @Param name the guardian first name
    * @Param lastname the guardian last name
    * @Param phone the phone number
    * @Param biography a short biography
    * @Param email the email address
    */
    public GuardianPublicDTO(UUID id, String name, String lastname, String phone, String biography, String email) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.biography = biography;
        this.email = email;
    }
}
