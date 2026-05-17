package com.pipejfdv.Juegos.MCSJuegos.Controller;

import com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.OK.ResponseOk;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.CategoryOfGame;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.CategoryOfGameDTO;
import com.pipejfdv.Juegos.MCSJuegos.Service.CategoryOfGameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/*
* REST controller for managing game categories
* Provides endpoints for creating, reading, listing, and updating categories
*/
@RestController
@RequestMapping("/games")
public class CategoryOfGamesController {
    private final CategoryOfGameService categoryOfGameService;

    public CategoryOfGamesController(CategoryOfGameService categoryOfGamesService) {
        this.categoryOfGameService = categoryOfGamesService;
    }

    /*
	* Creates a new game category
	* @Param categoryOfGame CategoryOfGame object with category details
	* @Return ResponseEntity with success message and CategoryOfGameDTO
	*/
    @PostMapping("/create/category")
    public ResponseEntity<ResponseOk<CategoryOfGameDTO>> createCategoryOfGame(@RequestBody CategoryOfGame categoryOfGame) {
        return ResponseEntity.ok(new ResponseOk<>(
                "Category created successfully",
                categoryOfGameService.createCategoryOfGame(categoryOfGame),
                HttpStatus.OK.value())
        );
    }

    /*
	* Gets a single category by ID
	* @Param id UUID of the category
	* @Return ResponseEntity with category data
	*/
    @GetMapping("/read/category/{id}")
    public ResponseEntity<ResponseOk<CategoryOfGameDTO>> getCategoryOfGame(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(new ResponseOk<>(
                "Category found successfully",
                categoryOfGameService.getCategoryOfGame(id),
                HttpStatus.OK.value()
        ));
    }

    /*
	* Lists all game categories
	* @Return ResponseEntity with list of CategoryOfGameDTO
	*/
    @GetMapping("/listCategories")
    public ResponseEntity<ResponseOk<List<CategoryOfGameDTO>>> listCategoryOfGames() {
        return ResponseEntity.ok(new ResponseOk<>(
                "Categories found successfully",
                categoryOfGameService.listCategoryOfGames(),
                HttpStatus.OK.value()
        ));
    }

    /*
	* Updates an existing game category
	* @Param id UUID of the category to update
	* @Param categoryOfGame CategoryOfGame object with updated details
	* @Return ResponseEntity with updated CategoryOfGameDTO
	*/
    @PutMapping("/update/category/{id}")
    public ResponseEntity<ResponseOk<CategoryOfGameDTO>> updateCategoryOfGame(
            @PathVariable UUID id,
            @RequestBody CategoryOfGame categoryOfGame
    ) {
        return ResponseEntity.ok(new ResponseOk<>(
                "Category updated successfully",
                categoryOfGameService.updateCategoryOfGame(id, categoryOfGame),
                HttpStatus.OK.value()
        ));
    }

}
