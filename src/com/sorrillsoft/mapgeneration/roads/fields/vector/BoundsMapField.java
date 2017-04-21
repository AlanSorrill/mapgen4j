/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.fields.vector;

import com.sorrillsoft.mapgeneration.roads.Vector;
import com.sorrillsoft.mapgeneration.roads.VectorField;
import com.sorrillsoft.mapgeneration.roads.fields.scalar.ScalarField;
import static java.lang.Math.max;

/**
 *
 * @author alan
 */
public class BoundsMapField extends VectorField {

    public MultiplyField getEffectField(VectorField other) {
        return new MultiplyField(this, other);
    }

    public BoundsMapField(ScalarField bounds) {
        this.bounds = bounds;
    }
    private ScalarField bounds;

    public ScalarField getBounds() {
        return bounds;
    }

    public void setBounds(ScalarField bounds) {
        this.bounds = bounds;
    }

    @Override
    public Vector onSample(double x, double y) {
        if (bounds.sample(x, y) > 0) {
            return new Vector(1, 1);
        } else {
            return new Vector(0, 0);
        }
    }

}
