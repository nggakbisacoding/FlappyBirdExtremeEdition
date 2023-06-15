package com.game.flappybird.component;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import com.game.flappybird.util.Constant;
import java.awt.Graphics;

public class Item {
    static BufferedImage[] imgs;

    int speed;
    int width, height;
    
    private static final int ITEM_WIDTH = imgs[0].getWidth();

    Rectangle itemRect;
    private int x;
    private int y;
    private boolean visible;
    
    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.visible = visible;
        setRectangle(this.x, this.y, this.height);
    }
    
    public void setRectangle(int x, int y, int height) {
        itemRect.x = x;
        itemRect.y = y;
        itemRect.height = height;
    }
    
    public boolean isVisible() {
        return visible;
    }


    public Item() {
        this.speed = Constant.GAME_SPEED;
        this.width = ITEM_WIDTH;

        itemRect = new Rectangle();
        itemRect.width = ITEM_WIDTH;
    }
    
    private void movement() {
        x -= speed;
        itemRect.x -= speed;
        if (x < -1 * ITEM_WIDTH) {
            visible = false;
        }
    }
    
    public void draw(Graphics g, Bird bird) {
//      g.setColor(Color.black);
//      g.drawRect((int) pipeRect.getX(), (int) pipeRect.getY(), (int) pipeRect.getWidth(), (int) pipeRect.getHeight());
        if (bird.getBirdCollisionRect().y == width) {
            return;
        }
        movement();
    }

    public boolean isInFrame() {
        return x + width < Constant.FRAME_WIDTH;
    }
    
    public int getX() {
        return x;
    }

    public Rectangle getitemRect() {
        return itemRect;
    }
}
