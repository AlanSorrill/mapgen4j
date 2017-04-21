/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.fields.vector;

import com.sorrillsoft.mapgeneration.roads.Vector;
import com.sorrillsoft.mapgeneration.roads.VectorField;
import com.sorrillsoft.mapgeneration.roads.fields.scalar.ScalarField;

/**
 *
 * @author Alan
 */
public class GradiantField extends VectorField {

    public GradiantField(ScalarField f) {
        this.field = field;
    }
    private ScalarField field;

    @Override
    protected Vector onSample(double x, double y) {
        double v = field.sample(x, y);
        double nx = field.sample(x + 1, y);
        double ny = field.sample(x, y + 1);
        return new Vector(v - nx, v - ny);
    }

}
