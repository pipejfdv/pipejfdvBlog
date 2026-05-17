package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Guardian;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.User;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.DocumentTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.GuardianRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.UserRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.GuardianContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
* Service for managing Guardian entities
* Handles CRUD operations and user-linked guardian lookup
*/
@Service
public class GuardianServices implements GuardianContract.Model {
    private final GuardianRepository repository;
    private final UserRepository userRepository;
    private final DocumentTypeRepository documentTypeRepository;

    /*CONSTRUCTOR*/
    public GuardianServices(GuardianRepository repository,  UserRepository userRepository, DocumentTypeRepository documentTypeRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.documentTypeRepository = documentTypeRepository;
    }

    /*CRUD*/

    /*
    * Retrieves a guardian by their ID
    * @Params id The UUID of the guardian
    * @Return Guardian The found guardian entity
    * @Throw IdNotFoundException if guardian not found
    */
    @Override
    public Guardian getGuardian(UUID id) throws IdNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));
    }

    /*
    * Retrieves all guardians from the database
    * @Return List of all guardians
    */
    @Override
    public List<Guardian> getGuardians() {
        return repository.findAll();
    }

    /*
    * Creates a new guardian and links them to a user and document type
    * @Params guardian The guardian entity to create
    * @Params idUserAssignment The UUID of the user to assign
    * @Params typeDocument The UUID of the document type
    * @Return Guardian The saved guardian entity
    * @Throw DuplicateElementException if name and lastname already exist
    */
    @Override
    public Guardian createGuardian(Guardian guardian, UUID idUserAssignment, UUID typeDocument) throws DuplicateElementException {
        if(repository.existsByName(guardian.getName()) && repository.existsByLastname(guardian.getLastname())) {
            throw new DuplicateElementException(guardian.getName());
        }
        User u = userRepository.findById(idUserAssignment).orElseThrow(
                () -> new IdNotFoundException(idUserAssignment));

        DocumentType dt = documentTypeRepository.findById(typeDocument).orElseThrow(
                () -> new IdNotFoundException(typeDocument)
        );
        guardian.setDocumentType(dt);
        guardian.setId(UUID.randomUUID());
        guardian.setUser(u);
        u.setGuardian(guardian);
        userRepository.save(u);
        return repository.save(guardian);
    }

    /*
    * Deletes a guardian and their associated user
    * @Params id The UUID of the guardian to delete
    * @Throw IdNotFoundException if guardian not found
    */
    @Override
    public void deleteGuardian(UUID id) throws IdNotFoundException {
        Guardian guardian = repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        User user = guardian.getUser();
        repository.delete(guardian);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    /*
    * Updates a guardian's name, lastname, phone and biography
    * @Params id The UUID of the guardian to update
    * @Params guardian The guardian entity with updated data
    * @Return Guardian The updated guardian entity
    * @Throw IdNotFoundException if guardian not found
    */
    @Override
    public Guardian updateGuardian(UUID id, Guardian guardian) throws IdNotFoundException {
        Guardian oldGuardian = repository.findById(id).orElseThrow(() -> new IdNotFoundException(id));
        oldGuardian.setName(guardian.getName());
        oldGuardian.setLastname(guardian.getLastname());
        oldGuardian.setPhone(guardian.getPhone());
        oldGuardian.setBiography(guardian.getBiography());
        return repository.save(oldGuardian);
    }
    /*
    * Searches for a guardian using the associated user
    * @Params user The user entity to search by
    * @Return Guardian The found guardian entity
    * @Throw IdNotFoundException if no guardian found for the user
    */
    @Override
    public Guardian searchGuardianForUserId (User user) throws IdNotFoundException{
        return repository.findGuardianForUser(user)
                .orElseThrow(() -> new IdNotFoundException(user.getId()));
    }
}
