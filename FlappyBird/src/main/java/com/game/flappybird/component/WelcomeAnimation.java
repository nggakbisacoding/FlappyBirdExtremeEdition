package com.game.flappybird.component;

import com.game.flappybird.util.Constant;
import com.game.flappybird.util.GameUtil;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WelcomeAnimation {

    private final BufferedImage titleImg;
    private final BufferedImage noticeImg;
    private String difficulty;

    private int flashCount = 0;

    public WelcomeAnimation() {
        titleImg = GameUtil.loadBufferedImage(Constant.TITLE_IMG_PATH);
        noticeImg = GameUtil.loadBufferedImage(Constant.NOTICE_IMG_PATH);
    }

    public void draw(Graphics g) {
        int x = (Constant.FRAME_WIDTH - titleImg.getWidth()) >> 1;
	int y = Constant.FRAME_HEIGHT / 3;
	g.drawImage(titleImg, x, y, null);
                
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
	difficulty = Difficulty.getDifficulty();
        g.drawString(difficulty, x, y);

	final int CYCLE = 30;
	if (flashCount++ > CYCLE)
            GameUtil.drawImage(noticeImg, Constant.FRAME_WIDTH - noticeImg.getWidth() >> 1, Constant.FRAME_HEIGHT / 5 * 3, g);
	if (flashCount == CYCLE * 2)
            flashCount = 0;
	}
}