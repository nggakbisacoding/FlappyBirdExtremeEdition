package com.game.flappybird.component;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import com.game.flappybird.util.Constant;
import com.game.flappybird.util.GameUtil;
import java.awt.Graphics;
import java.io.IOException;

public class Item {
    static BufferedImage[] imgs;
    public static int ITM_WIDTH;
    public static int ITM_HEIGHT;
    public static int HEAD_WIDTH;
    public static int HEAD_HEIGHT;
    
    static {
        final int BOX_IMAGE_COUNT = 2;
        imgs = new BufferedImage[BOX_IMAGE_COUNT];
        int count = 0;
        for (String data : Constant.BOX_IMG_PATH) {
            imgs[count] = GameUtil.loadBufferedImage(data);
            count++;
        }
    }

    int speed;
    int width, height;
    
    public static int ITEM_WIDTH = imgs[0].getWidth();
    public static int ITEM_HEIGHT = imgs[0].getHeight();
    public static int BOX_HEAD_HEIGHT = imgs[1].getHeight();
    public static int BOX_HEAD_WIDTH = imgs[1].getWidth();
    
    Rectangle itemRect;
    int x, y, dura;
    boolean visible;
    
    public void setAttribute(int x, int y, int height, boolean visible) {
        dura = GameUtil.getRandomNumber(1, 10);
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
            visible = true;
        }
    }
    
    public void draw(Graphics g, Bird bird) throws IOException {
        drawItem(g);
        if(bird.isDead()) {
            return;
        }
        movement();
    }
    
    public void drawItem(Graphics g) {
        g.drawImage(imgs[0], x, y * ITEM_HEIGHT, null);
    }

    public boolean isInFrame() {
        return x + width < Constant.FRAME_WIDTH;
    }
    
    public int getX() {
        return x;
    }
    
    public int getDura() {
        return dura;
    }
    
    public void openBox(Graphics g, Bird bird) throws IOException {
        if(bird.isDead()) {
            return;
        }
        g.drawImage(imgs[1], x, y * BOX_HEAD_HEIGHT, null);
    }

    public Rectangle getitemRect() {
        return itemRect;
    }
}
