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
public class RadialField extends VectorField {

    private Vector center = Vector.Zero;
    private boolean unit = true;
    private double multiplier=1;

    public boolean isUnit() {
        return unit;
    }

    public void setUnit(boolean unit) {
        this.unit = unit;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    public RadialField(Vector c) {
        center = c;
    }

    /**
     * Get the value of center
     *
     * @return the value of center
     */
    public Vector getCenter() {
        return center;
    }

    /**
     * Set the value of center
     *
     * @param center new value of center
     */
    public void setCenter(Vector center) {
        this.center = center;
    }

    @Override
    public Vector onSample(double x, double y) {
        Vector l = Vector.subtract(new Vector(x, y),center);
        
        if (unit) {
            l= l.toUnitVector();
        }
        return Vector.multiply(l, getMultiplier());
    }

}
