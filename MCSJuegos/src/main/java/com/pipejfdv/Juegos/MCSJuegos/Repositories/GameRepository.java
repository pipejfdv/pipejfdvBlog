package com.pipejfdv.Juegos.MCSJuegos.Repositories;

import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/*
* Repository for Game entity CRUD operations
*/
@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {
    /*
	* Finds a game by its name
	* @Param game the name to search for
	* @Return Optional containing the Game if found
	*/
    Optional<Game> findByName(String game);
}
