package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.Responses.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface GuardianContract {
    interface View{
        ResponseEntity<ApiResponse<GuardianDTO>> showGuardian(UUID idSearch, User user) throws IdNotFoundException, DuplicateElementException;
        ResponseEntity<ApiResponse<List<GuardianDTO>>> showGuardians();
        ResponseEntity<ApiResponse<GuardianDTO>> showDeleteGuardian(UUID id);
        ResponseEntity<ApiResponse<GuardianDTO>> showCreateGuardian(Guardian guardian, UUID idUserAssignment, UUID typeDocument);
        ResponseEntity<ApiResponse<GuardianDTO>> showEditGuardian(UUID id,Guardian guardian);
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
