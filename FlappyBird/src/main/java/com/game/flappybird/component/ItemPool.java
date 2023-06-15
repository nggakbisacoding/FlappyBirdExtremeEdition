package com.game.flappybird.component;

import java.util.ArrayList;
import java.util.List;

import com.game.flappybird.util.Constant;

public class ItemPool {
    private static final List<Item> pool = new ArrayList<>(); 
    public static final int MAX_ITEM_COUNT = 30; 
    public static final int FULL_ITEM = (Constant.FRAME_WIDTH / (Pipe.PIPE_HEAD_WIDTH + GameElementLayer.HORIZONTAL_INTERVAL) + 2) * 2;

    static {
	for (int i = 0; i < ItemPool.FULL_ITEM; i++) {
            pool.add(new Item());
	}
    }
        
    public static Item get(String className) {
        if ("Item".equals(className)) {
            int size = pool.size();
            if (size > 0) {
                return pool.remove(size - 1);
            } else {
                return new Item();
            }
	}
        return new Item();
    }

    public static void giveBack(Item item) {
        if(item.getClass() == Item.class) {
            if (pool.size() < MAX_ITEM_COUNT) {
                pool.add(item);
            }
        }
    }

}
