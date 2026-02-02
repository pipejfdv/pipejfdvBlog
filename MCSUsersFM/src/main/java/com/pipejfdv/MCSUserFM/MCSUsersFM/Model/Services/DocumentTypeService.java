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

@Service
public class DocumentTypeService implements DocumentTypeContract.Model {
    private final DocumentTypeRepository documentTypeRepository;

    public DocumentTypeService(DocumentTypeRepository documentTypeRepository){
        this.documentTypeRepository = documentTypeRepository;
    }

    /*CRUD*/
    /*CREATE*/
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
    @Override
    public DocumentType getDocumentType(String nameDocumentType, UUID idDocumentType) throws IdNotFoundException, NameNotFoundException {
        return validationForNameOrId(nameDocumentType, idDocumentType);
    }
    /*READ LIST DOCUMENTS*/
    @Override
    public List<DocumentType> DocumentTypeList() {
        return documentTypeRepository.findAll();
    }
    /*UPDATE*/
    @Override
    public DocumentType updateDocumentType(DocumentType updateDocument, String nameDocumentType) throws NameNotFoundException, ParametersNotReceived {
        DocumentType oldDocumentType = validationForNameOrId(nameDocumentType, null);
        oldDocumentType.setType(updateDocument.getType());
        documentTypeRepository.save(oldDocumentType);
        return oldDocumentType;
    }
    /*DELETE*/
    @Override
    public void deleteDocumentType(String nameDocumentType, UUID idDocumentType) throws IdNotFoundException, NameNotFoundException {
        documentTypeRepository.delete(
                validationForNameOrId(nameDocumentType, idDocumentType)
        );
    }
    /*Was created for simplify the search for name or id*/
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
