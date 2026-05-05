package com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class GameStatsDTO {
    private UUID id;
    private UUID childrenId;
    private String names;
    private UUID gameId;
    private String gameName;
    private long totalScore;
    private long bestScore;
    private LocalDateTime lastPlay;

    public GameStatsDTO(UUID id, UUID childrenId, String names, UUID gameId, String gameName, long totalScore, long bestScore, LocalDateTime lastPlay) {
        this.id = id;
        this.childrenId = childrenId;
        this.names = names;
        this.gameId = gameId;
        this.gameName = gameName;
        this.totalScore = totalScore;
        this.bestScore = bestScore;
        this.lastPlay = lastPlay;
    }
}
