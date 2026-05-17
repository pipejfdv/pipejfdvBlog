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

/*
* Service layer for game statistics operations
* Handles CRUD for individual game scores per child, communicating with User FM service
*/
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
    /*
	* Creates a new game statistics record for a child
	* Validates child existence via User FM and game existence in database
	* @Param gameStats GameStat entity with score and child/game references
	* @Return GameStatsDTO with created statistics
	* @Throw IdNotFound if game ID does not exist
	* @Throw RuntimeException if communication with User FM fails
	*/
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

    /*
	* Gets game statistics for a specific child and game
	* @Param childrenId UUID of the child
	* @Param gameId UUID of the game
	* @Return GameStatsDTO with statistics data
	* @Throw IdNotFound if game or stats not found
	*/
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

    /*
	* Updates game statistics, adding total score and updating best score if higher
	* @Param childrenId UUID of the child
	* @Param gameId UUID of the game
	* @Param gameStats GameStat object with new score data
	* @Return GameStatsDTO with updated statistics
	* @Throw IdNotFound if child or game stats not found
	*/
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
