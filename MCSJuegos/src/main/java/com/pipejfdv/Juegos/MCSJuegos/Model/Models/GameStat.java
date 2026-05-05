package com.pipejfdv.Juegos.MCSJuegos.Model.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "game_stats")
public class GameStat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "CHAR(36)",nullable = false, updatable = false)
    private UUID id;
    @Convert(converter = DurationConverter.class)
    @Column(nullable = false)
    private Duration totalPlayTime = Duration.ZERO;
    @Column(nullable = false)
    private long totalScore;
    @Column(nullable = false)
    private long bestScore;
    @Column(nullable = false)
    private LocalDateTime lastPlay;

    // relation 1:1 with Game
    @OneToOne(targetEntity = Game.class, fetch = FetchType.LAZY)
    private Game game;

    // relation 1:* with MCSUsersFM - Children
    @Column(nullable = false)
    private UUID childrenId;

    public GameStat(long totalScore, long bestScore, Game game, UUID childrenId) {
        this.totalScore = totalScore;
        this.bestScore = bestScore;
        this.game = game;
        this.childrenId = childrenId;
    }

    @PrePersist
    public void prePersist() {
        lastPlay = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        lastPlay = LocalDateTime.now();
    }
}

// Auto apply converter for Duration type
@Converter(autoApply = true)
class DurationConverter implements AttributeConverter<Duration, Long>{
    // Convert Duration object to database column (seconds)
    @Override
    public Long convertToDatabaseColumn(Duration duration){
        return duration == null ? null : duration.getSeconds();
    }
    // Convert database column (seconds) back to Duration object
    @Override
    public Duration convertToEntityAttribute(Long seconds){
        return seconds == null ? null : Duration.ofSeconds(seconds);
    }
}