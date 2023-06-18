package com.game.flappybird.component;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import com.game.flappybird.util.Constant;
import com.game.flappybird.util.GameUtil;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

public class GameElementLayer {
    private final List<Pipe> pipes;
    private final List<Item> items;

    public GameElementLayer() {
        pipes = new ArrayList<>();
        items = new ArrayList<>();
    }

    public void draw(Graphics g, Bird bird) throws LineUnavailableException, IOException, Exception {
        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            if (pipe.isVisible()) {
                pipe.draw(g, bird);
            } else {
                Pipe remove = pipes.remove(i);
                PipePool.giveBack(remove);
                i--;
            }
        }
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if(item.isVisible()) {
                item.draw(g, bird);
            } else {
                Item remove = items.remove(i);
                ItemPool.giveBack(remove);
                i--;
            }
        }
        isCollideBird(bird);
        isCollideBirdItem(g, bird);
        pipeBornLogic(bird);
        bornItem(bird);
    }

    public static final int VERTICAL_INTERVAL = Constant.FRAME_HEIGHT / 5;
    public static final int HORIZONTAL_INTERVAL = Constant.FRAME_HEIGHT >> 2;
    public static final int MIN_HEIGHT = Constant.FRAME_HEIGHT >> 3;
    public static final int MAX_HEIGHT = ((Constant.FRAME_HEIGHT) >> 3) * 5;

    private void pipeBornLogic(Bird bird) throws LineUnavailableException, IOException {
        if (bird.isDead()) {
            return;
        }
        if (pipes.isEmpty()) {
            int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1);

            Pipe top = PipePool.get("Pipe");
            top.setAttribute(Constant.FRAME_WIDTH, -Constant.TOP_PIPE_LENGTHENING,
                    topHeight + Constant.TOP_PIPE_LENGTHENING, Pipe.TYPE_TOP_NORMAL, true);

            Pipe bottom = PipePool.get("Pipe");
            bottom.setAttribute(Constant.FRAME_WIDTH, topHeight + VERTICAL_INTERVAL,
                    Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL, Pipe.TYPE_BOTTOM_NORMAL, true);

            pipes.add(top);
            pipes.add(bottom);
        } else {
            Pipe lastPipe = pipes.get(pipes.size() - 1); 
            int currentDistance = lastPipe.getX() - bird.getBirdX() + Bird.BIRD_WIDTH / 2;
            final int SCORE_DISTANCE = Pipe.PIPE_WIDTH * 2 + HORIZONTAL_INTERVAL; 
            if (lastPipe.isInFrame()) {
                if (pipes.size() >= PipePool.FULL_PIPE - 2
                        && currentDistance >= SCORE_DISTANCE + Pipe.PIPE_WIDTH * 3 / 2) {
                    ScoreCounter.getInstance().score(bird);
                }
                try {
                    int currentScore = (int) ScoreCounter.getInstance().getCurrentScore() + 1;
                    if (GameUtil.isInProbability(currentScore, 20)) {
                        if (GameUtil.isInProbability(1, 4))
                            addMovingHoverPipe(lastPipe);
                        else
                            addMovingNormalPipe(lastPipe);
                    } else {
                        if (GameUtil.isInProbability(1, 2))
                            addNormalPipe(lastPipe);
                        else
                            addHoverPipe(lastPipe);
                    }
                } catch (Exception e) {
                }
            }
        }
    }
    
    public void bornItem(Bird bird) throws IOException, LineUnavailableException, Exception {
        if (bird.isDead()) {
            return;
        }
        if(items.isEmpty()) {
            if(GameUtil.isInProbability(1, 4)) {
                Pipe pipe = pipes.get(pipes.size()-1);
                int top = pipe.height - Item.BOX_HEAD_HEIGHT;
                int bottom = GameUtil.getRandomNumber(top, Item.HEAD_HEIGHT);
            
                try {
                    Item pos = ItemPool.get("Item");
                    pos.setAttribute(top, bottom, Item.ITEM_HEIGHT, true);
            
                    items.add(pos);
                } catch (Exception e) {
                }
            } else {
                int x = (Constant.FRAME_WIDTH * 3) / 2;
                int y = Constant.FRAME_HEIGHT/2;
                
                try {
                    Item c = ItemPool.get("Item");
                    c.setAttribute(x, y, Item.ITEM_HEIGHT, true);
                    
                    items.add(c);
                } catch (Exception e) {
                }
            }
        } else {
            Item lastItem = items.get(items.size() - 1); 
            Pipe pool = pipes.get(0);
            int currentpos = pool.getX() + Pipe.PIPE_WIDTH * 2;
            int currentScore = (int) ScoreCounter.getInstance().getCurrentScore() + 1;
            if (pool.isInFrame()) {
                if (items.size() >= ItemPool.FULL_ITEM - 2
                        && currentpos <= currentScore + Item.HEAD_WIDTH * 3 / 2) {
                    ScoreCounter.getInstance().score(bird); 
                }
                try {
                    if (GameUtil.isInProbability(currentScore, GameUtil.getRandomNumber(1, 50))) {
                        if (GameUtil.isInProbability(1, 5))
                            movingItem(lastItem);
                    } else {
                        if (GameUtil.isInProbability(1, 4))
                            SpawnItem(lastItem);
                    }
                }
                catch (Exception e) {
                }
            }     
        }
    }

    private void addNormalPipe(Pipe lastPipe) {
        int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL;

        Pipe top = PipePool.get("Pipe");

        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING, topHeight + Constant.TOP_PIPE_LENGTHENING,
                Pipe.TYPE_TOP_NORMAL, true);

        Pipe bottom = PipePool.get("Pipe");
        bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL, Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL,
                Pipe.TYPE_BOTTOM_NORMAL, true);

        pipes.add(top);
        pipes.add(bottom);
    }

    private void addHoverPipe(Pipe lastPipe) {

        int topHoverHeight = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL;
        int y = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6);

        int type = Pipe.TYPE_HOVER_NORMAL;

        Pipe topHover = PipePool.get("Pipe");
        topHover.setAttribute(x, y, topHoverHeight, type, true);

        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("Pipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, type, true);

        pipes.add(topHover);
        pipes.add(bottomHover);

    }

    private void addMovingHoverPipe(Pipe lastPipe) {

        int topHoverHeight = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL;
        int y = GameUtil.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6);

        int type = Pipe.TYPE_HOVER_HARD;


        Pipe topHover = PipePool.get("MovingPipe");
        topHover.setAttribute(x, y, topHoverHeight, type, true);

        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("MovingPipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, type, true);

        pipes.add(topHover);
        pipes.add(bottomHover);

    }

    private void addMovingNormalPipe(Pipe lastPipe) {
        int topHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL;

        Pipe top = PipePool.get("MovingPipe");
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING, topHeight + Constant.TOP_PIPE_LENGTHENING,
                Pipe.TYPE_TOP_HARD, true);

        Pipe bottom = PipePool.get("MovingPipe");
        bottom.setAttribute(x, topHeight + VERTICAL_INTERVAL, Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL,
                Pipe.TYPE_BOTTOM_HARD, true);

        pipes.add(top);
        pipes.add(bottom);
    }

    public void isCollideBird(Bird bird) throws LineUnavailableException, IOException {
        if (bird.isDead() && bird.getHealth() == 0) {
            return;
        }
        for (Pipe pipe : pipes) {
            if (pipe.getPipeRect().intersects(bird.getBirdCollisionRect())) {
                bird.deadBirdFall();
                return;
            }
        }
    }

    public void SpawnItem(Item item) throws LineUnavailableException, IOException, Exception {
         if (GameUtil.isInProbability(1, 10)) { // Adjust the probability as needed
            int spaceBetweenPipes = VERTICAL_INTERVAL - Item.ITEM_WIDTH;
            int itemHeight = GameUtil.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT - spaceBetweenPipes);
            int itemY = GameUtil.getRandomNumber(100, spaceBetweenPipes);
            int itemX = Constant.FRAME_WIDTH + Item.ITEM_WIDTH; // X position to spawn the item
            
            item.setAttribute(itemX, itemY, itemHeight, true);
            items.add(item);
         }
    }
    
    private void movingItem(Item item) throws Exception {
        int count = 1;
        int rand = GameUtil.getRandomNumber(1, 20);
        int dominator = GameUtil.getRandomNumber(count, GameUtil.getRandomNumber(count,rand));
        if (GameUtil.isInProbability(count, dominator)) {
            int rnd = GameUtil.getRandomNumber(150,350);
            int topHeight = Item.HEAD_HEIGHT;
            int x = item.getX() + HORIZONTAL_INTERVAL;

            Item top = ItemPool.get("MovingItem");
            top.setAttribute(x, rnd, topHeight, true);

            items.add(top);
        }
    }
    
    public void isCollideBirdItem(Graphics g, Bird bird) throws IOException, Exception {
        if (bird.isDead() && bird.getHealth() == 0) {
            return;
        }
        for (Item item : items) {
            if (item.getitemRect().y == bird.getBirdCollisionRect().y) {
                item.openBox(g, bird);
                bird.birdBoost(g, item);
            }
        }
    }

    public void reset() {
        for (Pipe pipe : pipes) {
            PipePool.giveBack(pipe);
        }
        for (Item item : items) {
            ItemPool.giveBack(item);
        }
        pipes.clear();
    }
}