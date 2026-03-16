package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "guardians")
public class Guardian {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(unique = true, nullable = false, updatable = false, columnDefinition = "CHAR(36)")
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
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // relation 1:N with GuardianChildren
    @OneToMany(targetEntity = GuardianChildren.class, fetch = FetchType.LAZY, mappedBy = "guardian", cascade = CascadeType.ALL)
    private List<GuardianChildren> guardianChildren;
}
