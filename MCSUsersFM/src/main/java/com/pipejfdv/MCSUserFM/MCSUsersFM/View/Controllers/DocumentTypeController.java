package com.pipejfdv.MCSUserFM.MCSUsersFM.View.Controllers;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.ModelsDTO.DocumentTypeDTO;
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
