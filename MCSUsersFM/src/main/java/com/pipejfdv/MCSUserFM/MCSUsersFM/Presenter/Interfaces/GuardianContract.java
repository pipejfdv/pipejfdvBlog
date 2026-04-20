package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface GuardianContract {
    interface View{
        ResponseEntity<ApiResponseOK<List<GuardianDTO>>> showGuardians();
        ResponseEntity<ApiResponseOK<GuardianDTO>> showDeleteGuardian(UUID id);
        ResponseEntity<ApiResponseOK<GuardianDTO>> showCreateGuardian(Guardian guardian, UUID idUserAssignment, UUID typeDocument);
        ResponseEntity<ApiResponseOK<GuardianDTO>> showEditGuardian(UUID id, Guardian guardian);
        ResponseEntity<ApiResponseOK<GuardianDTO>> showToSearchGuardianForUserId (User userId);
        
        // Nuevos métodos específicos por rol
        ResponseEntity<ApiResponseOK<GuardianPublicDTO>> showGuardianPublic(String idSearch, Authentication authentication);
        ResponseEntity<ApiResponseOK<GuardianAdminDTO>> showGuardianAdmin(String idSearch, Authentication authentication);
    }

    interface Presenter{
        List<GuardianDTO> readyGuardians();
        void readyToDeleteGuardian(UUID id) throws IdNotFoundException;
        Guardian readyToCreateUser(Guardian guardian, UUID idUserAssignment, UUID typeDocument) throws DuplicateElementException;
        Guardian readyToUpdateUser(UUID id, Guardian guardian) throws IdNotFoundException;
        // search for guardian using user ID
        Guardian readyToSearchGuardianForUserId(User idUSer);
        
        // Nuevos métodos específicos por rol
        GuardianPublicDTO readyGuardianPublic(UUID id) throws IdNotFoundException;
        GuardianAdminDTO readyGuardianAdmin(UUID id) throws IdNotFoundException;
    }
    interface Model{
        Guardian getGuardian(UUID id) throws IdNotFoundException;
        List<Guardian> getGuardians();
        Guardian createGuardian(Guardian guardian, UUID idUserAssignment, UUID typeDocument) throws DuplicateElementException;
        void deleteGuardian(UUID id) throws IdNotFoundException;
        Guardian updateGuardian(UUID id, Guardian guardian) throws IdNotFoundException;
        // search for guardian using user ID
        Guardian searchGuardianForUserId (User idUser);
    }
}
