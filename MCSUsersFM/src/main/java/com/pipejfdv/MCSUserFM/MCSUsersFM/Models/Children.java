package com.pipejfdv.MCSUserFM.MCSUsersFM.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private UUID id;
    private String names;
    private String lastName;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate birthDate;

    // relation N:1 with DocumentType
    @ManyToOne(targetEntity = DocumentType.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;
    private String document;

    // relation N:1 with TceClassification
    @ManyToOne(targetEntity = TceClassification.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "tce_classification")
    private TceClassification tceClassification;

    // relation 1:N with GuardianChildren
    @OneToMany(targetEntity = GuardianChildren.class, fetch = FetchType.LAZY, mappedBy = "children")
    private List<GuardianChildren> guardianChildren;
}
