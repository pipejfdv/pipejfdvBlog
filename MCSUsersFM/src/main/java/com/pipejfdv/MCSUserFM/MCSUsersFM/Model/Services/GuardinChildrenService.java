package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.GuardianChildren;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.GuardianChildrenRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianChildrenContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GuardinChildrenService implements GuardianChildrenContract.Model{
    private final GuardianChildrenRepository guardianChildrenRepository;

    public GuardinChildrenService(GuardianChildrenRepository guardianChildrenRepository) {
        this.guardianChildrenRepository = guardianChildrenRepository;
    }

    @Override
    public GuardianChildren createGuardianChildren(UUID guardian, UUID child, UUID relationship) throws IdNotFoundException {

        //GuardianChildren guardianChildren = new GuardianChildren(guardian, child, relationship);
        return null;
    }

    @Override
    public GuardianChildren getGuardianChildren(UUID guardianChildId) throws IdNotFoundException {
        return null;
    }

    @Override
    public GuardianChildren updateGuardianChildren(UUID guardianChildId, UUID guardian, UUID child, UUID relationship) throws IdNotFoundException {
        return null;
    }

    @Override
    public List<GuardianChildren> getAllGuardianChildren() {
        return List.of();
    }
}
