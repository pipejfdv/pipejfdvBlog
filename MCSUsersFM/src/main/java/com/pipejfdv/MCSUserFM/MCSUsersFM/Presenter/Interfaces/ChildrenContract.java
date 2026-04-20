package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.ChildrenDTO;

import java.util.List;
import java.util.UUID;

public interface ChildrenContract {
    interface View {
        /*ResponseEntity<ApiResponseOK<ChildrenDTO>> showCreateChildren(Children children);
        ResponseEntity<ApiResponseOK<ChildrenDTO>> showGetChildren(UUID childrenId);
        ResponseEntity<ApiResponseOK<ChildrenDTO>> showUpdateChildren(UUID childrenId, Children children);
        ResponseEntity<ApiResponseOK<List<ChildrenDTO>>> showGetAllChildren();
        ResponseEntity<ApiResponseOK<ChildrenDTO>> showDeleteChildren(UUID childrenId);*/
    }
    interface Presenter {
        Children readyToCreateChildren(Children children);
        Children readyChildren(UUID childrenId);
        List<Children> readyChildrenList();
        Children readyToUpdateChildren(Children updateChildren, UUID childrenId);
        String readyToDeleteChildren(UUID childrenId);
    }
    interface Model {
        Children createChildren(Children children) throws DuplicateElementException, IdNotFoundException;
        Children getChildren(UUID childrenId) throws IdNotFoundException;
        Children updateChildren(UUID childrenId, Children children) throws IdNotFoundException;
        List<Children> getAllChildren();
        boolean deleteChildren(UUID childrenId) throws IdNotFoundException;
    }
}
