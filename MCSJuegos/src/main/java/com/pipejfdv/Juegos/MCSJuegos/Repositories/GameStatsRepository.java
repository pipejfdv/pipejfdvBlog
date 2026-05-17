package com.pipejfdv.Juegos.MCSJuegos.Repositories;

import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.GameStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/*
* Repository for GameStat entity CRUD operations
*/
@Repository
public interface GameStatsRepository extends JpaRepository<GameStat, UUID> {
    /*
	* Finds game statistics by child ID and game entity
	* @Param childrenId UUID of the child
	* @Param game Game entity
	* @Return Optional containing the GameStat if found
	*/
    Optional<GameStat> findByChildrenIdAndGame(UUID childrenId, Game game);
}
