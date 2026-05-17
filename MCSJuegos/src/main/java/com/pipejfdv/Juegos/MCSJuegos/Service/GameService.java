package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.Exceptions.ExistElement;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.GamesDTO;
import com.pipejfdv.Juegos.MCSJuegos.Repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/*
* Service layer for game CRUD operations
* Handles business logic for creating, reading, listing, updating, and deleting games
*/
@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    /*
	* Creates a new game after checking for duplicate names
	* @Param game Game entity to create
	* @Return GamesDTO with created game data
	* @Throw ExistElement if a game with the same name already exists
	*/
    public GamesDTO createGame(Game game) {
        if (gameRepository.findByName(game.getName()).isPresent()) {
            throw new ExistElement(game.getName());
        }
        Game createGame = gameRepository.save(game);
        return new GamesDTO(createGame.getId(),createGame.getName());
    }

    /*
	* Gets a game by its ID
	* @Param id UUID of the game
	* @Return GamesDTO with game data
	* @Throw IdNotFound if game ID does not exist
	*/
    public GamesDTO getGame(UUID id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new IdNotFound(id));
        return new GamesDTO(game.getId(), game.getName());
    }

    /*
	* Lists all games in the database
	* @Return List of GamesDTO with all games
	*/
    public List<GamesDTO> listGames() {
        return gameRepository.findAll().stream()
                .map(game -> new GamesDTO(
                        game.getId(),
                        game.getName()
                )).toList();
    }
    
    /*
	* Updates the name of an existing game
	* @Param id UUID of the game to update
	* @Param nameGame new name for the game
	* @Return GamesDTO with updated game data
	* @Throw IdNotFound if game ID does not exist
	*/
    public GamesDTO updateGame(UUID id, String nameGame) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new IdNotFound(id));
        game.setName(nameGame);
        gameRepository.save(game);
        return new GamesDTO(game.getId(), game.getName());
    }

    /*
	* Deletes a game by ID
	* @Param id UUID of the game to delete
	*/
    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }
}
