/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.fields;

import com.sorrillsoft.mapgeneration.roads.Vector;
import com.sorrillsoft.mapgeneration.roads.VectorField;

/**
 *
 * @author alan
 */
public class MultiplyField extends VectorField {

    private VectorField field;
    private VectorField multiplier;

    public MultiplyField(VectorField f, VectorField m) {
        field = f;
        this.multiplier=m;
    }

    @Override
    public Vector onSample(double x, double y) {
        return Vector.multiply(field.sample(x, y), multiplier.sample(x, y));
    }

    public void setField(VectorField f) {
        field = f;
    }

    public void setMultiplier(VectorField f) {
        multiplier = f;
    }

}
