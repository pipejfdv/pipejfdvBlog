package com.pipejfdv.Juegos.MCSJuegos.Controller;

import com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.OK.ResponseOk;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.GameStat;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.GameStatsDTO;
import com.pipejfdv.Juegos.MCSJuegos.Service.GameStatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/*
* REST controller for managing game statistics per child
* Provides endpoints for creating, reading, and updating game stats
*/
@RestController
@RequestMapping("/games")
public class GameStatsController {
    private final GameStatService gameStatService;

    public GameStatsController(GameStatService gameStatService) {
        this.gameStatService = gameStatService;
    }

    /*
	* Creates a new game statistics record for a child
	* @Param gameStat GameStat object with score details
	* @Return ResponseEntity with success message and GameStatsDTO
	*/
    @PostMapping("/createGameStat")
    public ResponseEntity<ResponseOk<GameStatsDTO>> createGameStats(
            @RequestBody GameStat gameStat
    ) {
        return ResponseEntity.ok(new ResponseOk<>(
                "Create completed",
                gameStatService.createGameStats(gameStat),
                HttpStatus.OK.value())
        );
    }

    /*
	* Gets game statistics for a specific child and game
	* @Param childrenId UUID of the child
	* @Param gameId UUID of the game
	* @Return ResponseEntity with game stats data
	*/
    @GetMapping("/read/{childrenId}/{gameId}")
    public ResponseEntity<ResponseOk<GameStatsDTO>> getGameStat(
            @PathVariable UUID childrenId,
            @PathVariable UUID gameId
    ){
        return ResponseEntity.ok(new ResponseOk<>(
                "Read completed",
                gameStatService.getGameStats(childrenId, gameId),
                HttpStatus.OK.value()
        ));
    }

    /*
	* Updates game statistics for a specific child and game
	* @Param childrenId UUID of the child
	* @Param gameId UUID of the game
	* @Param gameStat GameStat object with updated score data
	* @Return ResponseEntity with updated GameStatsDTO
	*/
    @PutMapping("/update/{childrenId}/{gameId}")
    public ResponseEntity<ResponseOk<GameStatsDTO>> updateGameStat(
            @PathVariable UUID childrenId,
            @PathVariable UUID gameId,
            @RequestBody GameStat gameStat
    ){
        return ResponseEntity.ok(new ResponseOk<>(
                "Update completed",
                gameStatService.updateGameStats(childrenId, gameId, gameStat),
                HttpStatus.OK.value()
        ));
    }
}
