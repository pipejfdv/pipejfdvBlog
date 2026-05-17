package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.DocumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/*
* Repository for managing DocumentType entity database operations
*/
@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, UUID> {
    boolean existsByType(String type);

    /*
    * Finds a document type by its type name
    * @Param type the document type name to search for
    * @Return an Optional containing the DocumentType if found, or empty if not
    */
    Optional<DocumentType> findDocumentTypeByType(String type);
}
