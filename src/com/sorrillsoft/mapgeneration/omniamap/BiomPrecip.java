/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.omniamap;

import com.flowpowered.noise.NoiseQuality;
import com.flowpowered.noise.module.source.Perlin;

/**
 *
 * @author Alan
 */
public class BiomPrecip extends BiomTemp{
    @Override
    protected void initNoise(int seed){
        noise = new Perlin();
        noise.setPersistence(0.8);
        noise.setNoiseQuality(NoiseQuality.FAST);
        noise.setOctaveCount(4);
        noise.setSeed(seed + 2);
        noise.setFrequency(1);
        noise.setLacunarity(0.5);
    }
}
