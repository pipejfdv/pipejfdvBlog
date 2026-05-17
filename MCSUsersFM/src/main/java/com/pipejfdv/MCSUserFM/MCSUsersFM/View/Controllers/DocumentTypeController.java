package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.Public.DocumentTypeDTO;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Class.DocumentTypePresenter;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Presenter.Interfaces.DocumentTypeContract;
import com.pipejfdv.MCSUserFM.MCSUsersFM.View.ResponsesHTTP.OK.ApiResponseOK;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/funnyMind")
public class DocumentTypeController implements DocumentTypeContract.View {
    private final DocumentTypePresenter documentTypePresenter;

    public DocumentTypeController (DocumentTypePresenter documentTypePresenter){
        this.documentTypePresenter = documentTypePresenter;
    }

    /*
    * Creates a new document type
    * @Params documentType DocumentType object with type name
    * @Return ResponseEntity with created document type DTO
    */
    @Override
    @PostMapping("/DT/create")
    public ResponseEntity<ApiResponseOK<DocumentTypeDTO>> showCreateDocumentType(@RequestBody DocumentType documentType) {
        DocumentType newDocument = documentTypePresenter.readyToCreateDocumentType(documentType);
        DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO(newDocument.getId(), newDocument.getType());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Document create - successful",
                documentTypeDTO,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Gets a document type by name or ID
    * @Params nameDocumentType Name of the document type (optional)
    * @Params idDocumentType UUID of the document type (optional)
    * @Return ResponseEntity with document type DTO
    */
    @Override
    @GetMapping("/DT/Document")
    public ResponseEntity<ApiResponseOK<DocumentTypeDTO>> showDocumentType(
            @RequestParam(value = "name", required = false) String nameDocumentType,
            @RequestParam(value = "id", required = false) UUID idDocumentType) {
        DocumentType document = documentTypePresenter.readyDocumentType(nameDocumentType, idDocumentType);
        DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO(document.getId(), document.getType());
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Document",
                documentTypeDTO,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Returns a list of all document types
    * @Return ResponseEntity with list of document type DTOs
    */
    @Override
    @GetMapping("/DT/List")
    public ResponseEntity<ApiResponseOK<List<DocumentTypeDTO>>> showAllDocumentType() {
        List<DocumentTypeDTO> listDocumentDTO = documentTypePresenter.readyDocumentTypeList().stream()
                .map(document -> new DocumentTypeDTO(document.getId(), document.getType()))
                .toList();
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Type documents",
                listDocumentDTO,
                HttpStatus.OK.value()
        ));
    }

    /*
    * Updates an existing document type name
    * @Params updateDocument DocumentType object with updated data
    * @Params nameDocumentType Current name of the document type
    * @Return ResponseEntity with updated document type DTO
    */
    @Override
    @PutMapping("/DT/update/{documentType}")
    public ResponseEntity<ApiResponseOK<DocumentTypeDTO>> showEditDocumentType(
            @RequestBody DocumentType updateDocument,
            @PathVariable(value = "documentType") String nameDocumentType) {
        DocumentType document = documentTypePresenter.readyToUpdateDocumentType(updateDocument, nameDocumentType);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "updated document",
                new DocumentTypeDTO(document.getId(), document.getType()),
                HttpStatus.OK.value()
                )
        );
    }

    /*
    * Deletes a document type by name or ID
    * @Params nameDocumentType Name of the document type (optional)
    * @Params idDocumentType UUID of the document type (optional)
    * @Return ResponseEntity with deleted document type DTO
    */
    @Override
    @DeleteMapping("/DT/delete")
    public ResponseEntity<ApiResponseOK<DocumentTypeDTO>> showDeleteDocumentType(
            @RequestParam(value = "name", required = false) String nameDocumentType,
            @RequestParam(value = "id", required = false) UUID idDocumentType) {
        DocumentType documentType = documentTypePresenter.readyDocumentType(nameDocumentType, idDocumentType);
        DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO(documentType.getId(), documentType.getType());
        documentTypePresenter.readyToDeleteDocumentType(nameDocumentType, idDocumentType);
        return ResponseEntity.ok(new ApiResponseOK<>(
                "Document deleted",
                documentTypeDTO,
                HttpStatus.OK.value()
        ));
    }
}
