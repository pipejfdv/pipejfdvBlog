package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.ComunicatiosMCS.FunnyMindDB;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.GameStat;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.ChildDTO;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.GameStatsDTO;
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
    public GameStatsDTO createGameStats(GameStat gameStats) {
        try {
            ChildDTO children = funnyMindDB.getChildren(gameStats.getChildrenId());
            Game game = gameRepository.findById(gameStats.getGame().getId())
                    .orElseThrow(() -> new IdNotFound(gameStats.getGame().getId()));
            GameStat savedGameStat = gameStatRepository.save(new GameStat(
                    gameStats.getTotalScore(),
                    gameStats.getBestScore(),
                    game,
                    children.getId()
            ));
            return new GameStatsDTO(
                    savedGameStat.getId(),
                    children.getId(),
                    children.getNames(),
                    savedGameStat.getGame().getId(),
                    game.getName(),
                    savedGameStat.getTotalScore(),
                    savedGameStat.getBestScore(),
                    savedGameStat.getLastPlay()
            );
        } catch (Exception e) {
            throw new RuntimeException("error: " + e);
        }
    }

    // Read - it's just for game
    public GameStatsDTO getGameStats(UUID childrenId, UUID gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new IdNotFound(gameId));
        GameStat gameStat = gameStatRepository.findByChildrenIdAndGame(childrenId, game)
                .orElseThrow(() -> new IdNotFound(childrenId));
        ChildDTO children = funnyMindDB.getChildren(gameStat.getChildrenId());
        return new GameStatsDTO(
                gameStat.getId(),
                gameStat.getChildrenId(),
                children.getNames(),
                gameStat.getGame().getId(),
                game.getName(),
                gameStat.getTotalScore(),
                gameStat.getBestScore(),
                gameStat.getLastPlay()
        );
    }

    // update
    public GameStatsDTO updateGameStats(UUID childrenId, UUID gameId, GameStat gameStats) {
        ChildDTO children;
        try {
            children = funnyMindDB.getChildren(childrenId);
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
        GameStat updateGameStat = gameStatRepository.save(gameStat);
        return new GameStatsDTO(
                updateGameStat.getId(),
                updateGameStat.getChildrenId(),
                children.getNames(),
                updateGameStat.getGame().getId(),
                game.getName(),
                updateGameStat.getTotalScore(),
                updateGameStat.getBestScore(),
                updateGameStat.getLastPlay()
        );
    }
}
