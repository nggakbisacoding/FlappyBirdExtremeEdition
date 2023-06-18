package com.game.flappybird.component;

import java.util.ArrayList;
import java.util.List;

import com.game.flappybird.util.Constant;

public class ItemPool {
    private static final List<Item> pool = new ArrayList<>(); 
    private static final List<MovingItem> movingPool = new ArrayList<>();
    public static final int MAX_ITEM_COUNT = 10; 
    public static final int FULL_ITEM = (Constant.FRAME_WIDTH / (Item.BOX_HEAD_WIDTH + GameElementLayer.HORIZONTAL_INTERVAL) + 2) * 2;
    
    static {
        for (int i = 0; i < ItemPool.FULL_ITEM; i++) {
            pool.add(new Item());
        }
        for (int i = 0; i < ItemPool.FULL_ITEM; i++) {
            movingPool.add(new MovingItem());
        }
    }
        
    public static Item get(String className) throws Exception {
        if ("Item".equals(className)) {
            int size = pool.size();
            if (size > 0) {
                return pool.remove(size - 1);
            } else {
                return new Item();
            }
        } else {
            int size = movingPool.size();
            if (size > 0) {
                return movingPool.remove(size - 1);
            } else {
                return new MovingItem();
            }
        }
    }

    public static void giveBack(Item item) {
        if(item.getClass() == Item.class) {
            if (pool.size() < MAX_ITEM_COUNT) {
                pool.add(item);
            }
        }else {
            if (movingPool.size() < MAX_ITEM_COUNT) {
                movingPool.add((MovingItem)item);
            }
        }
    }
}
