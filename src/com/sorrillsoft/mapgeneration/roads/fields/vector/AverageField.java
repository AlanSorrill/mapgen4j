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
public class AverageField extends VectorField {

    private ArrayList<VectorField> fields = new ArrayList();

    public AverageField() {
    }

    @Override
    public Vector onSample(double x, double y) {
        Vector v = new Vector(0, 0);
        for (VectorField f : fields) {
            v = Vector.add(v, f.sample(x, y));
        }

        return Vector.divide(v, fields.size());
    }

    public void addField(VectorField constantField) {
        fields.add(constantField);
    }

}
