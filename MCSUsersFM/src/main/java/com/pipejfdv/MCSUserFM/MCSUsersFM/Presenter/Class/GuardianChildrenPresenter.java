package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.ParametersNotReceived;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.WrongParametersException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.GuardianChildren;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.CreationGuardianChildrenDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.GuardinChildrenService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianChildrenContract;
import org.springframework.stereotype.Component;

import java.util.UUID;

/*
* Presenter for Guardian-Children association operations
* Acts as intermediary between controller and GuardinChildrenService
*/
@Component
public class GuardianChildrenPresenter implements GuardianChildrenContract.Presenter {
    private final GuardinChildrenService guardinChildrenService;

    public GuardianChildrenPresenter(GuardinChildrenService guardinChildrenService) {
        this.guardinChildrenService = guardinChildrenService;
    }

    /*
    * Creates a new guardian-children association from a DTO
    * @Params packsIds The DTO containing guardian, child and relationship IDs
    * @Return GuardianChildren The created association
    * @Throw IdNotFoundException if any entity not found
    * @Throw ParametersNotReceived if parameters are missing
    * @Throw WrongParametersException if parameters are invalid
    */
    @Override
    public GuardianChildren createGuardianChildren(CreationGuardianChildrenDTO packsIds) throws IdNotFoundException, ParametersNotReceived, WrongParametersException {
        UUID guardianId = packsIds.getGuardian();
        UUID childId = packsIds.getChild();
        UUID relationshipId = packsIds.getRelationship();
        return guardinChildrenService.createGuardianChildren(guardianId, childId, relationshipId);
    }

    /*
    * Updates the relationship in a guardian-children association
    * @Params guardianChildId The UUID of the association to update
    * @Params relationshipId The UUID of the new relationship type
    * @Return GuardianChildren The updated association
    * @Throw IdNotFoundException if the association is not found
    */
    @Override
    public GuardianChildren updateGuardianChildren(UUID guardianChildId, UUID relationshipId) throws IdNotFoundException {
        return guardinChildrenService.updateGuardianChildren(guardianChildId, relationshipId);
    }
}
