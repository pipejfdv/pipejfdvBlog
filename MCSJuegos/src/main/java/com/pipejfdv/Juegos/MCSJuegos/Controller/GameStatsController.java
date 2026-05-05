package com.pipejfdv.Juegos.MCSJuegos.Controller;

import com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.OK.ResponseOk;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.GameStat;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.GameStatsDTO;
import com.pipejfdv.Juegos.MCSJuegos.Service.GameStatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameStatsController {
    private final GameStatService gameStatService;

    public GameStatsController(GameStatService gameStatService) {
        this.gameStatService = gameStatService;
    }

    //create
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

    //read
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

    //update
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
