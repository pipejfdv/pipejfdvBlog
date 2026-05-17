package com.pipejfdv.Juegos.MCSJuegos.Model.Models;

import lombok.Data;
import lombok.Getter;

import java.time.Duration;

/*
* DTO that packages all parameters needed to calculate progress
* Contains game results (correct answers, mistakes, time) and child progress reference
*/
@Data
@Getter
public class ProgressParameterPackage {
    int correctAnswer;
    int totalItems;
    int mistakes;
    Duration timeTaken;
    Duration maxTime;
    ChildProgres childProgres;

    public ProgressParameterPackage(int correctAnswer, int totalItems, int mistakes, Duration timeTaken, Duration maxTime, ChildProgres childProgres) {
        this.correctAnswer = correctAnswer;
        this.totalItems = totalItems;
        this.mistakes = mistakes;
        this.timeTaken = timeTaken;
        this.maxTime = maxTime;
        this.childProgres = childProgres;
    }
}
