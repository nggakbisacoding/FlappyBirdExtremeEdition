package com.game.flappybird.component;

import com.game.flappybird.util.Constant;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cloud {

    private final int speed;
    private int x;
    private final int y;

    private final BufferedImage img;

    private final int scaleImageWidth;
    private final int scaleImageHeight;

    public Cloud(BufferedImage img, int x, int y) {
        super();
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = Constant.GAME_SPEED * 2;
        double scale = 1 + Math.random();
        scaleImageWidth = (int) (scale * img.getWidth());
        scaleImageHeight = (int) (scale * img.getWidth());
    }

    public void draw(Graphics g, Bird bird) throws IOException {
        int speeds = this.speed;
        if (bird.isDead())
            speeds = 1;
        x -= speeds;
        g.drawImage(img, x, y, scaleImageWidth, scaleImageHeight, null);
    }

    public boolean isOutFrame() {
        return x < -1 * scaleImageWidth;
    }

}