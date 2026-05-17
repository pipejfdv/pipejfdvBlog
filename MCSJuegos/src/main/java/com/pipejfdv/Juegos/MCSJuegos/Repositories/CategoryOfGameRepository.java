package com.pipejfdv.Juegos.MCSJuegos.Repositories;

import com.pipejfdv.Juegos.MCSJuegos.Model.Models.CategoryOfGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/*
* Repository for CategoryOfGame entity CRUD operations
*/
@Repository
public interface CategoryOfGameRepository extends JpaRepository<CategoryOfGame, UUID> {
    /*
	* Finds a category by its name
	* @Param name the name to search for
	* @Return CategoryOfGame if found, null otherwise
	*/
    CategoryOfGame findByName(String name);
}
