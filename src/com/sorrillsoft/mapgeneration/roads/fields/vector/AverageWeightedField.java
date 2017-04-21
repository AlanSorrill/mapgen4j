/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.fields.vector;


import com.sorrillsoft.mapgeneration.roads.Vector;
import com.sorrillsoft.mapgeneration.roads.VectorField;
import java.util.ArrayList;

/**
 *
 * @author alan
 */
public class AverageWeightedField extends VectorField {

    private ArrayList<VectorField> fields = new ArrayList();
    private ArrayList<Double> weights = new ArrayList();
    private double totalWeight = 0;

    public AverageWeightedField() {
    }

    @Override
    public Vector onSample(double x, double y) {
        Vector v = new Vector(0, 0);
        VectorField f;
        for (int i = 0; i < fields.size(); i++) {
            f = fields.get(i);
            v = Vector.add(v, f.sample(x, y));
            v = Vector.add(v, v.multiply(f.sample(x, y), (weights.get(i) / totalWeight)));
        }

        return v;
    }

    public void addField(VectorField constantField, double weight) {
        fields.add(constantField);
        weights.add(weight);
        totalWeight += weight;
    }

}
