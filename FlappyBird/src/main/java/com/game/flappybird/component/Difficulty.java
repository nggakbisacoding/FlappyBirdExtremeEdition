package com.game.flappybird.component;

import com.game.flappybird.util.Constant;

public class Difficulty {
    private static String state;

    public static String getDifficulty() {
        setDifficulty();
        return state == "EASY" ? "Easy" : state == "MEDIUM" ? "Medium" : "ASIAN MODE";
    }
    
    public static void setDifficulty() {
        if(Constant.GAME_SPEED < 5) {
            state = "EASY";
        } else if (Constant.GAME_SPEED > 5 && Constant.GAME_SPEED < 10) {
            state = "MEDIUM";
        }
    }
}
