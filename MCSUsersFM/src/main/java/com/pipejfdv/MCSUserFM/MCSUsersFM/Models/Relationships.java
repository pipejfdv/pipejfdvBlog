package com.pipejfdv.MCSUserFM.MCSUsersFM.Models;

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
@Table(name = "relationships")
public class Relationships {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String relationship;

    //relation 1:N with GuardianChildren
    @OneToMany(targetEntity = GuardianChildren.class, fetch = FetchType.LAZY, mappedBy = "relationships")
    private List<GuardianChildren> guardianChildren;
}
