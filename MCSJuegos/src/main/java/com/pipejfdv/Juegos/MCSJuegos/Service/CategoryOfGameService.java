package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.CategoryOfGame;
import com.pipejfdv.Juegos.MCSJuegos.Repositories.CategoryOfGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryOfGameService {
    private final CategoryOfGameRepository categoryOfGameRepository;

    public CategoryOfGameService(CategoryOfGameRepository categoryOfGameRepository) {
        this.categoryOfGameRepository = categoryOfGameRepository;
    }
    // Create
    public CategoryOfGame createCategoryOfGame(CategoryOfGame categoryOfGame) {
        return categoryOfGameRepository.save(categoryOfGame);
    }

    // read
    public CategoryOfGame getCategoryOfGame(UUID id) {
        return categoryOfGameRepository.findById(id).orElseThrow(() -> new IdNotFound(id));
    }

    // list
    public List<CategoryOfGame> listCategoryOfGames() {
        return categoryOfGameRepository.findAll();
    }

    // update
    public CategoryOfGame updateCategoryOfGame(UUID id, String nameCategoryOfGame, String descriptionCategoryOfGame) {
        CategoryOfGame category = getCategoryOfGame(id);
        category.setName(nameCategoryOfGame);
        category.setDescription(descriptionCategoryOfGame);
        return categoryOfGameRepository.save(category);
    }

    // delete
    public void deleteCategoryOfGame(UUID id) {
        categoryOfGameRepository.deleteById(id);
    }
}
