package com.pipejfdv.MCSUserFM.MCSUsersFM.Models;

import jakarta.persistence.*;
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
    private UUID id;
    private String name;
    private String lastname;
    private String phone;
    private String biography;

    // relation N:1 with Document type
    @ManyToOne(targetEntity = DocumentType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "document_type")
    private DocumentType documentType;
    private String document;

    // relation 1:1 with User
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private User user;

    // relation 1:N with GuardianChildren
    @OneToMany(targetEntity = GuardianChildren.class, fetch = FetchType.LAZY, mappedBy = "guardian")
    private List<GuardianChildren> guardianChildren;
}
