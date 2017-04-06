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
public class Elevation extends Layer<Double> {

    private Perlin noise;

    @Override
    public void init(int seed) {
        noise = new Perlin();
        noise.setPersistence(0.5);
        noise.setNoiseQuality(NoiseQuality.FAST);
        noise.setOctaveCount(10);
        noise.setSeed(seed);
        noise.setFrequency(1);
        noise.setLacunarity(1.5);
    }

    public Double getSeaLevel() {
        return 127.5;
    }
    private double period= 0.003;
    @Override
    public Double getValue(int x, int y) {
        return noise.getValue(x*period, y*period, 0);
    }

    @Override
    public Color getDisplayColor(int x, int y) {
        int val = (int) Math.round((getValue(x,y) / 2) * 255);
        val = Math.min(val, 255);
        val = Math.max(val, 0);
        if (val > getSeaLevel()) {
            return new Color(0, val, 0);
        } else {
            return new Color(0, 0, val);
        }
    }
    
    @Override
    public String toString(){
        return "Elevation";
    }

}
