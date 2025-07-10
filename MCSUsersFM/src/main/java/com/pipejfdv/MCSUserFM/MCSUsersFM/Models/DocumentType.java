package com.pipejfdv.MCSUserFM.MCSUsersFM.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document_type")
public class DocumentType {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String type;

    //relation 1:N with Guardian
    @OneToMany(targetEntity = Guardian.class, fetch = FetchType.LAZY, mappedBy = "documentType")
    private List<Guardian> guardians;

    //relation 1:N with Children
    @OneToMany(targetEntity = Children.class, fetch = FetchType.LAZY, mappedBy = "documentType")
    private List<Children> children;
}
