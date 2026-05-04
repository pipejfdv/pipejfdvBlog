package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.Exceptions.ExistElement;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.GamesDTO;
import com.pipejfdv.Juegos.MCSJuegos.Repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // Create
    public GamesDTO createGame(Game game) {
        if (gameRepository.findByName(game.getName()).isPresent()) {
            throw new ExistElement(game.getName());
        }
        Game createGame = gameRepository.save(game);
        return new GamesDTO(createGame.getId(),createGame.getName());
    }

    // Read
    public GamesDTO getGame(UUID id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new IdNotFound(id));
        return new GamesDTO(game.getId(), game.getName());
    }

    // List
    public List<GamesDTO> listGames() {
        return gameRepository.findAll().stream()
                .map(game -> new GamesDTO(
                        game.getId(),
                        game.getName()
                )).toList();
    }
    
    // Update
    public GamesDTO updateGame(UUID id, String nameGame) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new IdNotFound(id));
        game.setName(nameGame);
        gameRepository.save(game);
        return new GamesDTO(game.getId(), game.getName());
    }

    // Delete
    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }
}
