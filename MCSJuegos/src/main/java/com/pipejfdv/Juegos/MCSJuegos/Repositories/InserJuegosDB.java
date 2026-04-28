package com.pipejfdv.Juegos.MCSJuegos.Repositories;

import com.pipejfdv.Juegos.MCSJuegos.Model.Models.CategoryOfGame;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.Game;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InserJuegosDB implements CommandLineRunner {
    private final CategoryOfGameRepository categoryOfGameRepository;
    private final GameRepository gameRepository;

    public InserJuegosDB(CategoryOfGameRepository categoryOfGameRepository, GameRepository gameRepository) {
        this.categoryOfGameRepository = categoryOfGameRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryOfGameRepository.count() == 0){
            categoryOfGameRepository.saveAll(
                    List.of(
                            new CategoryOfGame("FUNCIÓN EJECUTIVA", "Desarrollo de planificación, secuenciación lógica y resolución estratégica de problemas."),
                            new CategoryOfGame("MEMORIA Y ATENCIÓN", "Fortalecimiento de la memoria visual, concentración sostenida y reconocimiento de patrones."),
                            new CategoryOfGame("VELOCIDAD DE PROCESAMIENTO", "Entrenamiento de reflejos mentales, tiempo de reacción y respuesta rápida ante estímulos.")
                    )
            );
        }
        if (gameRepository.count() == 0){
            gameRepository.saveAll(
                    List.of(
                            new Game("Torre de Hanói", categoryOfGameRepository.findByName("FUNCIÓN EJECUTIVA")),
                            new Game("Los pares", categoryOfGameRepository.findByName("MEMORIA Y ATENCIÓN")),
                            new Game("Reaction", categoryOfGameRepository.findByName("VELOCIDAD DE PROCESAMIENTO"))
                    )
            );
        }
    }
}
