package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface GuardianContract {
    interface View{
        ResponseEntity<ApiResponseOK<GuardianDTO>> showGuardian(UUID idSearch, User user) throws IdNotFoundException, DuplicateElementException;
        ResponseEntity<ApiResponseOK<List<GuardianDTO>>> showGuardians();
        ResponseEntity<ApiResponseOK<GuardianDTO>> showDeleteGuardian(UUID id);
        ResponseEntity<ApiResponseOK<GuardianDTO>> showCreateGuardian(Guardian guardian, UUID idUserAssignment, UUID typeDocument);
        ResponseEntity<ApiResponseOK<GuardianDTO>> showEditGuardian(UUID id, Guardian guardian);
    }

    interface Presenter{
        GuardianDTO readyGuardian(UUID idSearch, User user) throws IdNotFoundException;
        List<GuardianDTO> readyGuardians();
        void readyToDeleteGuardian(UUID id) throws IdNotFoundException;
        Guardian readyToCreateUser(Guardian guardian, UUID idUserAssignment, UUID typeDocument) throws DuplicateElementException;
        Guardian readyToUpdateUser(UUID id, Guardian guardian) throws IdNotFoundException;
    }
    interface Model{
        Guardian getGuardian(UUID id) throws IdNotFoundException;
        List<Guardian> getGuardians();
        Guardian createGuardian(Guardian guardian, UUID idUserAssignment, UUID typeDocument) throws DuplicateElementException;
        void deleteGuardian(UUID id) throws IdNotFoundException;
        Guardian updateGuardian(UUID id, Guardian guardian) throws IdNotFoundException;
    }
}
