package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.ChildrenAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ChildrenPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.ChildrenService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.ChildrenContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/*
* Presenter for Children operations
* Acts as intermediary between controller and ChildrenService
* Maps entities to DTOs for public and admin views
*/
@Component
public class ChildrenPresenter implements ChildrenContract.Presenter {
    private final ChildrenService childrenService;

    public ChildrenPresenter(ChildrenService childrenService) {
        this.childrenService = childrenService;
    }

    /*
    * Creates a new children and returns a public DTO
    * @Params children The children entity to create
    * @Return ChildrenPublicDTO The public DTO of the created children
    */
    @Override
    public ChildrenPublicDTO CreateChildren(Children children) {
        Children newChildren= childrenService.createChildren(children);
        return new ChildrenPublicDTO(
                newChildren.getId(),
                newChildren.getNames(),
                newChildren.getLastName(),
                newChildren.getBirthDate(),
                newChildren.getTceClassification().getClassification()
        );
    }

    /*
    * Retrieves a children by ID and returns a public DTO
    * @Params childrenId The UUID of the children
    * @Return ChildrenPublicDTO The public DTO of the children
    */
    @Override
    public ChildrenPublicDTO getChildrenPublic(UUID childrenId) {
        Children children = childrenService.getChildren(childrenId);
        return new ChildrenPublicDTO(
                children.getId(),
                children.getNames(),
                children.getLastName(),
                children.getBirthDate(),
                children.getTceClassification().getClassification()
        );
    }

    /*
    * Retrieves a children by ID and returns an admin DTO with document info
    * @Params childrenId The UUID of the children
    * @Return ChildrenAdminDTO The admin DTO of the children
    */
    @Override
    public ChildrenAdminDTO getChildrenAdmin(UUID childrenId) {
        Children children = childrenService.getChildren(childrenId);
        return new ChildrenAdminDTO(
                children.getId(),
                children.getNames(),
                children.getLastName(),
                children.getBirthDate(),
                children.getTceClassification().getClassification(),
                children.getDocumentType().getType(),
                children.getDocument()
        );
    }

    /*
    * Retrieves all children as admin DTOs
    * @Return List of admin DTOs for all children
    */
    @Override
    public List<ChildrenAdminDTO> childrenList() {
        return childrenService.getAllChildren()
                .stream()
                .map(c -> new ChildrenAdminDTO(
                        c.getId(),
                        c.getNames(),
                        c.getLastName(),
                        c.getBirthDate(),
                        c.getTceClassification().getClassification(),
                        c.getDocumentType().getType(),
                        c.getDocument()
                ))
                .toList();
    }

    /*
    * Retrieves all children for a guardian as public DTOs
    * @Params guardianId The UUID of the guardian
    * @Return List of public DTOs for the guardian's children
    */
    @Override
    public List<ChildrenPublicDTO> getChildrenByGuardian(UUID guardianId) {
        return childrenService.getChildrenByGuardian(guardianId)
                .stream()
                .map(children -> new ChildrenPublicDTO(
                        children.getId(),
                        children.getNames(),
                        children.getLastName(),
                        children.getBirthDate(),
                        children.getTceClassification().getClassification()
                )).toList();
    }

    /*
    * Updates a children and returns a public DTO
    * @Params updateChildren The children entity with updated data
    * @Params childrenId The UUID of the children to update
    * @Return ChildrenPublicDTO The public DTO of the updated children
    */
    @Override
    public ChildrenPublicDTO updateChildren(Children updateChildren, UUID childrenId) {
        Children children = childrenService.updateChildren(childrenId, updateChildren);
        return new ChildrenPublicDTO(
                children.getId(),
                children.getNames(),
                children.getLastName(),
                children.getBirthDate(),
                children.getTceClassification().getClassification()
        );
    }

    /*
    * Deletes a children by ID and returns a confirmation message
    * @Params childrenId The UUID of the children to delete
    * @Return String Success or error message
    */
    @Override
    public String deleteChildren(UUID childrenId) {
        boolean confirmation = childrenService.deleteChildren(childrenId);
        if(confirmation) {
            return "Children deleted successfully";
        }
        else {
            return "Error";
        }
    }
}
