/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads;

import static java.lang.Math.PI;
import java.util.ArrayList;

/**
 *
 * @author alan
 */
public abstract class VectorField {

    public ArrayList<VFPostProcess> pProcesses = new ArrayList();

    public Vector sample(Vector l, boolean eign) {
        return sample(l.getX(), l.getY(), eign);
    }

    public Vector sample(Vector l) {
        return sample(l.getX(), l.getY());
    }

    public Vector sample(double x, double y, boolean eign) {
        if (eign) {
            return sampleEign(x, y);
        } else {
            return sample(x, y);
        }
    }

    public Vector sample(double x, double y) {
        Vector v = onSample(x, y);
        for (VFPostProcess p : pProcesses) {
            v = p.process(v, x, y);
        }
        return v;
    }

    protected abstract Vector onSample(double x, double y);

    public Vector sampleEign(double x, double y) {
        Vector v = onSample(x, y);
        return Vector.fromTheta(v.getTheta() + (PI / 2), v.length());
    }

}
