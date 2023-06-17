package com.game.flappybird.component;

import com.game.flappybird.util.GameUtil;
import java.util.List;

public class GenerateItem{
    public static int state;
    public static final int SPEED_UP = 0;
    public static final int SPEED_DOWN = 1;
    public static final int SCORE_DOWN = 2;
    public static final int SCORE_UP = 3;
    public static int dura;
    
    public static void setRandomItem() {
        state = GameUtil.getRandomNumber(SPEED_UP, SCORE_UP);
    }
    
    public boolean isBoosted() {
        return state == SPEED_UP || state == SPEED_DOWN;
    }
    
    public boolean isScored() {
        return state == SCORE_DOWN || state == SCORE_UP;
    }
}
