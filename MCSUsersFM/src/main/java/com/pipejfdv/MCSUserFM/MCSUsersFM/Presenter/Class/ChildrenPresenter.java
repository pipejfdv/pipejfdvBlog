package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.ChildrenAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ChildrenPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.ChildrenService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.ChildrenContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ChildrenPresenter implements ChildrenContract.Presenter {
    private final ChildrenService childrenService;

    public ChildrenPresenter(ChildrenService childrenService) {
        this.childrenService = childrenService;
    }

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
