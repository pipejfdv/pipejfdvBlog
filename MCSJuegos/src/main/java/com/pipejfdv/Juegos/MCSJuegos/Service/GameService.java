package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
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
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    // Read
    public Game getGame(UUID id) {
        return gameRepository.findById(id).orElseThrow(() -> new IdNotFound(id));
    }

    // List
    public List<Game> listGames() {
        return gameRepository.findAll();
    }
    
    // Update
    public Game updateGame(UUID id, String nameGame) {
        Game game = getGame(id);
        game.setName(nameGame);
        return gameRepository.save(game);
    }

    // Delete
    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }
}
