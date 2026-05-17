package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

/*
* Admin DTO for transferring profile data including last access timestamp
*/
@Getter
public class ProfilesAdminDTO {
    private UUID id;
    private String nameProfile;
    private LocalDateTime lastAccess;

    /*
    * Creates a ProfilesAdminDTO with all fields
    * @Param id the profile UUID
    * @Param nameProfile the profile name
    * @Param lastAccess the last access date and time
    */
    public ProfilesAdminDTO(UUID id, String nameProfile, LocalDateTime lastAccess) {
        this.id = id;
        this.nameProfile = nameProfile;
        this.lastAccess = lastAccess;
    }
}
