package com.game.flappybird.component;

public class Difficulty {
    static int state;
    private static final int EASY = 0;
    private static final int MEDIUM = 1;

    public static String getDifficulty() {
        return state == EASY ? "Easy" : state == MEDIUM ? "Medium" : "Hard";
    }
}
