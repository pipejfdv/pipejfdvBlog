package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.CategoryOfGame;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.CategoryOfGameDTO;
import com.pipejfdv.Juegos.MCSJuegos.Repositories.CategoryOfGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
* Service layer for game category CRUD operations
* Handles business logic for categories of games
*/
@Service
public class CategoryOfGameService {
    private final CategoryOfGameRepository categoryOfGameRepository;

    public CategoryOfGameService(CategoryOfGameRepository categoryOfGameRepository) {
        this.categoryOfGameRepository = categoryOfGameRepository;
    }
    /*
	* Creates a new game category
	* @Param categoryOfGame CategoryOfGame entity to create
	* @Return CategoryOfGameDTO with created category data
	*/
    public CategoryOfGameDTO createCategoryOfGame(CategoryOfGame categoryOfGame) {
        CategoryOfGame category = categoryOfGameRepository.save(categoryOfGame);
        return new CategoryOfGameDTO(category.getId(), category.getName(), category.getDescription());
    }

    /*
	* Gets a category by its ID
	* @Param id UUID of the category
	* @Return CategoryOfGameDTO with category data
	* @Throw IdNotFound if category ID does not exist
	*/
    public CategoryOfGameDTO getCategoryOfGame(UUID id) {
        CategoryOfGame category = categoryOfGameRepository.findById(id)
                .orElseThrow(() -> new IdNotFound(id));
        return new CategoryOfGameDTO(category.getId(), category.getName(), category.getDescription());
    }

    /*
	* Lists all game categories
	* @Return List of CategoryOfGameDTO with all categories
	*/
    public List<CategoryOfGameDTO> listCategoryOfGames() {
        return categoryOfGameRepository.findAll().stream()
                .map(categoryOfGame -> new CategoryOfGameDTO(
                        categoryOfGame.getId(),
                        categoryOfGame.getName(),
                        categoryOfGame.getDescription()
                )).toList();
    }

    /*
	* Updates an existing game category
	* @Param id UUID of the category to update
	* @Param categoryOfGame CategoryOfGame object with updated values
	* @Return CategoryOfGameDTO with updated category data
	* @Throw IdNotFound if category ID does not exist
	*/
    public CategoryOfGameDTO updateCategoryOfGame(UUID id, CategoryOfGame categoryOfGame) {
        CategoryOfGame category = categoryOfGameRepository.findById(id)
                .orElseThrow(()->new IdNotFound(id));
        category.setName(categoryOfGame.getName());
        category.setDescription(categoryOfGame.getDescription());
        categoryOfGameRepository.save(category);
        return new CategoryOfGameDTO(category.getId(), category.getName(), category.getDescription());
    }

}
