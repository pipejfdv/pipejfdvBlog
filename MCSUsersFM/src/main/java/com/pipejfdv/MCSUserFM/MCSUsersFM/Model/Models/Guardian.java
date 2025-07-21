package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "guardians")
public class Guardian {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, updatable = false, length = 36)
    private UUID id;
    @Column(nullable = false)
    @NotBlank(message = "name void")
    private String name;
    @Column(nullable = false)
    @NotBlank(message = "lastname void")
    private String lastname;
    private String phone;
    @Column(columnDefinition = "TEXT")
    private String biography;

    // relation N:1 with Document type
    @ManyToOne(targetEntity = DocumentType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;
    private String document;

    // relation 1:1 with User
    @OneToOne(mappedBy = "guardian")
    private User user;

    // relation 1:N with GuardianChildren
    @OneToMany(targetEntity = GuardianChildren.class, fetch = FetchType.LAZY, mappedBy = "guardian")
    private List<GuardianChildren> guardianChildren;
}
