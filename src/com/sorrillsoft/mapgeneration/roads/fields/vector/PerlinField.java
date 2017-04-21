/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.fields.vector;

import com.flowpowered.noise.NoiseQuality;
import com.flowpowered.noise.module.source.Perlin;
import com.sorrillsoft.mapgeneration.roads.Vector;
import com.sorrillsoft.mapgeneration.roads.VectorField;
import static java.lang.Math.PI;

/**
 *
 * @author alan
 */
public class PerlinField extends VectorField {

    Perlin noise;
    private double period = 0.001;

    public double getPeriod() {
        return period;
    }

    public void setPeriod(double period) {
        this.period = period;
    }
    

    public PerlinField() {
        noise = new Perlin();
        noise.setPersistence(0.5);
        noise.setNoiseQuality(NoiseQuality.FAST);
        noise.setOctaveCount(10);
        noise.setSeed(0);
        noise.setFrequency(1);
        noise.setLacunarity(1.5);
    }

    @Override
    public Vector onSample(double x, double y) {
        double v = noise.getValue(x * period, y * period, 0);
        //System.out.println(v);
        return Vector.fromTheta(v * (2 * PI), 10);
    }

    public Perlin getNoise() {
        return noise;
    }

}
