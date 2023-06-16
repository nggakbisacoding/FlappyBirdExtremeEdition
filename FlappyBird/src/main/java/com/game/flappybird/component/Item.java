package com.game.flappybird.component;

import static com.game.flappybird.component.Pipe.imgs;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

import com.game.flappybird.util.Constant;
import com.game.flappybird.util.GameUtil;
import java.awt.Graphics;

public class Item {
    static BufferedImage[] imgs;
    
    static {
        final int BOX_IMAGE_COUNT = 2;
        imgs = new BufferedImage[BOX_IMAGE_COUNT];
        for (int i = 0; i < BOX_IMAGE_COUNT; i++) {
            imgs[i] = GameUtil.loadBufferedImage(Constant.BOX_IMG_PATH[i]);
        }
    }

    int speed;
    int width, height;
    
    private static final int ITEM_WIDTH = imgs[0].getWidth();
    public static final int ITEM_HEIGHT = imgs[0].getHeight();
    public static final int BOX_HEAD_WIDTH = imgs[1].getWidth();
    public static final int BOX_HEAD_HEIGHT = imgs[1].getHeight();

    Rectangle itemRect;
    private int x;
    private int y;
    private boolean visible;
    
    public void setAttribute(int x, int y, int height, boolean visible) {
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
            openBox(bird);
        }
        movement();
    }

    public boolean isInFrame() {
        return x + width < Constant.FRAME_WIDTH;
    }
    
    public int getX() {
        return x;
    }
    
    public void openBox(Bird bird) {
        
    }

    public Rectangle getitemRect() {
        return itemRect;
    }
}
