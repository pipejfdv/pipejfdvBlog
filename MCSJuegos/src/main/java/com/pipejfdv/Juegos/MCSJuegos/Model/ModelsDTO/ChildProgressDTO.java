package com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO;

import com.pipejfdv.Juegos.MCSJuegos.Model.Models.levelDomain;
import lombok.Getter;

import java.util.UUID;

/*
* DTO for transferring child progress data between layers
* Contains XP, daily attempts, level, and child ID
*/
@Getter
public class ChildProgressDTO {
    private UUID id;
    private double xp;
    private int attemptsDaily;
    private levelDomain.level level;
    private UUID childrenId;

    public ChildProgressDTO(UUID id, double xp, int attemptsDaily, levelDomain.level level, UUID childrenId) {
        this.id = id;
        this.xp = xp;
        this.attemptsDaily = attemptsDaily;
        this.level = level;
        this.childrenId = childrenId;
    }
}
