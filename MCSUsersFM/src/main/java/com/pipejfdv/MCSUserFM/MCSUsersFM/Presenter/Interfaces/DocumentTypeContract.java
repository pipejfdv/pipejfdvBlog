package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.ParametersNotReceived;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.DocumentTypeDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface DocumentTypeContract {
    interface View{
        ResponseEntity<ApiResponseOK<DocumentTypeDTO>> showCreateDocumentType (DocumentType documentType);
        ResponseEntity<ApiResponseOK<DocumentTypeDTO>> showDocumentType (String nameDocumentType, UUID idDocumentType);
        ResponseEntity<ApiResponseOK<List<DocumentTypeDTO>>> showAllDocumentType ();
        ResponseEntity<ApiResponseOK<DocumentTypeDTO>> showEditDocumentType (DocumentType updateDocument, String nameDocumentType);
        ResponseEntity<ApiResponseOK<DocumentTypeDTO>> showDeleteDocumentType (String nameDocumentType, UUID idDocumentType);
    }
    interface Presenter{
        DocumentType readyToCreateDocumentType (DocumentType documentType);
        DocumentType readyDocumentType (String nameDocumentType, UUID idDocumentType);
        List<DocumentType> readyDocumentTypeList ();
        DocumentType readyToUpdateDocumentType (DocumentType updateDocument, String nameDocumentType);
        void readyToDeleteDocumentType (String nameDocumentType, UUID idDocumentType);
    }
    interface Model{
        DocumentType createDocumentType (DocumentType documentType) throws DuplicateElementException, IdNotFoundException;
        DocumentType getDocumentType (String nameDocumentType, UUID idDocumentType) throws IdNotFoundException, NameNotFoundException;
        List<DocumentType> DocumentTypeList ();
        DocumentType updateDocumentType (DocumentType updateDocument, String nameDocumentType) throws NameNotFoundException, ParametersNotReceived;
        void deleteDocumentType (String nameDocumentType, UUID idDocumentType) throws IdNotFoundException, NameNotFoundException;
    }
}
