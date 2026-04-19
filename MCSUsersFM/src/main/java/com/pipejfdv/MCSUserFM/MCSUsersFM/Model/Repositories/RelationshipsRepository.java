package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface RelationshipsRepository extends JpaRepository<Relationships, UUID> {
    Relationships findByRelationship (String relation);

    boolean existsByRelationship(@NotBlank(message = "the name of realtionship is void") String relationship);
}
