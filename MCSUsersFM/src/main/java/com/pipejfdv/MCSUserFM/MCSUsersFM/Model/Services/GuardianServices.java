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
import java.util.UUID;

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

    @Override
    public Guardian getGuardian(UUID id) throws IdNotFoundException {
        return repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException(id));
    }

    @Override
    public List<Guardian> getGuardians() {
        return repository.findAll();
    }

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

    @Override
    public void deleteGuardian(UUID id) throws IdNotFoundException {
        Guardian guardian = getGuardian(id);
        repository.delete(guardian);
    }

    @Override
    public Guardian updateGuardian(UUID id, Guardian guardian) throws IdNotFoundException {
        Guardian oldGuardian = getGuardian(id);
        oldGuardian.setName(guardian.getName());
        oldGuardian.setLastname(guardian.getLastname());
        oldGuardian.setPhone(guardian.getPhone());
        oldGuardian.setBiography(guardian.getBiography());
        return repository.save(oldGuardian);
    }
}
