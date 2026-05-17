package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.GuardianChildren;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.GuardianChildrenRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianChildrenContract;
import org.aspectj.asm.internal.Relationship;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
* Service for managing the relationship between guardians and children
* Handles creation and update of guardian-children associations
*/
@Service
public class GuardinChildrenService implements GuardianChildrenContract.Model{
    private final GuardianChildrenRepository guardianChildrenRepository;
    private final GuardianServices guardianService;
    private final ChildrenService childrenService;
    private final RelationshipsService relationshipService;

    public GuardinChildrenService(GuardianChildrenRepository guardianChildrenRepository,
                                  GuardianServices guardianService,
                                  ChildrenService childrenService,
                                  RelationshipsService relationshipService) {
        this.guardianChildrenRepository = guardianChildrenRepository;
        this.guardianService = guardianService;
        this.childrenService = childrenService;
        this.relationshipService = relationshipService;
    }

    /*
    * Creates a new association between a guardian, a child, and a relationship type
    * @Params guardianId The UUID of the guardian
    * @Params childId The UUID of the child
    * @Params relationshipId The UUID of the relationship type
    * @Return GuardianChildren The created association
    * @Throw IdNotFoundException if guardian, child or relationship not found
    */
    @Override
    public GuardianChildren createGuardianChildren(UUID guardianId, UUID childId, UUID relationshipId) throws IdNotFoundException {
        Guardian guardian = guardianService.getGuardian(guardianId);
        Children children = childrenService.getChildren(childId);
        Relationships relationship = relationshipService.getRelationships(relationshipId, null);
        return guardianChildrenRepository.save(new GuardianChildren(guardian, children, relationship));
    }

    /*
    * Updates the relationship type in a guardian-children association
    * @Params guardianChildId The UUID of the association to update
    * @Params relationship The UUID of the new relationship type
    * @Return GuardianChildren The updated association
    * @Throw IdNotFoundException if the association is not found
    */
    @Override
    public GuardianChildren updateGuardianChildren(UUID guardianChildId, UUID relationship) throws IdNotFoundException {
        GuardianChildren guardianChildren = guardianChildrenRepository.findById(guardianChildId).orElseThrow(() -> new IdNotFoundException(guardianChildId));
        guardianChildren.setRelationships(relationshipService.getRelationships(relationship, null));
        return guardianChildrenRepository.save(guardianChildren);
    }
}
