/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.game.flappybird.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 *
 * @author Fandead
 */
public class MusicUtil {

    private static final String fly = "fly.wav";
    private static final String crash = "crash.wav";
    private static final String score = "score.wav";
    private static final String bestScore = "bestscore.wav";
    
    public static void play(String path) throws LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        AudioInputStream audioStream = null;
        
        try {
            Path currentRelativePath = Paths.get("").toAbsolutePath().getParent();
            String s = currentRelativePath.toAbsolutePath().toString();

            File file = new File(s+"\\FlappyBird\\resources\\wav\\"+path);
            audioStream = AudioSystem.getAudioInputStream(file);
            clip.open(audioStream);

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        clip.start();
    }

    public static void playFly() throws LineUnavailableException {
        play(fly);
    }

    public static void playCrash() throws LineUnavailableException {
        play(crash);
    }

    public static void playScore() throws LineUnavailableException {
        play(score);
    }
    
    public static void playBestScore() throws LineUnavailableException {
        play(bestScore);
    }
}