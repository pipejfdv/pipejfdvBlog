package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.ParametersNotReceived;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.WrongParametersException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.GuardianChildren;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.CreationGuardianChildrenDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface GuardianChildrenContract {
    interface View {
        ResponseEntity<ApiResponseOK<String>> createGuardianChildren(CreationGuardianChildrenDTO packsIds);
        ResponseEntity<ApiResponseOK<String>> updateGuardianChildren(UUID guardianChildId, UUID relationshipId);
    }

    interface Presenter {
        GuardianChildren createGuardianChildren(CreationGuardianChildrenDTO packsIds) throws IdNotFoundException, ParametersNotReceived, WrongParametersException;
        GuardianChildren updateGuardianChildren(UUID guardianChildId, UUID relationshipId) throws IdNotFoundException;
    }

    interface Model {
        GuardianChildren createGuardianChildren(UUID guardian, UUID child, UUID relationship) throws IdNotFoundException;
        GuardianChildren updateGuardianChildren(UUID guardianChildId, UUID relationship) throws IdNotFoundException;

    }
}
