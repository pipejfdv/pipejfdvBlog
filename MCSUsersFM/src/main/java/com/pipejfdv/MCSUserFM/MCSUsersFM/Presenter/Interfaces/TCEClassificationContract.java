package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.TceClassificationDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

/*
* Contract defining the MVP interfaces for TCE Classification operations
*/
public interface TCEClassificationContract {
    interface View {
        ResponseEntity<ApiResponseOK<TceClassification>> getTCEClassification(UUID id);
        ResponseEntity<ApiResponseOK<List<TceClassificationDTO>>> TCEClassifications();
        ResponseEntity<ApiResponseOK<TceClassification>> updateTCEClassification(UUID id, String tceClassification);
        //update TCEClassification by children
        ResponseEntity<ApiResponseOK<Boolean>> updateTCEClassificationByChildren(UUID idChildren, UUID idTceClassification);
    }
    interface Presenter{
        TceClassification getTCEClassification(UUID id) throws IdNotFoundException;
        List<TceClassificationDTO> getTCEClassifications();
        TceClassification updateTCEClassification(UUID id, String tceClassification) throws IdNotFoundException;
        //update TCEClassification by children
        boolean updateTCEClassificationByChildren(UUID idChildren, UUID idTceClassification) throws IdNotFoundException;
    }
    interface Model{
        TceClassification getTCEClassification(UUID id) throws IdNotFoundException;
        List<TceClassification> getTCEClassifications();
        TceClassification updateTCEClassification(UUID id, String tceClassification) throws IdNotFoundException;
        //update TCEClassification by children
        boolean updateTCEClassificationByChildren(UUID idChildren, UUID idTceClassification) throws IdNotFoundException;
    }
}
