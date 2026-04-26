package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "guardian_children")
public class GuardianChildren {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)")
    private UUID id;

    //relation N:1 with Guardian
    @ManyToOne (targetEntity = Guardian.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "guardian_id")
    private Guardian guardian;

    //relation N:1 with Children
    @ManyToOne(targetEntity = Children.class, fetch = FetchType.EAGER)
    private Children children;

    //relation N:1 with Relationships
    @ManyToOne(targetEntity = Relationships.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "relationships")
    private Relationships relationships;

    public GuardianChildren(Guardian guardian, Children children, Relationships relationships) {
        this.id = UUID.randomUUID();
        this.guardian = guardian;
        this.children = children;
        this.relationships = relationships;
    }
}
