/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.omniamap.roads.fields;

import com.sorrillsoft.mapgeneration.omniamap.roads.Tensor;
import com.sorrillsoft.mapgeneration.omniamap.roads.TensorField;
import com.sorrillsoft.mapgeneration.omniamap.roads.Vector2;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author alan
 */
public class WeightedAverageTField extends TensorField {

    private double totalWeight = 0;
    private HashMap<TensorField, Double> fields = new HashMap();

    public void blend(TensorField field, double weight) {
        totalWeight += weight;
        fields.put(field, weight);

    }

    @Override
    public Tensor sample(Vector2 pos) {
        Tensor result = new Tensor(0, 0);
        Iterator<TensorField> i = fields.keySet().iterator();
        TensorField f;

        while (i.hasNext()) {
            f = i.next();
            result = Tensor.add(result, Tensor.multiply((fields.get(f) / totalWeight), f.sample(pos)));
        }

        return result;
    }

}
