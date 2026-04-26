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

@Component
public class GuardianChildrenPresenter implements GuardianChildrenContract.Presenter {
    private final GuardinChildrenService guardinChildrenService;

    public GuardianChildrenPresenter(GuardinChildrenService guardinChildrenService) {
        this.guardinChildrenService = guardinChildrenService;
    }

    @Override
    public GuardianChildren createGuardianChildren(CreationGuardianChildrenDTO packsIds) throws IdNotFoundException, ParametersNotReceived, WrongParametersException {
        UUID guardianId = packsIds.getGuardian();
        UUID childId = packsIds.getChild();
        UUID relationshipId = packsIds.getRelationship();
        return guardinChildrenService.createGuardianChildren(guardianId, childId, relationshipId);
    }

    @Override
    public GuardianChildren updateGuardianChildren(UUID guardianChildId, UUID relationshipId) throws IdNotFoundException {
        return guardinChildrenService.updateGuardianChildren(guardianChildId, relationshipId);
    }
}
