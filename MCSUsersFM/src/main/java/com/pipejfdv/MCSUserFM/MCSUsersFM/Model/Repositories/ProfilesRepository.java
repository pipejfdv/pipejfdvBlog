package com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Repositories;

import com.pipejfdv.MCSUserFM.MCSUsersFM.Model.Models.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProfilesRepository extends JpaRepository<Profile, UUID> {
    @Query("""
            SELECT p FROM Profile p
            JOIN Children c ON p.children.id = c.id
            JOIN GuardianChildren gc ON gc.children.id = c.id
            WHERE gc.guardian.id = :guardianId""")
    List<Profile> listProfilesPublic(UUID guardianId);

    boolean existsByNameProfile(String profiles);
}
