package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.DocumentTypeService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.DocumentTypeContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DocumentTypePresenter implements DocumentTypeContract.Presenter {
    private final DocumentTypeService documentTypeService;

    public DocumentTypePresenter(DocumentTypeService documentTypeService){
        this.documentTypeService = documentTypeService;
    }
    @Override
    public DocumentType readyToCreateDocumentType(DocumentType documentType) {
        return documentTypeService.createDocumentType(documentType);
    }

    @Override
    public DocumentType readyDocumentType(String nameDocumentType, UUID idDocumentType) {
        return documentTypeService.getDocumentType(nameDocumentType, idDocumentType);
    }

    @Override
    public List<DocumentType> readyDocumentTypeList() {
        return documentTypeService.DocumentTypeList();
    }

    @Override
    public DocumentType readyToUpdateDocumentType(DocumentType updateDocument, String nameDocumentType) {
        return documentTypeService.updateDocumentType(updateDocument, nameDocumentType);
    }

    @Override
    public void readyToDeleteDocumentType(String nameDocumentType, UUID idDocumentType) {
        documentTypeService.deleteDocumentType(nameDocumentType, idDocumentType);
    }
}
