package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.WrongParametersException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.RelationshipsDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface RelationshipsContract {
    interface View{
        ResponseEntity<ApiResponseOK<Relationships>> createRelationships(String relationships) throws DuplicateElementException;
        ResponseEntity<ApiResponseOK<Relationships>> getRelationships(UUID id, String relation) throws IdNotFoundException, NameNotFoundException;
        ResponseEntity<ApiResponseOK<List<Relationships>>> listRelationshipsAdmin();
        ResponseEntity<ApiResponseOK<List<RelationshipsDTO>>> listRelationshipsPublic();
        ResponseEntity<ApiResponseOK<Relationships>> updateRelationships(UUID id, String relation) throws IdNotFoundException;
    }
    interface Presenter{
        //Relationships
        Relationships createRelationships(String relationships) throws DuplicateElementException;
        Relationships getRelationships (UUID id, String relation) throws IdNotFoundException, NameNotFoundException;
        List<RelationshipsDTO> listRelationshipsPublic ();
        List<Relationships> listRelationshipsAdmin ();
        Relationships updateRelationships (UUID id, String relation)throws IdNotFoundException, NameNotFoundException;
    }
    interface Model{
        //CRUD
        Relationships createRelationships(String relationships) throws DuplicateElementException;
        Relationships getRelationships (UUID id, String relation) throws IdNotFoundException, NameNotFoundException;
        List<Relationships> listRelationships ();
        Relationships updateRelationships (UUID id, String relation)throws IdNotFoundException, NameNotFoundException;
    }
}
