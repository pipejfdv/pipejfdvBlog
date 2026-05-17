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

/*
* Service for managing Children entities
* Handles CRUD operations and queries by guardian
*/
@Service
public class ChildrenService implements ChildrenContract.Model {
    private final ChildrenRepository childrenRepository;
    private final DocumentTypeRepository documentTypeRepository;

    public ChildrenService(ChildrenRepository childrenRepository, DocumentTypeRepository documentTypeRepository) {
        this.childrenRepository = childrenRepository;
        this.documentTypeRepository = documentTypeRepository;
    }

    /*
    * Creates a new children record
    * @Params children The children entity to create
    * @Return Children The saved children entity
    * @Throw DuplicateElementException if document already exists
    */
    @Override
    public Children createChildren(Children children) throws DuplicateElementException {
        if(childrenRepository.existsByDocument(children.getDocument())) {
            throw new DuplicateElementException("Children with document " + children.getDocument() + " already exists");
        }
        return childrenRepository.save(children);
    }

    /*
    * Retrieves a children record by its ID
    * @Params childrenId The UUID of the children
    * @Return Children The found children entity
    * @Throw IdNotFoundException if children not found
    */
    @Override
    public Children getChildren(UUID childrenId) throws IdNotFoundException {
        return childrenRepository.findById(childrenId).orElseThrow(() -> new IdNotFoundException(childrenId));
    }

    /*
    * Updates names and last name of a children record
    * @Params childrenId The UUID of the children to update
    * @Params children The children entity with updated data
    * @Return Children The updated children entity
    * @Throw IdNotFoundException if children not found
    */
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

    /*
    * Retrieves all children associated with a guardian
    * @Params guardianId The UUID of the guardian
    * @Return List of children for the guardian
    */
    @Override
    public List<Children> getChildrenByGuardian(UUID guardianId) {
        return childrenRepository.getChildrenByGuardianId(guardianId);
    }

    /*
    * Retrieves all children records
    * @Return List of all children
    */
    @Override
    public List<Children> getAllChildren() {
        return childrenRepository.findAll();
    }

    /*
    * Deletes a children record by its ID
    * @Params childrenId The UUID of the children to delete
    * @Return boolean True if deletion was successful
    * @Throw IdNotFoundException if children not found
    */
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
