package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.GuardianDTO;
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

    @Override
    public GuardianDTO readyGuardian(UUID id, User user) throws IdNotFoundException {
        Guardian guardian = guardianServices.getGuardian(id);
        if(user.getAccountType().getName().equals("Admin")){
            return new GuardianDTO(
                    guardian.getName(),
                    guardian.getLastname(),
                    guardian.getPhone(),
                    guardian.getBiography(),
                    guardian.getUser().getUsername(),
                    guardian.getUser().getEmail(),
                    guardian.getUser().getAccountType().getName(),
                    guardian.getDocument(),
                    guardian.getDocumentType().getType());
        }
        return new GuardianDTO(
                guardian.getName(),
                guardian.getLastname(),
                guardian.getPhone(),
                guardian.getBiography(),
                null,
                guardian.getUser().getEmail(),
                null,
                guardian.getDocument(),
                guardian.getDocumentType().getType());
    }

    @Override
    public List<GuardianDTO> readyGuardians() {
        List<GuardianDTO> listDTO = guardianServices.getGuardians().stream()
                .map(guardian -> new GuardianDTO(
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
}
