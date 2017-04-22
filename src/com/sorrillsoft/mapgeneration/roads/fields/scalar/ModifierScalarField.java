/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.fields.scalar;

/**
 *
 * @author Alan
 */
public abstract class ModifierScalarField extends ScalarField {

    public ModifierScalarField(ScalarField sf) {
        original = sf;
    }
    private ScalarField original;

    public abstract double modifySample(double val, double x, double y);

    @Override
    public double sample(double x, double y) {
        return modifySample(original.sample(x, y), x, y);
    }

}
