package com.pipejfdv.Juegos.MCSJuegos.Repositories;

import com.pipejfdv.Juegos.MCSJuegos.Model.Models.CategoryOfGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryOfGameRepository extends JpaRepository<CategoryOfGame, UUID> {
    CategoryOfGame findByName(String name);
}
