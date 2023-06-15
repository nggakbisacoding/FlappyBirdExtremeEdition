package com.game.flappybird.component;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import com.game.flappybird.util.Constant;
import com.game.flappybird.util.GameUtil;

public class Item {
    static BufferedImage[] imgs;
    int speed;
    int width, height;
    
    private static final int ITEM_WIDTH = imgs[0].getWidth();

    Rectangle itemRect;

    public Item() {
        this.speed = Constant.GAME_SPEED;
        this.width = ITEM_WIDTH;

        itemRect = new Rectangle();
        itemRect.width = ITEM_WIDTH;

        GameUtil.getRandomNumber(height, ITEM_WIDTH);
    }
}
