package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.CategoryOfGame;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.CategoryOfGameDTO;
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
    public CategoryOfGameDTO createCategoryOfGame(CategoryOfGame categoryOfGame) {
        CategoryOfGame category = categoryOfGameRepository.save(categoryOfGame);
        return new CategoryOfGameDTO(category.getId(), category.getName(), category.getDescription());
    }

    // read
    public CategoryOfGameDTO getCategoryOfGame(UUID id) {
        CategoryOfGame category = categoryOfGameRepository.findById(id)
                .orElseThrow(() -> new IdNotFound(id));
        return new CategoryOfGameDTO(category.getId(), category.getName(), category.getDescription());
    }

    // list
    public List<CategoryOfGameDTO> listCategoryOfGames() {
        return categoryOfGameRepository.findAll().stream()
                .map(categoryOfGame -> new CategoryOfGameDTO(
                        categoryOfGame.getId(),
                        categoryOfGame.getName(),
                        categoryOfGame.getDescription()
                )).toList();
    }

    // update
    public CategoryOfGameDTO updateCategoryOfGame(UUID id, CategoryOfGame categoryOfGame) {
        CategoryOfGame category = categoryOfGameRepository.findById(id)
                .orElseThrow(()->new IdNotFound(id));
        category.setName(categoryOfGame.getName());
        category.setDescription(categoryOfGame.getDescription());
        categoryOfGameRepository.save(category);
        return new CategoryOfGameDTO(category.getId(), category.getName(), category.getDescription());
    }

}
