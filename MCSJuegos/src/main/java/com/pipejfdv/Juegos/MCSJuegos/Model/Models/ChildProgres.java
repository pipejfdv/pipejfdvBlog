package com.pipejfdv.Juegos.MCSJuegos.Model.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "child_progres",
        uniqueConstraints = @UniqueConstraint(columnNames = {"children_id", "category_of_game"}))
public class ChildProgres {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)",nullable = false, updatable = false)
    private UUID id;
    @Column(nullable = false)
    private double xp = 0;
    // configuration is ChildProgresService
    @Column(name = "attempts_daily")
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

    public ChildProgres(levelDomain.level level, double xp, CategoryOfGame categoryOfGame, UUID childrenId) {
        this.level = level;
        this.xp = xp;
        this.categoryOfGame = categoryOfGame;
        this.childrenId = childrenId;
    }
}