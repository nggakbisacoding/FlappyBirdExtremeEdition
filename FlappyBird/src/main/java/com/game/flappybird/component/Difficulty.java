package com.game.flappybird.component;

public class Difficulty {
    private static String state;
    private static final int EASY = 0;
    private static final int MEDIUM = 1;
    private static final int HARD = 2;

    public static void setDifficulty(int difficulty) {
        switch (difficulty) {
            case EASY -> state = "Easy";
            case MEDIUM -> state = "Medium";
            case HARD -> state = "Hard";
        }
    }
}
