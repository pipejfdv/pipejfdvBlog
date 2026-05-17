package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;
/*
* Public DTO for transferring profile data without access timestamps
*/
@Getter
public class ProfilesPublicDTO {
    private UUID id;
    private String nameProfile;

    /*
    * Creates a ProfilesPublicDTO with ID and profile name
    * @Param id the profile UUID
    * @Param nameProfile the profile name
    */
    public ProfilesPublicDTO(UUID id, String nameProfile) {
        this.id = id;
        this.nameProfile = nameProfile;
    }
}
