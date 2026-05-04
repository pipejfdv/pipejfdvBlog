package com.pipejfdv.Juegos.MCSJuegos.Model.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "category_of_games")
@AllArgsConstructor
@NoArgsConstructor
public class CategoryOfGame {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)",nullable = false, updatable = false)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    // relation 1:N with Game
    @OneToMany(targetEntity = Game.class, fetch = FetchType.LAZY, mappedBy = "categoryOfGames")
    private List<Game> games;

    // relation 1:N with ChildProgres
    @OneToMany(targetEntity = ChildProgres.class, fetch = FetchType.LAZY, mappedBy = "categoryOfGame")
    private List<ChildProgres> childProgres;

    public CategoryOfGame(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
