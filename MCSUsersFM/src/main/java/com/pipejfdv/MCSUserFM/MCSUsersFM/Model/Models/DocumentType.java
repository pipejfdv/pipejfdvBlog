package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

/*
* Represents the type of identification document (e.g. ID card, passport)
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document_type")
public class DocumentType {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)", nullable = false, unique = true)
    private UUID id;
    @Column(nullable = false)
    private String type;

    //relation 1:N with Guardian
    @OneToMany(targetEntity = Guardian.class, fetch = FetchType.LAZY, mappedBy = "documentType")
    private List<Guardian> guardians;

    //relation 1:N with Children
    @OneToMany(targetEntity = Children.class, fetch = FetchType.LAZY, mappedBy = "documentType")
    private List<Children> children;

    /*
    * Creates a new document type with a generated UUID
    * @Param type the document type name
    */
    public DocumentType(String type) {
        this.id = UUID.randomUUID();
        this.type = type;
    }
}
