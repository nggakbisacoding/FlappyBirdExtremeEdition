package com.game.flappybird.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.game.flappybird.app.Game;
import com.game.flappybird.util.Constant;
import com.game.flappybird.util.GameUtil;
import com.game.flappybird.util.MusicUtil;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

public class Bird {
    public static final int IMG_COUNT = 8;
    public static final int STATE_COUNT = 4;
    private final BufferedImage[][] birdImages;
    private final int x;
    private int y;
    private int wingState;
    private static int heart;

    private BufferedImage image;
    private BufferedImage heartImage;

    private int state;
    public static final int BIRD_NORMAL = 0;
    public static final int BIRD_UP = 1;
    public static final int BIRD_FALL = 2;
    public static final int BIRD_DEAD_FALL = 3;
    public static final int BIRD_DEAD = 4;

    private final Rectangle birdCollisionRect;
    public static final int RECT_DESCALE = 2;

    private final ScoreCounter counter;
    private final GameOverAnimation gameOverAnimation;

    public static int BIRD_WIDTH;
    public static int BIRD_HEIGHT;

    public Bird() {
        counter = ScoreCounter.getInstance();
        gameOverAnimation = new GameOverAnimation();

        birdImages = new BufferedImage[STATE_COUNT][IMG_COUNT];
        for (int j = 0; j < STATE_COUNT; j++) {
            for (int i = 0; i < IMG_COUNT; i++) {
                birdImages[j][i] = GameUtil.loadBufferedImage(Constant.BIRDS_IMG_PATH[j][i]);
            }
        }
        
        heartImage = GameUtil.loadBufferedImage(Constant.HEART_PATH);

        assert birdImages[0][0] != null;
        assert heartImage != null;
        BIRD_WIDTH = birdImages[0][0].getWidth();
        BIRD_HEIGHT = birdImages[0][0].getHeight();

        x = Constant.FRAME_WIDTH >> 2;
        y = Constant.FRAME_HEIGHT >> 1;

        int rectX = x - BIRD_WIDTH / 2;
        int rectY = y - BIRD_HEIGHT / 2;
        birdCollisionRect = new Rectangle(rectX + RECT_DESCALE, rectY + RECT_DESCALE * 2, BIRD_WIDTH - RECT_DESCALE * 3,
                BIRD_WIDTH - RECT_DESCALE * 4);
    }

    public void draw(Graphics g) throws LineUnavailableException, IOException {
        movement();
        int state_index = Math.min(state, BIRD_DEAD_FALL);
        int halfImgWidth = birdImages[state_index][0].getWidth() >> 1;
        int halfImgHeight = birdImages[state_index][0].getHeight() >> 1;
        if (velocity > 0)
            image = birdImages[BIRD_UP][0];
        g.drawImage(image, x - halfImgWidth, y - halfImgHeight, null); 

        if (state == BIRD_DEAD && heart < 1)
            gameOverAnimation.draw(g, this);
        else if (state != BIRD_DEAD_FALL)
            drawScore(g);
        drawHeart(g);
//      g.setColor(Color.black);
//      g.drawRect((int) birdRect.getX(), (int) birdRect.getY(), (int) birdRect.getWidth(), (int) birdRect.getHeight());
    }

    public static final int ACC_FLAP = 14; // players speed on flapping
    public static final double ACC_Y = 2; // players downward acceleration
    public static final int MAX_VEL_Y = 15; // max vel along Y, max descend speed
    private int velocity = 0; // bird's velocity along Y, default same as playerFlapped
    private final int BOTTOM_BOUNDARY = Constant.FRAME_HEIGHT - GameBackground.GROUND_HEIGHT - (BIRD_HEIGHT / 2);

    private void movement() throws LineUnavailableException, IOException {
        wingState++;
        image = birdImages[Math.min(state, BIRD_DEAD_FALL)][wingState / 10 % IMG_COUNT];
        if (state == BIRD_FALL || state == BIRD_DEAD_FALL) {
            freeFall();
            if (birdCollisionRect.y > BOTTOM_BOUNDARY) {
                if (state == BIRD_FALL) {
                    MusicUtil.playCrash();
                }
                die();
            }
            else if(birdCollisionRect.y > BOTTOM_BOUNDARY && heart != 0) {
                if (state == BIRD_FALL) {
                    MusicUtil.playCrash();
                }
            }
        }
    }

    private void freeFall() {
        if (velocity < MAX_VEL_Y)
            velocity -= ACC_Y;
        y = Math.min((y - velocity), BOTTOM_BOUNDARY);
        birdCollisionRect.y = birdCollisionRect.y - velocity;
    }

    private void die() {
        counter.saveScore();
        state = BIRD_DEAD;
        Game.setGameState(Game.STATE_OVER);
    }
    
    public int getHealth() {
        return heart;
    }

    public void birdFlap() throws LineUnavailableException, IOException {
        if (keyIsReleased()) {
            if (isDead())
                return;
            MusicUtil.playFly();
            state = BIRD_UP;
            if (birdCollisionRect.y > Constant.TOP_BAR_HEIGHT) {
                velocity = ACC_FLAP;
                wingState = 0;
            }
            keyPressed();
        }
    }

    public void birdFall() throws IOException {
        if (isDead())
            return;
        state = BIRD_FALL;
    }

    public void deadBirdFall() throws LineUnavailableException {
        state = BIRD_DEAD_FALL;
        MusicUtil.playCrash();
        velocity = 0;
    }
    
    public void setHeart() {
        heart = switch (Difficulty.getDifficulty()) {
            case "Easy" -> 2;
            case "Medium" -> 1;
            default -> 0;
        };
    }

    public boolean isDead() throws IOException {
        if(heart > 0)
            return false;
        return state == BIRD_DEAD_FALL || state == BIRD_DEAD;
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
        String str = Long.toString(counter.getCurrentScore());
        int height = Constant.FRAME_WIDTH - GameUtil.getStringWidth(Constant.CURRENT_SCORE_FONT, str) >> 1;
        g.drawString(str, height, Constant.FRAME_HEIGHT / 10);
    }
    
    private void drawHeart(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
        int width = 20;
        for (int i=0;i<=heart;i++) {
            g.drawImage(heartImage, width, Constant.FRAME_HEIGHT / 15, null);
            width += 40;
        }
    }

    public void reset() throws IOException {
        state = BIRD_NORMAL;
        y = Constant.FRAME_HEIGHT >> 1;
        velocity = 0;

        int ImgHeight = birdImages[state][0].getHeight();
        birdCollisionRect.y = y - ImgHeight / 2 + RECT_DESCALE * 2;

        counter.reset();
    }
    
    public void resetNotDead() throws IOException {
        state = BIRD_NORMAL;
        y = Constant.FRAME_HEIGHT >> 1;
        velocity = 0;
        
        int ImgHeight = birdImages[state][0].getHeight();
        birdCollisionRect.y = y - ImgHeight / 2 + RECT_DESCALE * 2;
        
        heart--;
    }
    
    private boolean keyFlag = true; 

    public void keyPressed() {
        keyFlag = false;
    }

    public void keyReleased() {
        keyFlag = true;
    }

    public boolean keyIsReleased() {
        return keyFlag;
    }

    public long getCurrentScore() {
        return counter.getCurrentScore();
    }

    public long getBestScore() {
        return counter.getBestScore();
    }

    public int getBirdX() {
        return x;
    }

    public Rectangle getBirdCollisionRect() {
        return birdCollisionRect;
    }
}