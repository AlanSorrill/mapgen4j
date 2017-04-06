/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 *
 * @author Alan
 */
public abstract class Noise {

    private int seed = 0;
    private int width = 10;
    private int height = 10;
    private Display display;

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display d) {
        display = d;
    }

    public void setDisplayProgress(int prog) {
        if (getDisplay() != null) {
            getDisplay().setProgress(prog);
        }
    }

    public void setResolution(int w, int h) {
        width = w;
        height = h;
    }

    public int[] getResolution() {
        return new int[]{width, height};
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
        if (rand == null) {
            rand = new Random(seed);
        } else {
            rand.setSeed(seed);
        }
    }

    public abstract void init();

    public abstract void generate();

    public abstract BufferedImage getImage();

    private Random rand;

    public int getRandomInt(int min, int max) {
        return min + getRand().nextInt(max - min);
    }

    public static int getRandomStaticInt(int min, int max) {
        return (int) (min + Math.random() * (max - min));
    }

    public Random getRand() {
        if (rand == null) {
            setSeed(getSeed());
        }
        return rand;
    }

    public Color getDataAt(int x, int y) {
        return new Color(getImage().getRGB(x, y));
    }
}
