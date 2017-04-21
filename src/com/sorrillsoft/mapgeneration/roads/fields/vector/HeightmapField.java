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
public class HeightmapField extends VectorField {

    public HeightmapField(ScalarField f) {
        field = f;
        gradiant = new GradiantField(field);
    }
    private ScalarField field;
    private GradiantField gradiant;

    @Override
    protected Vector onSample(double x, double y) {
        Vector grad = gradiant.sample(x, y);
        double theta = Math.atan2(grad.getY(), grad.getX()) + (Math.PI / 2);
        double r = Math.sqrt(grad.getX()*grad.getX()+grad.getY()*grad.getY());
        return Vector.fromTheta(theta, r);//NEEDS NORMALIZATION
    }

}
