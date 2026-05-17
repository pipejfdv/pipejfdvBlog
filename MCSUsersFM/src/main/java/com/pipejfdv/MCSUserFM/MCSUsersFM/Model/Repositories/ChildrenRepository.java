package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Children;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/*
* Repository for managing Children entity database operations
*/
@Repository
public interface ChildrenRepository extends JpaRepository<Children, UUID> {
    boolean existsByDocument(Long document);

    @Query("SELECT gc.children FROM GuardianChildren gc WHERE gc.guardian.id = :guardianId")
    /*
    * Retrieves all children associated with a specific guardian
    * @Param guardianId the guardian UUID
    * @Return list of children for the given guardian
    */
    List<Children> getChildrenByGuardianId(UUID guardianId);
}
