package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Services;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.DuplicateElementException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.IdNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.NameNotFoundException;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Exceptions.ParametersNotReceived;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories.DocumentTypeRepository;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.DocumentTypeContract;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
* Service for managing Document Type entities
* Handles CRUD operations and name/ID-based lookup
*/
@Service
public class DocumentTypeService implements DocumentTypeContract.Model {
    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository){
        this.documentTypeRepository = documentTypeRepository;
    }

    /*CRUD*/
    /*CREATE*/
    /*
    * Creates a new document type
    * @Params documentType The document type entity to create
    * @Return DocumentType The saved document type
    * @Throw DuplicateElementException if type already exists
    * @Throw IdNotFoundException if save fails
    */
    public DocumentType createDocumentType(DocumentType documentType)
            throws DuplicateElementException,
            IdNotFoundException {
        if(documentTypeRepository.existsByType(documentType.getType())){
            throw new DuplicateElementException(documentType.getType());
        }

        UUID id = UUID.randomUUID();
        documentType.setId(id);
        documentTypeRepository.save(documentType);
        return documentTypeRepository.findById(id).orElseThrow(
                () -> new IdNotFoundException(id)
        );
    }
    /*READ*/
    /*
    * Retrieves a document type by name or ID
    * @Params nameDocumentType The name of the document type (optional)
    * @Params idDocumentType The UUID of the document type (optional)
    * @Return DocumentType The found document type
    * @Throw IdNotFoundException if searched by ID and not found
    * @Throw NameNotFoundException if searched by name and not found
    */
    @Override
    public DocumentType getDocumentType(String nameDocumentType, UUID idDocumentType) throws IdNotFoundException, NameNotFoundException {
        return validationForNameOrId(nameDocumentType, idDocumentType);
    }
    /*READ LIST DOCUMENTS*/
    /*
    * Retrieves all document types
    * @Return List of all document types
    */
    @Override
    public List<DocumentType> DocumentTypeList() {
        return documentTypeRepository.findAll();
    }
    /*UPDATE*/
    /*
    * Updates a document type name by its current name
    * @Params updateDocument The document type with new data
    * @Params nameDocumentType The current name of the document type
    * @Return DocumentType The updated document type
    * @Throw NameNotFoundException if document type not found by name
    */
    @Override
    public DocumentType updateDocumentType(DocumentType updateDocument, String nameDocumentType) throws NameNotFoundException, ParametersNotReceived {
        DocumentType oldDocumentType = validationForNameOrId(nameDocumentType, null);
        oldDocumentType.setType(updateDocument.getType());
        documentTypeRepository.save(oldDocumentType);
        return oldDocumentType;
    }
    /*DELETE*/
    /*
    * Deletes a document type by name or ID
    * @Params nameDocumentType The name of the document type (optional)
    * @Params idDocumentType The UUID of the document type (optional)
    * @Throw IdNotFoundException if searched by ID and not found
    * @Throw NameNotFoundException if searched by name and not found
    */
    @Override
    public void deleteDocumentType(String nameDocumentType, UUID idDocumentType) throws IdNotFoundException, NameNotFoundException {
        documentTypeRepository.delete(
                validationForNameOrId(nameDocumentType, idDocumentType)
        );
    }
    /*
    * Validates and retrieves a document type by name or ID
    * @Params nameDocumentType The name to search by (optional)
    * @Params idDocumentType The UUID to search by (optional)
    * @Return DocumentType The found document type
    */
    public DocumentType validationForNameOrId (String nameDocumentType, UUID idDocumentType){
        if(nameDocumentType != null){
            return documentTypeRepository.findDocumentTypeByType(nameDocumentType)
                    .orElseThrow( ()->new NameNotFoundException(nameDocumentType));
        }
        else {
            return documentTypeRepository.findById(idDocumentType)
                    .orElseThrow(() -> new IdNotFoundException(idDocumentType));
        }
    }
}
