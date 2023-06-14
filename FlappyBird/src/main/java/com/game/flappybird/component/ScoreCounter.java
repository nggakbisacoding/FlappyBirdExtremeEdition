package com.game.flappybird.component;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.game.flappybird.util.Constant;
import com.game.flappybird.util.MusicUtil;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

public class ScoreCounter {

	private static class ScoreCounterHolder {
		private static final ScoreCounter scoreCounter = new ScoreCounter();
	}

	public static ScoreCounter getInstance() {
		return ScoreCounterHolder.scoreCounter;
	}

	private long score = 0;
	private long bestScore;

	private ScoreCounter() {
		bestScore = -1;
		try {
			loadBestScore();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadBestScore() throws Exception {
		File file = new File(Constant.SCORE_FILE_PATH);
		if (file.exists()) {
                    try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
                        bestScore = dis.readLong();
                    }
		}
	}

	public void saveScore() {
		bestScore = Math.max(bestScore, getCurrentScore());
		try {
			File file = new File(Constant.SCORE_FILE_PATH);
                    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(file))) {
                        dos.writeLong(bestScore);
                    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void score(Bird bird) throws LineUnavailableException {
		if (!bird.isDead()) {
			MusicUtil.playScore();
			score += 1;
                        if (score > getBestScore())
                            MusicUtil.playBestScore();
		}
	}

	public long getBestScore() {
		return bestScore;
	}

	public long getCurrentScore() {
		return score;
	}

	public void reset() throws FileNotFoundException, IOException {
		score = 0;
	}

}