package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.ComunicatiosMCS.FunnyMindDB;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.CategoryOfGame;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.ChildProgres;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.levelDomain;
import com.pipejfdv.Juegos.MCSJuegos.Repositories.ChildProgresRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class ChildProgresService {
    private final ChildProgresRepository childProgresRepository;
    private final CategoryOfGameService categoryOfGameService;
    private final FunnyMindDB funnyMindDB;
    static float StandardPointsCategory = 4;
    static int levelBeginner = 200;
    static int levelBasic = 400;
    static int levelIntermediate = 600;
    static int levelAdvanced = 800;
    static int levelExpert = 1000;

    public ChildProgresService(ChildProgresRepository childProgresRepository,
                               FunnyMindDB funnyMindDB,
                               CategoryOfGameService categoryOfGameService) {
        this.childProgresRepository = childProgresRepository;
        this.funnyMindDB = funnyMindDB;
        this.categoryOfGameService = categoryOfGameService;
    }
    // configuration of attempts for daily
    @Scheduled(cron = "0 0 0 * * ?") // midnight
    public void resetAttemptsDaily() {
        childProgresRepository.resetAttemptsDaily();
    }

    // score for game
    private double scoreForGame(UUID categoryOfGameId,
                                int totalItems, int correctAnswer, int mistakes,
                                Duration timeTaken, Duration maxTime){
        CategoryOfGame categoryOfGame = categoryOfGameService.getCategoryOfGame(categoryOfGameId);
        if(categoryOfGame.getName().equals("FUNCIÓN EJECUTIVA") || categoryOfGame.getName().equals("MEMORIA Y ATENCIÓN")){
            double score = (double) (correctAnswer / totalItems)*100 - mistakes * 4.2;
            return (score > 30)
                    ? score
                    : 0;
        } else if (categoryOfGame.getName().equals("VELOCIDAD DE PROCESAMIENTO")) {
            long timeUsed = timeTaken.toSeconds();
            long timeAllowed = maxTime.toSeconds();
            double score = ((double) (timeAllowed - timeUsed)/timeAllowed)*100;
            return (score > 25)
                    ? score
                    : 0;
        }
        else {
            return 0;
        }
    }

    // calculate score
    private double calculateScore(double points){
        return points / StandardPointsCategory;
    }

    // multiplication of score
    private double multiplicationScore(double score, UUID childrenId){
        ChildProgres childProgres = childProgresRepository.findByChildrenId(childrenId)
                .orElseThrow(() -> new IdNotFound(childrenId));
        log.info("Obtuve el niño: {}", childProgres.getChildrenId());
        // get domain by children
        /*String childProgresCategoryOfGame = childProgres.getCategoryOfGame().getName();
        log.info("Obtuve el dominio del niño frente a ese juego:{}", childProgresCategoryOfGame);*/
        // get level by children
        String childProgresLevel = childProgres.getLevel().name();
        log.info("Obtuve el nivel de ese dominio del niño:{}", childProgresLevel);
        return switch (childProgresLevel) {
            case "Inicial" -> score * 0.5;
            case "Basico" -> score * 1;
            case "Intermedio" -> score * 1.5;
            case "Avanzado" -> score * 2.0;
            case "Expert" -> score * 2.5;
            default -> score * 0.0;
        };
    }
    // get penalty
    private double penalty(double score, int attempts){
        return switch (attempts) {
            case 2 -> score + (((score * 80) / 100) - score); // 80% of the total score
            case 3 -> score + (((score * 60) / 100) - score); // 60% of the total score
            case 4 -> score + (((score * 40) / 100) - score); // 40% of the total score
            case 5 -> score + (((score * 20) / 100) - score); // 20% of the total score
            default -> score;
        };
    }
    // get next level
    private levelDomain.level nextLevel(double finalXp){
        if(finalXp > levelBeginner){
            return levelDomain.level.Basico;
        } else if (finalXp > levelBasic) {
            return levelDomain.level.Intermedio;
        } else if (finalXp > levelIntermediate) {
            return levelDomain.level.Avanzado;
        } else if (finalXp > levelAdvanced) {
            return levelDomain.level.Experto;
        } else if (finalXp > levelExpert){
            return levelDomain.level.Experto;
        } else {
            return levelDomain.level.Inicial;
        }
    }
    //-------------------CRUD-------------------
    // Create
    public ChildProgres createChildProgres(ChildProgres childProgres) throws IdNotFound {
        try {
            funnyMindDB.getChildren(childProgres.getChildrenId());
        }
        catch (FeignException.NotFound e) {
            throw new IdNotFound(childProgres.getChildrenId());
        }
        return childProgresRepository.save(childProgres);
    }

    // Read only one
    public ChildProgres getChildProgres(UUID childrenId, UUID categoryOfGameId) throws IdNotFound {
        return childProgresRepository.findByChildrenIdAndCategoryOfGameId(childrenId, categoryOfGameId)
                .orElseThrow(()-> new IdNotFound(childrenId));
    }

    // update by game
    public ChildProgres updateChildProgres(UUID childrenId, UUID categoryOfGameId,
                                           int correctAnswer, int totalItems, int mistakes,
                                           Duration timeTaken, Duration maxTime,
                                           int attempts) throws IdNotFound {
        //validate score is biggest that 30 and value
        double score = scoreForGame(categoryOfGameId, totalItems, correctAnswer, mistakes, timeTaken, maxTime);
        double xp = calculateScore(score);
        double xpTotal = multiplicationScore(xp, childrenId);
        ChildProgres childProgres = childProgresRepository.findByChildrenIdAndCategoryOfGameId(childrenId, categoryOfGameId)
                .orElseThrow(() -> new IdNotFound(childrenId));
        childProgres.setAttemptsDaily(attempts);
        double finalXp = penalty(xpTotal , childProgres.getAttemptsDaily()) + childProgres.getXp();
        childProgres.setLevel(nextLevel(finalXp));
        childProgres.setXp(finalXp);
        return childProgresRepository.save(childProgres);
    }


}
