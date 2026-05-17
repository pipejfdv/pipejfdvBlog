package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Relationships;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
/*
* Repository for managing Relationships entity database operations
*/
@Repository
public interface RelationshipsRepository extends JpaRepository<Relationships, UUID> {
    /*
    * Finds a relationship by its name
    * @Param relation the relationship name to search for
    * @Return the Relationships entity if found, or null
    */
    Relationships findByRelationship (String relation);

    boolean existsByRelationship(@NotBlank(message = "the name of realtionship is void") String relationship);
}
