package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public;

import lombok.Getter;

import java.util.UUID;

/*
* DTO for transferring data needed to create a guardian-children relationship
*/
@Getter
public class CreationGuardianChildrenDTO {
    private final UUID guardian;
    private final UUID child;
    private final UUID relationship;

    /*
    * Creates a CreationGuardianChildrenDTO with the three entity IDs
    * @Param guardian the guardian UUID
    * @Param child the child UUID
    * @Param relationship the relationship UUID
    */
    public CreationGuardianChildrenDTO(UUID guardian, UUID child, UUID relationship) {
        this.guardian = guardian;
        this.child = child;
        this.relationship = relationship;
    }
}
