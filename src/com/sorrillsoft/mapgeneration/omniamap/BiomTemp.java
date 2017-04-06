/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.omniamap;

import com.flowpowered.noise.NoiseQuality;
import com.flowpowered.noise.module.source.Perlin;
import com.sorrillsoft.mapgeneration.Layer;
import java.awt.Color;

/**
 *
 * @author alan
 */
public class BiomTemp extends Layer<Double> {

    protected Perlin noise;
    protected void initNoise(int seed){
        noise = new Perlin();
        noise.setPersistence(0.8);
        noise.setNoiseQuality(NoiseQuality.FAST);
        noise.setOctaveCount(4);
        noise.setSeed(seed + 1);
        noise.setFrequency(1);
        noise.setLacunarity(0.5);
    }

    @Override
    public void init(int seed) {
        initNoise(seed);
    }

    private double period = 0.003 * 2;

    @Override
    public Double getValue(int x, int y) {
        return noise.getValue(x * period, y * period, 0);
    }

    @Override
    public Color getDisplayColor(int x, int y) {
        int val = (int) Math.round((getValue(x, y) / 2) * 255);
        val = Math.min(val, 255);
        val = Math.max(val, 0);
        return new Color(val, 0, 0, 127);
    }

    @Override
    public String toString() {
        return "BiomTemp";
    }

}
