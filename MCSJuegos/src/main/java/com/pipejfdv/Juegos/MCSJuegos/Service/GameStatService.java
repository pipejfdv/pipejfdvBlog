package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.ComunicatiosMCS.FunnyMindDB;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.GameStat;
import com.pipejfdv.Juegos.MCSJuegos.Repositories.GameRepository;
import com.pipejfdv.Juegos.MCSJuegos.Repositories.GameStatsRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameStatService {
    private final GameStatsRepository gameStatRepository;
    private final FunnyMindDB funnyMindDB;
    private final GameRepository gameRepository;

    public GameStatService(GameStatsRepository gameStatRepository,
                           FunnyMindDB funnyMindDB,
                           GameRepository gameRepository) {
        this.gameStatRepository = gameStatRepository;
        this.funnyMindDB = funnyMindDB;
        this.gameRepository = gameRepository;
    }
    //---------CRUD---------
    // Create
    public GameStat createGameStats(GameStat gameStats) {
        try {
            funnyMindDB.getChildren(gameStats.getChildrenId());
        } catch (Exception e) {
            throw new IdNotFound(gameStats.getChildrenId());
        }
        return gameStatRepository.save(gameStats);
    }

    // Read - it's just for game
    public GameStat getGameStats(UUID childrenId, UUID gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IdNotFound(gameId));
        return gameStatRepository.findByChildrenIdAndGame(childrenId, game)
                .orElseThrow(() -> new IdNotFound(childrenId));
    }

    // update
    public GameStat updateGameStats(UUID childrenId, UUID gameId, GameStat gameStats) {
        try {
            funnyMindDB.getChildren(childrenId);

        } catch (Exception e) {
            throw new IdNotFound(childrenId);
        }
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IdNotFound(gameId));
        GameStat gameStat = gameStatRepository.findByChildrenIdAndGame(childrenId, game)
                .orElseThrow(() -> new IdNotFound(childrenId));
        gameStat.setTotalScore(gameStat.getTotalScore()+gameStats.getTotalScore());
        if (gameStat.getBestScore() < gameStats.getBestScore()){
            gameStat.setBestScore(gameStats.getBestScore());
        }
        return gameStatRepository.save(gameStats);
    }
}
