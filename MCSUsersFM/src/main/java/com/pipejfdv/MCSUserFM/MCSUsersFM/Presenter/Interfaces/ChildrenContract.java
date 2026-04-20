package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.ChildrenAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.ChildrenPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ChildrenContract {
    interface View {
        ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> CreateChildren(Children children);
        ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> GetChildrenPublic(UUID childrenId);
        ResponseEntity<ApiResponseOK<ChildrenAdminDTO>> GetChildrenAdmin(UUID childrenId);
        ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> UpdateChildren(UUID childrenId, Children children);
        ResponseEntity<ApiResponseOK<List<ChildrenPublicDTO>>> GetAllChildren();
        ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> DeleteChildren(UUID childrenId);
    }
    interface Presenter {
        Children CreateChildren(Children children);
        Children getChildren(UUID childrenId);
        List<Children> ChildrenList();
        Children updateChildren(Children updateChildren, UUID childrenId);
        String DeleteChildren(UUID childrenId);
    }
    interface Model {
        Children createChildren(Children children) throws DuplicateElementException, IdNotFoundException;
        Children getChildren(UUID childrenId) throws IdNotFoundException;
        Children updateChildren(UUID childrenId, Children children) throws IdNotFoundException;
        List<Children> getAllChildren();
        boolean deleteChildren(UUID childrenId) throws IdNotFoundException;
    }
}
