package com.pipejfdv.Juegos.MCSJuegos.Model.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Table(name = "games")
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)",nullable = false, updatable = false)
    private UUID id;
    @Column(nullable = false)
    private String name;

    // relation N:1 with CategoryOfGame
    @ManyToOne(targetEntity = CategoryOfGame.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_of_game")
    private CategoryOfGame categoryOfGames;

    @OneToOne(targetEntity = GameStat.class, fetch = FetchType.EAGER)
    private GameStat gameStat;

    public Game(String name, CategoryOfGame categoryOfGames) {
        this.name = name;
        this.categoryOfGames = categoryOfGames;
    }
}