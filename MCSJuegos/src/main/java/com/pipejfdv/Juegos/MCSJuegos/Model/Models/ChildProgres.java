package com.pipejfdv.Juegos.MCSJuegos.Model.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "child_progres")
public class ChildProgres {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "CHAR(36)",nullable = false, updatable = false)
    private UUID id;
    @Column(nullable = false)
    private double xp = 0;
    @Column(nullable = false)
    private int attemptsDaily = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private levelDomain.level level;

    // relation *:1 with CategoryOfGame
    @ManyToOne(targetEntity = CategoryOfGame.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "category_of_game")
    private CategoryOfGame categoryOfGame;

    // relation 1:* with MCSUsersFM - Children
    @Column(nullable = false)
    private UUID childrenId;
}