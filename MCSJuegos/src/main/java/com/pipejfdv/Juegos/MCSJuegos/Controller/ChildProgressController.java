package com.pipejfdv.Juegos.MCSJuegos.Controller;

import com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.OK.ResponseOk;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.ProgressParameterPackage;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.ChildProgressDTO;
import com.pipejfdv.Juegos.MCSJuegos.Service.ChildProgresService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
* REST controller for managing child progress across game categories
* Handles creating or updating progress and fetching progress data
*/
@RestController
@RequestMapping("/games")
public class ChildProgressController {
    private final ChildProgresService childProgresService;

    public ChildProgressController(ChildProgresService childProgresService) {
        this.childProgresService = childProgresService;
    }

    /*
	* Creates or updates a child's progress in a game category
	* Checks if progress exists, then creates or updates accordingly
	* @Param parameterPackage ProgressParameterPackage with game results
	* @Return ResponseEntity with success message and ChildProgressDTO
	* @Throw IdNotFound if the child is not found
	*/
    @PutMapping("/progress")
    public ResponseEntity<ResponseOk<ChildProgressDTO>> chargeChildProgres(
            @RequestBody ProgressParameterPackage parameterPackage) throws IdNotFound {
        boolean confirmation = childProgresService.existsChildProgres(parameterPackage.getChildProgres().getChildrenId(),
                parameterPackage.getChildProgres().getCategoryOfGame().getId());
        if (confirmation){
            return ResponseEntity.ok(new ResponseOk<>(
                    "update success",
                    childProgresService.updateChildProgres(parameterPackage),
                    HttpStatus.OK.value()
            ));
        }
        else{
         return ResponseEntity.ok(new ResponseOk<>(
                 "create success",
                 childProgresService.createChildProgres(parameterPackage),
                 HttpStatus.OK.value()
         ));
        }
    }

    /*
	* Gets a child's progress in a specific game category
	* @Param childId UUID of the child
	* @Param categoryOfGameId UUID of the game category
	* @Return ResponseEntity with ChildProgressDTO
	*/
    @GetMapping("/progress/{childId}/{categoryOfGameId}")
    public ResponseEntity<ResponseOk<ChildProgressDTO>> getProgress(@PathVariable UUID childId, @PathVariable UUID categoryOfGameId){
        return ResponseEntity.ok(new ResponseOk<>(
                "get success",
                childProgresService.getChildProgres(childId, categoryOfGameId),
                HttpStatus.OK.value()
        ));
    }
}
