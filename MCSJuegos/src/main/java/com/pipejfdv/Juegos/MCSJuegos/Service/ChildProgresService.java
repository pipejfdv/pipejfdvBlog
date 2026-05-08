package com.pipejfdv.Juegos.MCSJuegos.Service;

import com.pipejfdv.Juegos.MCSJuegos.ComunicatiosMCS.FunnyMindDB;
import com.pipejfdv.Juegos.MCSJuegos.Exceptions.IdNotFound;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.ChildProgres;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.ProgressParameterPackage;
import com.pipejfdv.Juegos.MCSJuegos.Model.Models.levelDomain;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.CategoryOfGameDTO;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.ChildDTO;
import com.pipejfdv.Juegos.MCSJuegos.Model.ModelsDTO.ChildProgressDTO;
import com.pipejfdv.Juegos.MCSJuegos.Repositories.ChildProgresRepository;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
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
        CategoryOfGameDTO categoryOfGame = categoryOfGameService.getCategoryOfGame(categoryOfGameId);
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
    private double multiplicationScore(double score, UUID childrenId, UUID categoryOfGameId){
        ChildProgres childProgres = childProgresRepository.findByChildrenIdAndCategoryOfGameId(childrenId, categoryOfGameId)
                .orElseThrow(() -> new IdNotFound(childrenId));
        log.info("Obtuve el niño: {}", childProgres.getChildrenId());
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
        if(finalXp >= levelExpert){
            return levelDomain.level.Experto;
        } else if (finalXp >= levelAdvanced) {
            return levelDomain.level.Avanzado;
        } else if (finalXp >= levelIntermediate) {
            return levelDomain.level.Intermedio;
        } else if (finalXp >= levelBasic) {
            return levelDomain.level.Basico;
        } else if (finalXp >= levelBeginner) {
            return levelDomain.level.Basico;
        } else {
            return levelDomain.level.Inicial;
        }
    }
    // validation if ChildProgres already exists
    public boolean existsChildProgres(UUID childrenId, UUID categoryOfGameId) {
        return childProgresRepository.findByChildrenIdAndCategoryOfGameId(childrenId, categoryOfGameId).isPresent();
    }
    //-------------------CRUD-------------------
    // Create
    public ChildProgressDTO createChildProgres(ProgressParameterPackage params) throws IdNotFound {
        ChildDTO child = null;
        try {
            child = funnyMindDB.getChildren(params.getChildProgres().getChildrenId());
        }
        catch (FeignException.NotFound e) {
            throw new IdNotFound(params.getChildProgres().getChildrenId());
        }
        double score = scoreForGame(params.getChildProgres().getCategoryOfGame().getId(), params.getTotalItems(),
                params.getCorrectAnswer(), params.getMistakes(), params.getTimeTaken(), params.getMaxTime());
        double xp = calculateScore(score);
        ChildProgres childProgres = childProgresRepository.save(new ChildProgres(
                params.getChildProgres().getLevel(),
                xp,
                params.getChildProgres().getCategoryOfGame(),
                child.getId()
        ));
        return new ChildProgressDTO(
                childProgres.getId(),
                childProgres.getXp(),
                childProgres.getAttemptsDaily(),
                childProgres.getLevel(),
                childProgres.getChildrenId()
        );
    }

    // Read only one
    public ChildProgressDTO getChildProgres(UUID childrenId, UUID categoryOfGameId) throws IdNotFound {
        ChildProgres childProgres = childProgresRepository.findByChildrenIdAndCategoryOfGameId(childrenId, categoryOfGameId)
                .orElseThrow(()-> new IdNotFound(childrenId));
        return new ChildProgressDTO(
                childProgres.getId(),
                childProgres.getXp(),
                childProgres.getAttemptsDaily(),
                childProgres.getLevel(),
                childProgres.getChildrenId()
        );
    }

    // update by game
    public ChildProgressDTO updateChildProgres(ProgressParameterPackage params) throws IdNotFound {
        double score = scoreForGame(params.getChildProgres().getCategoryOfGame().getId(), params.getTotalItems(),
                params.getCorrectAnswer(), params.getMistakes(), params.getTimeTaken(), params.getMaxTime());
        double xp = calculateScore(score);
        double xpTotal = multiplicationScore(xp, params.getChildProgres().getChildrenId(), params.getChildProgres().getCategoryOfGame().getId());
        ChildProgres childProgres = childProgresRepository.findByChildrenIdAndCategoryOfGameId(
                params.getChildProgres().getChildrenId(), params.getChildProgres().getCategoryOfGame().getId())
                .orElseThrow(() -> new IdNotFound(params.getChildProgres().getChildrenId()));
        childProgres.setAttemptsDaily(childProgres.getAttemptsDaily()+1);
        double finalXp = penalty(xpTotal , childProgres.getAttemptsDaily()) + childProgres.getXp();
        childProgres.setLevel(nextLevel(finalXp));
        childProgres.setXp(finalXp);
        ChildProgres updateChild = childProgresRepository.save(childProgres);
        return new ChildProgressDTO(
                updateChild.getId(),
                updateChild.getXp(),
                updateChild.getAttemptsDaily(),
                updateChild.getLevel(),
                updateChild.getChildrenId()
        );
    }


}
