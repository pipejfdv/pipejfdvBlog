package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Admin.ChildrenAdminDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.ChildrenPublicDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.UUID;

public interface ChildrenContract {
    interface View {
        ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> CreateChildren(Children children, UUID document, UUID tceClassification);
        ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> GetChildrenPublic(String childrenId);
        ResponseEntity<ApiResponseOK<ChildrenAdminDTO>> GetChildrenAdmin(String childrenId);
        ResponseEntity<ApiResponseOK<ChildrenPublicDTO>> UpdateChildren(String childrenId, Children children, Authentication authentication);
        ResponseEntity<ApiResponseOK<List<ChildrenAdminDTO>>> GetAllChildren();
        ResponseEntity<ApiResponseOK<List<ChildrenPublicDTO>>> GetChildrenByGuardian(Authentication authentication);
        ResponseEntity<ApiResponseOK<String>> DeleteChildren(String childrenId, Authentication authentication);
    }
    interface Presenter {
        ChildrenPublicDTO CreateChildren(Children children);
        ChildrenPublicDTO getChildrenPublic(UUID childrenId);
        ChildrenAdminDTO getChildrenAdmin(UUID childrenId);
        List<ChildrenAdminDTO> childrenList();
        List<ChildrenPublicDTO> getChildrenByGuardian(UUID guardianId);
        ChildrenPublicDTO updateChildren(Children updateChildren, UUID childrenId);
        String deleteChildren(UUID childrenId);
    }
    interface Model {
        Children createChildren(Children children) throws DuplicateElementException;
        Children getChildren(UUID childrenId) throws IdNotFoundException;
        Children updateChildren(UUID childrenId, Children children) throws IdNotFoundException;
        List<Children> getChildrenByGuardian(UUID guardianId);
        List<Children> getAllChildren();
        boolean deleteChildren(UUID childrenId) throws IdNotFoundException;
    }
}
