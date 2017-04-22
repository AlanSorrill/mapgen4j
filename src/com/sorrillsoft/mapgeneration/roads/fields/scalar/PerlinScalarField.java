/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.fields.scalar;

import com.flowpowered.noise.module.source.Perlin;

/**
 *
 * @author Alan
 */
public class PerlinScalarField extends ScalarField {

    public PerlinScalarField(Perlin noise) {
        this.noise = noise;
    }

    public Perlin getNoise() {
        return noise;
    }

    public void setNoise(Perlin noise) {
        this.noise = noise;
    }
    private double z = 0;
    private Perlin noise;

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    private double coefficient = 1;
    private double period = 1;

    @Override
    public double sample(double x, double y) {
        return coefficient * noise.getValue(x * period, y * period, z * period);
    }

    public void setCoefficient(double i) {
        this.coefficient = i;
    }

    public void setPeriod(double d) {
        this.period = d;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public double getPeriod() {
        return period;
    }

}
