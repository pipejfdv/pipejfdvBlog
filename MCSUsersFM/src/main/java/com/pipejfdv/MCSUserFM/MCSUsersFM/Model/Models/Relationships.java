package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Table(name = "relationships")
public class Relationships {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private UUID id;
    private String relationship;

    //relation 1:N with GuardianChildren
    @OneToMany(targetEntity = GuardianChildren.class, fetch = FetchType.LAZY, mappedBy = "relationships")
    private List<GuardianChildren> guardianChildren;

    // Constructor for creating new relationships without guardian children
    public Relationships(String relationship) {
        this.id = UUID.randomUUID();
        this.relationship = relationship;
    }
}
