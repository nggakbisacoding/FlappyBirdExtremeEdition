package com.game.flappybird.component;

import java.awt.*;
import java.awt.image.BufferedImage;

import com.game.flappybird.util.Constant;
import com.game.flappybird.util.GameUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Pipe {
    static BufferedImage[] imgs;

    static {
        final int PIPE_IMAGE_COUNT = 3;
        imgs = new BufferedImage[PIPE_IMAGE_COUNT];
        for (int i = 0; i < PIPE_IMAGE_COUNT; i++) {
            imgs[i] = GameUtil.loadBufferedImage(Constant.PIPE_IMG_PATH[i]);
        }
    }

    public static final int PIPE_WIDTH = imgs[0].getWidth();
    public static final int PIPE_HEIGHT = imgs[0].getHeight();
    public static final int PIPE_HEAD_WIDTH = imgs[1].getWidth();
    public static final int PIPE_HEAD_HEIGHT = imgs[1].getHeight();

    int x, y;
    int width, height;

    boolean visible;
    int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_TOP_HARD = 1;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_BOTTOM_HARD = 3;
    public static final int TYPE_HOVER_NORMAL = 4;
    public static final int TYPE_HOVER_HARD = 5;

    int speed;

    Rectangle pipeRect;

    public Pipe() {
        this.speed = Constant.GAME_SPEED;
        this.width = PIPE_WIDTH;

        pipeRect = new Rectangle();
        pipeRect.width = PIPE_WIDTH;
    }

    public void setAttribute(int x, int y, int height, int type, boolean visible) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.type = type;
        this.visible = visible;
        setRectangle(this.x, this.y, this.height);
    }

    public void setRectangle(int x, int y, int height) {
        pipeRect.x = x;
        pipeRect.y = y;
        pipeRect.height = height;
    }

    public boolean isVisible() {
        return visible;
    }

    public void draw(Graphics g, Bird bird) {
        switch (type) {
            case TYPE_TOP_NORMAL -> drawTopNormal(g);
            case TYPE_BOTTOM_NORMAL -> drawBottomNormal(g);
            case TYPE_HOVER_NORMAL -> drawHoverNormal(g);
        }
//      g.setColor(Color.black);
//      g.drawRect((int) pipeRect.getX(), (int) pipeRect.getY(), (int) pipeRect.getWidth(), (int) pipeRect.getHeight());
        try {
            if (bird.isDead()) {
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(Pipe.class.getName()).log(Level.SEVERE, null, ex);
        }
        movement();
    }

    private void drawTopNormal(Graphics g) {
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT, null);
        }
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT, null);
    }

    private void drawBottomNormal(Graphics g) {
        int count = (height - PIPE_HEAD_HEIGHT - Constant.GROUND_HEIGHT) / PIPE_HEIGHT + 1;
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - Constant.GROUND_HEIGHT - i * PIPE_HEIGHT,
                    null);
        }
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), Constant.FRAME_HEIGHT - height, null);
    }

    private void drawHoverNormal(Graphics g) {
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y, null);
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
        }
        int kanan = this.y + height - PIPE_HEAD_HEIGHT;
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), kanan, null);
    }

    private void movement() {
        x -= speed;
        pipeRect.x -= speed;
        if (x < -1 * PIPE_HEAD_WIDTH) {
            visible = false;
        }
    }

    public boolean isInFrame() {
        return x + width < Constant.FRAME_WIDTH;
    }
    
    public int getX() {
        return x;
    }
    
    public int getHeight() {
        return height;
    }

    public Rectangle getPipeRect() {
        return pipeRect;
    }

}