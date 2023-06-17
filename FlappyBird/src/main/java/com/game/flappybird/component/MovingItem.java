package com.game.flappybird.component;

import java.awt.Graphics;

import com.game.flappybird.util.Constant;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MovingItem extends Item{
    private int dealtY;
    public static final int MAX_DELTA = 100;
    private int direction;
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;

    public MovingItem() {
        super();
    }

    @Override
    public void setAttribute(int x, int y, int height, boolean visible) {
        super.setAttribute(x, y, height, visible);
        dealtY = 0;
        direction = DIR_DOWN;
    }
    
    @Override
    public void draw(Graphics g, Bird bird) {
        drawItem(g);
        if (bird.getBirdCollisionRect().y == width) {
            openBox(bird);
        }
        try {
            if (bird.isDead()) {
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(MovingPipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        movement();
    }

    @Override
    public void drawItem(Graphics g) {
        int count = (height - 2 * BOX_HEAD_HEIGHT) / ITEM_HEIGHT + 1;
        g.drawImage(imgs[0], x - ((BOX_HEAD_WIDTH - width) >> 1), y + dealtY, null);
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + dealtY + i * ITEM_HEIGHT + BOX_HEAD_HEIGHT, null);
        }
    }
    
    private void movement() {
        x -= speed;
        itemRect.x -= speed;
        if (x < -1 * BOX_HEAD_WIDTH) {
            visible = false;
        }
        if (direction == DIR_DOWN) {
            dealtY++;
            if (dealtY > MAX_DELTA) {
                direction = DIR_UP;
            }
        } else {
            dealtY--;
            if (dealtY <= 0) {
                direction = DIR_DOWN;
            }
        }
        itemRect.y = this.y + dealtY;
    }
}
