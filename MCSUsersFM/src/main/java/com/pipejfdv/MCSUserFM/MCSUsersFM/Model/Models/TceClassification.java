package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tce_clssification")
public class TceClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(length = 36, updatable = false, nullable = false)
    private UUID id;
    private String classification;

    //relation 1:N with Children
    @OneToMany(targetEntity = Children.class, fetch = FetchType.LAZY, mappedBy = "tceClassification")
    private List<Children> children;
}
