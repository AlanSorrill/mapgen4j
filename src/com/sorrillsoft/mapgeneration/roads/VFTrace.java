/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads;

import java.awt.Color;
import java.awt.Graphics;
import static java.lang.Math.PI;
import java.util.ArrayList;

/**
 *
 * @author alan
 */
public class VFTrace {

    public VFTrace(VectorField field) {
        this.field = field;
    }

    public Streamline[] getTraces() {
        return traces.toArray(new Streamline[traces.size()]);
    }
    private ArrayList<Streamline> traces = new ArrayList();
    private VectorField field;

    public VectorField getField() {
        return field;
    }

    public void setField(VectorField field) {
        this.field = field;
    }
    public static final int FOREWARD = 1;
    public static final int BACKWARD = -1;
    public static final int BOTH = 0;

    public void trace(Vector location, int resolution, int direction, int length, boolean eign) {
        if (direction == BOTH) {
            trace(location.copy(), resolution, FOREWARD, length, eign);
            trace(location.copy(), resolution, BACKWARD, length, eign);
            return;
        }
        StreamlineBuilder b = new StreamlineBuilder();
        Vector sample;
        b.addToData(location.copy());
        for (int i = 0; i < length; i++) {
            sample = getField().sample(location, eign);
            if(sample.length()==0){
                if(i==0){
                    b.data.clear();
                }
                break;
            }
            if(sample.length()>1){
                sample = sample.toUnitVector();
            }
            //if (direction == BACKWARD) {
            //    sample = Vector.fromTheta(sample.getTheta() + (PI), 1);
            //}
            sample.multiply(direction);
            sample.multiply(resolution);
            location.addVector(sample);
            b.addToData(location.copy());
        }
        traces.add(b.build());
    }

    public class Streamline {

        private Vector[] data;

        private Streamline(Vector[] d) {
            data = d;
        }

        public Vector[] getData() {
            return data;
        }

    }

    public class StreamlineBuilder {

        private ArrayList<Vector> data = new ArrayList();

        public int addToData(Vector v) {
            data.add(v);
            return data.size() - 1;
        }

        public Vector getData(int i) {
            return data.get(i);
        }

        public Streamline build() {
            return new Streamline(data.toArray(new Vector[data.size()]));
        }
    }
}
