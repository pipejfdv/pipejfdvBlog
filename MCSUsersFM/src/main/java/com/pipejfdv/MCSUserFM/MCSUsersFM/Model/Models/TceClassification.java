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
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tce_clssification")
public class TceClassification {
    @Id
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)", updatable = false, nullable = false)
    private UUID id;
    private String classification;

    //relation 1:N with Children
    @OneToMany(targetEntity = Children.class, fetch = FetchType.LAZY, mappedBy = "tceClassification")
    private List<Children> children;
}
