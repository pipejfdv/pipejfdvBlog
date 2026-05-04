package com.pipejfdv.Juegos.MCSJuegos.Controller;

import com.pipejfdv.Juegos.MCSJuegos.Controller.ResponsesHTTP.OK.ResponseOk;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.GamesDTO;
import com.pipejfdv.Juegos.MCSJuegos.Service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GamesController {
    private final GameService gameService;

    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }

    // Create
    @PostMapping("/create/game")
    public ResponseEntity<ResponseOk<GamesDTO>> createGame(@RequestBody Game game ) {
        return ResponseEntity.ok(new ResponseOk<>(
                "Game created successfully",
                gameService.createGame(game),
                HttpStatus.OK.value())
        );
    }

    // read game
    @GetMapping("/read/game/{id}")
    public ResponseEntity<ResponseOk<GamesDTO>> getGame(
            @PathVariable UUID id
    ){
        return ResponseEntity.ok(new ResponseOk<>(
                "Game found successfully",
                gameService.getGame(id),
                HttpStatus.OK.value()
        ));
    }

    // list of games
    @GetMapping("/listGames")
    public ResponseEntity<ResponseOk<List<GamesDTO>>> listGames() {
        return ResponseEntity.ok(new ResponseOk<>(
                "Games found successfully",
                gameService.listGames(),
                HttpStatus.OK.value()
        ));
    }
    //update name of game
    @PutMapping("/updateGame/{idGame}/{name}")
    public ResponseEntity<ResponseOk<GamesDTO>> updateGame(
            @PathVariable UUID idGame,
            @PathVariable String name
    ){
        return ResponseEntity.ok(new ResponseOk<>(
                "Game updated successfully",
                gameService.updateGame(idGame, name),
                HttpStatus.OK.value()
        ));
    }

    // deleted game
    @DeleteMapping("/deleted/{id}")
    public ResponseEntity<ResponseOk<String>> deleteGame(
            @PathVariable UUID id
    ){
        gameService.deleteGame(id);
        return ResponseEntity.ok(new ResponseOk<>(
                "Game deleted successfully",
                "confirmation " +id,
                HttpStatus.OK.value()
        ));
    }
}
