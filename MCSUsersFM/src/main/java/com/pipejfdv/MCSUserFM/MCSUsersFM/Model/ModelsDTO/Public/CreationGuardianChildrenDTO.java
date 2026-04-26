package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreationGuardianChildrenDTO {
    private final UUID guardian;
    private final UUID child;
    private final UUID relationship;

    public CreationGuardianChildrenDTO(UUID guardian, UUID child, UUID relationship) {
        this.guardian = guardian;
        this.child = child;
        this.relationship = relationship;
    }
}
