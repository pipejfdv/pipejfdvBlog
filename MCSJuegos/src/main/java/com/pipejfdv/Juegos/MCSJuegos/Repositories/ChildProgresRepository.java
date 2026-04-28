package com.pipejfdv.Juegos.MCSJuegos.Repositories;

import com.pipejfdv.Juegos.MCSJuegos.Model.Models.ChildProgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ChildProgresRepository extends JpaRepository<ChildProgres, UUID> {
    Optional<ChildProgres> findByChildrenId(UUID childrenId);

    Optional<ChildProgres> findByChildrenIdAndCategoryOfGameId(UUID childrenId, UUID categoryOfGameId);

    // reset attempts
    @Modifying
    @Query("UPDATE ChildProgres c SET c.attemptsDaily = 0")
    void resetAttemptsDaily();
}
