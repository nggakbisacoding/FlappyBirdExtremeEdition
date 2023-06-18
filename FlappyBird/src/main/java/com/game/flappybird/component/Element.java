/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.game.flappybird.component;

import java.awt.Graphics;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Fandead
 */
public interface Element {
    public void draw(Graphics g) throws LineUnavailableException, IOException;
    
    public void draw(Graphics g, Bird bird);
}
