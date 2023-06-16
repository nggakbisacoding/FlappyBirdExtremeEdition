package com.game.flappybird.component;

import java.util.List;

public class GenerateItem extends Item{
    private static int state;
    private static final int BOOST_UP = 0;
    private static final int BOOST_DOWN = 1;
    private static final int SPEED_UP = 2;
    private static final int SCORE_DOWN = 3;
    private static final int SCORE_UP = 4;
    private List<Item> item;
    
    public GenerateItem() {
        super();
    }

    public List<Item> getItem() {
        return item;
    }
    
    public Item RandomItem() {
        for(Item data :item) {
            Item items = (Item) item;
        }
        return null;
    }
    
    public void setRandomItem() {
        
    }
    
    public boolean isBoosted() {
        return state == BOOST_UP || state == BOOST_DOWN;
    }
    
    public boolean isScored() {
        return state == SCORE_DOWN || state == SCORE_UP;
    }
}
