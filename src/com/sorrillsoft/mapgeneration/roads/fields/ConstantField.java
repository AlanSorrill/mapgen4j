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
public class ConstantField extends VectorField {

    private Vector cVect = null;

    public ConstantField(Vector v) {
        setcVect(v);
    }

    /**
     * Get the value of cVect
     *
     * @return the value of cVect
     */
    public Vector getcVect() {
        return cVect;
    }

    /**
     * Set the value of cVect
     *
     * @param cVect new value of cVect
     */
    public void setcVect(Vector cVect) {
        this.cVect = cVect;
    }

    @Override
    public String toString() {
        return "Constant{" + "cVect=" + cVect + '}';
    }

    @Override
    public Vector onSample(double x, double y) {
        return cVect;
    }

}
