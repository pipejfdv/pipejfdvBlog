package com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services.DocumentTypeService;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.DocumentTypeContract;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/*
* Presenter for Document Type operations
* Acts as intermediary between controller and DocumentTypeService
*/
@Component
public class DocumentTypePresenter implements DocumentTypeContract.Presenter {
    private final DocumentTypeService documentTypeService;

    public DocumentTypePresenter(DocumentTypeService documentTypeService){
        this.documentTypeService = documentTypeService;
    }
    /*
    * Creates a new document type
    * @Params documentType The document type entity to create
    * @Return DocumentType The created document type
    */
    @Override
    public DocumentType readyToCreateDocumentType(DocumentType documentType) {
        return documentTypeService.createDocumentType(documentType);
    }

    /*
    * Retrieves a document type by name or ID
    * @Params nameDocumentType The name of the document type (optional)
    * @Params idDocumentType The UUID of the document type (optional)
    * @Return DocumentType The found document type
    */
    @Override
    public DocumentType readyDocumentType(String nameDocumentType, UUID idDocumentType) {
        return documentTypeService.getDocumentType(nameDocumentType, idDocumentType);
    }

    /*
    * Retrieves all document types
    * @Return List of all document types
    */
    @Override
    public List<DocumentType> readyDocumentTypeList() {
        return documentTypeService.DocumentTypeList();
    }

    /*
    * Updates a document type by its current name
    * @Params updateDocument The document type with new data
    * @Params nameDocumentType The current name of the document type
    * @Return DocumentType The updated document type
    */
    @Override
    public DocumentType readyToUpdateDocumentType(DocumentType updateDocument, String nameDocumentType) {
        return documentTypeService.updateDocumentType(updateDocument, nameDocumentType);
    }

    /*
    * Deletes a document type by name or ID
    * @Params nameDocumentType The name of the document type (optional)
    * @Params idDocumentType The UUID of the document type (optional)
    */
    @Override
    public void readyToDeleteDocumentType(String nameDocumentType, UUID idDocumentType) {
        documentTypeService.deleteDocumentType(nameDocumentType, idDocumentType);
    }
}
