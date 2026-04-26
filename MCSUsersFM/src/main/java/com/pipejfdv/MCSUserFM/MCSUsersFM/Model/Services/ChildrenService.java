package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.ChildrenRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.DocumentTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.ChildrenContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ChildrenService implements ChildrenContract.Model {
    private final ChildrenRepository childrenRepository;
    private final DocumentTypeRepository documentTypeRepository;

    public ChildrenService(ChildrenRepository childrenRepository, DocumentTypeRepository documentTypeRepository) {
        this.childrenRepository = childrenRepository;
        this.documentTypeRepository = documentTypeRepository;
    }

    @Override
    public Children createChildren(Children children) throws DuplicateElementException {
        if(childrenRepository.existsByDocument(children.getDocument())) {
            throw new DuplicateElementException("Children with document " + children.getDocument() + " already exists");
        }
        return childrenRepository.save(children);
    }

    @Override
    public Children getChildren(UUID childrenId) throws IdNotFoundException {
        return childrenRepository.findById(childrenId).orElseThrow(() -> new IdNotFoundException(childrenId));
    }

    @Override
    public Children updateChildren(UUID childrenId, Children children) throws IdNotFoundException {
        Children childrenToUpdate = getChildren(childrenId);
        if(childrenToUpdate == null){
            throw new IdNotFoundException(childrenId);
        }
        childrenToUpdate.setNames(children.getNames());
        childrenToUpdate.setLastName(children.getLastName());
        return childrenRepository.save(childrenToUpdate);
    }

    @Override
    public List<Children> getChildrenByGuardian(UUID guardianId) {
        return childrenRepository.getChildrenByGuardianId(guardianId);
    }

    @Override
    public List<Children> getAllChildren() {
        return childrenRepository.findAll();
    }

    @Override
    public boolean deleteChildren(UUID childrenId) throws IdNotFoundException {
        Children children = getChildren(childrenId);
        if(children == null){
            throw new IdNotFoundException(childrenId);
        }
        childrenRepository.delete(childrenRepository.getReferenceById(childrenId));
        return true;
    }
}
