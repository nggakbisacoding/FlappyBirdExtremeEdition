/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.game.flappybird.component;

import java.util.List;

/**
 *
 * @author Fandead
 */
public class GenerateItem extends Item{
    private List<Item> item;

    public GenerateItem(String name, String effect, int amount) {
        super(name, effect, amount);
    }
}
