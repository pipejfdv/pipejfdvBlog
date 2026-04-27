package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;
@Getter
public class ProfilesPublicDTO {
    private UUID id;
    private String nameProfile;

    public ProfilesPublicDTO(UUID id, String nameProfile) {
        this.id = id;
        this.nameProfile = nameProfile;
    }
}
