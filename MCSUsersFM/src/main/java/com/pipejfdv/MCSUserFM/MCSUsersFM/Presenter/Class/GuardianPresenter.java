package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.GuardianPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.GuardianAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.GuardianServices;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/*
* Presenter for Guardian operations
* Acts as intermediary between controller and GuardianServices
* Maps entities to DTOs for public and admin views
*/
@Component
public class GuardianPresenter implements GuardianContract.Presenter {
    private final GuardianServices guardianServices;

    public GuardianPresenter(GuardianServices guardianServices) {
        this.guardianServices = guardianServices;
    }

    /*
    * Retrieves a guardian by ID and returns a public DTO for User/Therapist role
    * @Params id The UUID of the guardian
    * @Return GuardianPublicDTO The public DTO of the guardian
    * @Throw IdNotFoundException if guardian not found
    */
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

    /*
    * Retrieves a guardian by ID and returns an admin DTO with full details
    * @Params id The UUID of the guardian
    * @Return GuardianAdminDTO The admin DTO of the guardian
    * @Throw IdNotFoundException if guardian not found
    */
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

    /*
    * Retrieves all guardians and returns public DTOs
    * @Return List of public DTOs for all guardians
    */
    @Override
    public List<GuardianPublicDTO> readyGuardians() {
        return guardianServices.getGuardians().stream()
                .map(guardian -> new GuardianPublicDTO(
                        guardian.getId(),
                        guardian.getName(),
                        guardian.getLastname(),
                        guardian.getPhone(),
                        guardian.getBiography(),
                        guardian.getUser().getEmail()
                        ))
                .toList();
    }

    /*
    * Deletes a guardian by ID and returns confirmation
    * @Params id The UUID of the guardian to delete
    * @Return Boolean True if deletion was successful
    * @Throw IdNotFoundException if guardian not found
    */
    @Override
    public Boolean readyToDeleteGuardian(UUID id) throws IdNotFoundException {
        guardianServices.deleteGuardian(id);
        return true;
    }

    /*
    * Creates a new guardian linked to a user and document type
    * @Params guardian The guardian entity to create
    * @Params idUserAssignment The UUID of the user to assign
    * @Params typeDocument The UUID of the document type
    * @Return Guardian The created guardian entity
    * @Throw DuplicateElementException if guardian already exists
    */
    @Override
    public Guardian readyToCreateUser(Guardian guardian, UUID idUserAssignment, UUID typeDocument) throws DuplicateElementException {
        return guardianServices.createGuardian(guardian, idUserAssignment, typeDocument);
    }

    /*
    * Updates a guardian and returns a public DTO
    * @Params id The UUID of the guardian to update
    * @Params guardian The guardian entity with updated data
    * @Return GuardianPublicDTO The public DTO of the updated guardian
    * @Throw IdNotFoundException if guardian not found
    */
    @Override
    public GuardianPublicDTO readyToUpdateUser(UUID id, Guardian guardian) throws IdNotFoundException {
        Guardian newGuardian = guardianServices.updateGuardian(id, guardian);
        return new GuardianPublicDTO(
                newGuardian.getId(),
                newGuardian.getName(),
                newGuardian.getLastname(),
                newGuardian.getPhone(),
                newGuardian.getBiography(),
                newGuardian.getUser().getEmail()
        );
    }

    /*
    * Searches for a guardian by the associated user
    * @Params idUSer The user entity to search by
    * @Return Guardian The found guardian entity
    */
    @Override
    public Guardian readyToSearchGuardianForUserId(User idUSer) {
        return guardianServices.searchGuardianForUserId(idUSer);
    }

}
