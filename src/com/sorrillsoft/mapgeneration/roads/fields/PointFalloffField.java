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
public class PointFalloffField extends VectorField {

    private Vector center = Vector.Zero;
    private double radius;
    private double max = 1;

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double falloff) {
        this.radius = falloff;
    }

    public PointFalloffField(Vector c, double radius) {
        center = c;
        this.radius = radius;
    }
    private boolean zeroClamp = true;

    public boolean isZeroClamp() {
        return zeroClamp;
    }

    public void setZeroClamp(boolean zeroClamp) {
        this.zeroClamp = zeroClamp;
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
        double dist = Vector.subtract(new Vector(x, y), center).length();
        double val = dist / radius;
        if ((val > 1) && zeroClamp) {
            val = 1;
        }
        double ret = max - max * val;
        return new Vector(ret, ret);
    }

}
