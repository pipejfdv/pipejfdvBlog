package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "children")
public class Children {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID id;
    @Column(nullable = false)
    @NotBlank(message = "name of child is void")
    private String names;
    @Column(nullable = false)
    @NotBlank(message = "lastname of child is void")
    private String lastName;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    // relation N:1 with DocumentType
    @ManyToOne(targetEntity = DocumentType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;
    @NotBlank(message = "document void")
    private Long document;

    // relation N:1 with TceClassification
    @ManyToOne(targetEntity = TceClassification.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "tce_classification")
    private TceClassification tceClassification;

    // relation 1:N with GuardianChildren
    @OneToMany(targetEntity = GuardianChildren.class, fetch = FetchType.LAZY, mappedBy = "children")
    private List<GuardianChildren> guardianChildren;

    public Children( String names, String lastName, LocalDate birthDate, DocumentType documentType, Long document, TceClassification tceClassification, List<GuardianChildren> guardianChildren) {
        this.id = UUID.randomUUID();
        this.names = names;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.documentType = documentType;
        this.document = document;
        this.tceClassification = tceClassification;
        this.guardianChildren = guardianChildren;
    }
}
