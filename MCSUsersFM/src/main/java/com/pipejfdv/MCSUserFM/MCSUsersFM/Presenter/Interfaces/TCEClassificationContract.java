package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.TceClassification;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface TCEClassificationContract {
    interface View {
        ResponseEntity<ApiResponseOK<TceClassification>> getTCEClassification(UUID id);
        ResponseEntity<ApiResponseOK<List<TceClassification>>> TCEClassifications();
        ResponseEntity<ApiResponseOK<TceClassification>> updateTCEClassification(UUID id, String tceClassification);
    }
    interface Presenter{
        TceClassification getTCEClassification(UUID id) throws IdNotFoundException;
        List<TceClassification> getTCEClassifications();
        TceClassification updateTCEClassification(UUID id, String tceClassification) throws IdNotFoundException;
    }
    interface Model{
        TceClassification getTCEClassification(UUID id) throws IdNotFoundException;
        List<TceClassification> getTCEClassifications();
        TceClassification updateTCEClassification(UUID id, String tceClassification) throws IdNotFoundException;
    }
}
