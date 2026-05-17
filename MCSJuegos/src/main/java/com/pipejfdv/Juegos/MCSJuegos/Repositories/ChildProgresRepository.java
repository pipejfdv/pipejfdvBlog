package com.pipejfdv.Juegos.MCSJuegos.Repositories;

import com.pipejfdv.Juegos.MCSJuegos.Model.Models.ChildProgres;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/*
* Repository for ChildProgres entity CRUD operations
* Includes custom query for daily attempt resets
*/
@Repository
public interface ChildProgresRepository extends JpaRepository<ChildProgres, UUID> {
    /*
	* Finds child progress by child ID
	* @Param childrenId UUID of the child
	* @Return Optional containing ChildProgres if found
	*/
    Optional<ChildProgres> findByChildrenId(UUID childrenId);

    /*
	* Finds child progress by child ID and category of game ID
	* @Param childrenId UUID of the child
	* @Param categoryOfGameId UUID of the game category
	* @Return Optional containing ChildProgres if found
	*/
    Optional<ChildProgres> findByChildrenIdAndCategoryOfGameId(UUID childrenId, UUID categoryOfGameId);

    /*
	* Resets daily attempt counter to zero for all children
	* Runs as a bulk update query
	*/
    @Modifying
    @Query("UPDATE ChildProgres c SET c.attemptsDaily = 0")
    void resetAttemptsDaily();
}
