package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ProfilesAdminDTO {
    private UUID id;
    private String nameProfile;
    private LocalDateTime lastAccess;

    public ProfilesAdminDTO(UUID id, String nameProfile, LocalDateTime lastAccess) {
        this.id = id;
        this.nameProfile = nameProfile;
        this.lastAccess = lastAccess;
    }
}
