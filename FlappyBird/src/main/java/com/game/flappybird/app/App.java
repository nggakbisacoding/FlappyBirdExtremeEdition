/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.game.flappybird.app;

import com.game.flappybird.util.Constant;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class App extends JPanel{
    public static void main(String[] args) {
        JFrame frame = new JFrame(Constant.GAME_TITLE);
        Game world = new Game();
        frame.add(world);
        frame.setSize(Constant.FRAME_WIDTH, Constant.FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
