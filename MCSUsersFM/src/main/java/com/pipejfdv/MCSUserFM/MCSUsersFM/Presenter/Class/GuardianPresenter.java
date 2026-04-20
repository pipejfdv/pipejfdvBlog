package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.GuardianServices;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class GuardianPresenter implements GuardianContract.Presenter {
    private final GuardianServices guardianServices;

    public GuardianPresenter(GuardianServices guardianServices) {
        this.guardianServices = guardianServices;
    }

    
    // Nuevo método para rol de User/Therapist - retorna GuardianPublicDTO
    public GuardianPublicDTO readyGuardianPublic(UUID id) throws IdNotFoundException {
        Guardian guardian = guardianServices.getGuardian(id);
        return new GuardianPublicDTO(
                guardian.getId(),
                guardian.getName(),
                guardian.getLastname(),
                guardian.getPhone(),
                guardian.getBiography(),
                guardian.getUser().getEmail());
    }

    // Nuevo método para rol de Admin - retorna GuardianAdminDTO
    public GuardianAdminDTO readyGuardianAdmin(UUID id) throws IdNotFoundException {
        Guardian guardian = guardianServices.getGuardian(id);
        return new GuardianAdminDTO(
                guardian.getId(),
                guardian.getName(),
                guardian.getLastname(),
                guardian.getPhone(),
                guardian.getBiography(),
                guardian.getUser().getEmail(),
                guardian.getUser().getUsername(),
                guardian.getUser().getAccountType().getName(),
                guardian.getDocument(),
                guardian.getDocumentType().getType());
    }

    @Override
    public List<GuardianDTO> readyGuardians() {
        List<GuardianDTO> listDTO = guardianServices.getGuardians().stream()
                .map(guardian -> new GuardianDTO(
                        guardian.getId(),
                        guardian.getName(),
                        guardian.getLastname(),
                        guardian.getPhone(),
                        null,
                        guardian.getUser().getUsername(),
                        guardian.getUser().getEmail(),
                        guardian.getUser().getAccountType().getName(),
                        guardian.getDocument(),
                        guardian.getDocumentType().getType()))
                .collect(Collectors.toList());
        return listDTO;
    }

    @Override
    public void readyToDeleteGuardian(UUID id) throws IdNotFoundException {
        guardianServices.deleteGuardian(id);
    }

    @Override
    public Guardian readyToCreateUser(Guardian guardian, UUID idUserAssignment, UUID typeDocument) throws DuplicateElementException {
        return guardianServices.createGuardian(guardian, idUserAssignment, typeDocument);
    }

    @Override
    public Guardian readyToUpdateUser(UUID id, Guardian guardian) throws IdNotFoundException {
        return guardianServices.updateGuardian(id, guardian);
    }

    @Override
    public Guardian readyToSearchGuardianForUserId(User idUSer){
        return guardianServices.searchGuardianForUserId(idUSer);
    }
}
