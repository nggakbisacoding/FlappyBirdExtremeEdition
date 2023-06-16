package com.game.flappybird.component;

import java.util.List;

public class GenerateItem extends Item{
    private static final String[] power = {"SpeedUP","SpeedDown", "HealthUP", "HealthDown", "ScoreUp", "ScoreDown"};
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
}
