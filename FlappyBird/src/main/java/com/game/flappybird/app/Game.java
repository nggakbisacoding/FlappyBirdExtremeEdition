package com.game.flappybird.app;

import com.game.flappybird.component.GameElementLayer;
import com.game.flappybird.component.Bird;
import com.game.flappybird.component.GameBackground;
import com.game.flappybird.component.GameForeground;
import com.game.flappybird.component.WelcomeAnimation;

import static com.game.flappybird.util.Constant.FRAME_HEIGHT;
import static com.game.flappybird.util.Constant.FRAME_WIDTH;
import static com.game.flappybird.util.Constant.FRAME_X;
import static com.game.flappybird.util.Constant.FRAME_Y;
import static com.game.flappybird.util.Constant.FPS;
import static com.game.flappybird.util.Constant.GAME_TITLE;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

public class Game extends Frame {
    private static final long serialVersionUID = 1L;

    private static int gameState;
    public static final int GAME_READY = 0;
    public static final int GAME_START = 1;
    public static final int STATE_OVER = 2;

    private GameBackground background;
    private GameForeground foreground;
    private Bird bird;
    private GameElementLayer gameElement;
    private WelcomeAnimation welcomeAnimation;

    public Game() {
        initFrame();
        setVisible(true);
        initGame();
    }

    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(GAME_TITLE);
        setLocation(FRAME_X, FRAME_Y);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        addKeyListener(new BirdKeyListener());
    }

    class BirdKeyListener implements KeyListener {
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyCode();
            switch (gameState) {
                case GAME_READY -> {
                    if (keycode == KeyEvent.VK_SPACE) {
                        try {
                            bird.birdFlap();
                            bird.birdFall();
                            setGameState(GAME_START);
                        } catch (LineUnavailableException | IOException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                case GAME_START -> {
                    if (keycode == KeyEvent.VK_SPACE) {
                        try {
                            bird.birdFlap();
                            bird.birdFall();
                        } catch (LineUnavailableException | IOException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                case STATE_OVER -> {
                    if (keycode == KeyEvent.VK_SPACE) {
                        try {
                            if(bird.getHealth() >= 0)
                                resetNotDead();
                            else
                                resetGame();
                        } catch (IOException ex) {
                            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
        
        private void resetNotDead() throws IOException {
            setGameState(GAME_READY);
            gameElement.reset();
            bird.resetNotDead();
        }

        private void resetGame() throws IOException {
            setGameState(GAME_READY);
            gameElement.reset();
            bird.reset();
            bird.setHeart();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyChar();
            if (keycode == KeyEvent.VK_SPACE) {
                bird.keyReleased();
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }
    }

    private void initGame() {
        background = new GameBackground();
        gameElement = new GameElementLayer();
        foreground = new GameForeground();
        welcomeAnimation = new WelcomeAnimation();
        bird = new Bird();
        setGameState(GAME_READY);

        new Thread(() ->{
            while (true) {
                repaint();
                try {
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private final BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);


    @Override
    public void update(Graphics g) {
        Graphics bufG = bufImg.getGraphics();
        try {
            background.draw(bufG, bird);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            foreground.draw(bufG, bird);
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (gameState == GAME_READY) {
            welcomeAnimation.draw(bufG);
        } else {
            try {
                gameElement.draw(bufG, bird);
            } catch (LineUnavailableException | IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            bird.draw(bufG);
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        g.drawImage(bufImg, 0, 0, null);
    }

    public static void setGameState(int gameState) {
        Game.gameState = gameState;
    }

}