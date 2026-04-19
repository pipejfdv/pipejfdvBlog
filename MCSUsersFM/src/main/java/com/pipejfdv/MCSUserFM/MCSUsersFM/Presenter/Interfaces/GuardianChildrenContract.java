package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.ParametersNotReceived;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.WrongParametersException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.GuardianChildren;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.CreationGuardianChildrenDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface GuardianChildrenContract {
    interface View {
        ResponseEntity<ApiResponseOK<GuardianChildren>> showCreateGuardianChildren(CreationGuardianChildrenDTO packsIds);
        ResponseEntity<ApiResponseOK<GuardianChildren>> showGetGuardianChildren(UUID guardianChildId);
        ResponseEntity<ApiResponseOK<GuardianChildren>> showUpdateGuardianChildren(UUID guardianChildId, CreationGuardianChildrenDTO packsIds);
        ResponseEntity<ApiResponseOK<List<GuardianChildren>>> showGetAllGuardianChildren();
    }

    interface Presenter {
        GuardianChildren createGuardianChildren(CreationGuardianChildrenDTO packsIds) throws IdNotFoundException, ParametersNotReceived, WrongParametersException;
        GuardianChildren getGuardianChildren(UUID guardianChildId) throws IdNotFoundException;
        GuardianChildren updateGuardianChildren(UUID guardianChildId, CreationGuardianChildrenDTO packsIds) throws IdNotFoundException;
        List<GuardianChildren> getAllGuardianChildren();
    }

    interface Model {
        GuardianChildren createGuardianChildren(UUID guardian, UUID child, UUID relationship) throws IdNotFoundException;
        GuardianChildren getGuardianChildren(UUID guardianChildId) throws IdNotFoundException;
        GuardianChildren updateGuardianChildren(UUID guardianChildId, UUID guardian, UUID child, UUID relationship) throws IdNotFoundException;
        List<GuardianChildren> getAllGuardianChildren();
    }
}
