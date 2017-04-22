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
public class ConstantScalarField extends ScalarField {

    private double val;

    public ConstantScalarField(double v) {
        val = v;
    }

    @Override
    public double sample(double x, double y) {
        return val;
    }

}
