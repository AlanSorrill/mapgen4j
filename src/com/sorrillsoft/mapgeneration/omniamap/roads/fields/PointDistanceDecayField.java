/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.omniamap.roads.fields;

import com.sorrillsoft.mapgeneration.omniamap.roads.Tensor;
import com.sorrillsoft.mapgeneration.omniamap.roads.TensorField;
import com.sorrillsoft.mapgeneration.omniamap.roads.Vector2;

/**
 *
 * @author alan
 */
public class PointDistanceDecayField extends TensorField {

    private TensorField _field;
    private Vector2 _center;
    private double _decay;

    public PointDistanceDecayField(TensorField field, Vector2 center, float decay) {
        _field = field;
        _center = center;
        _decay = decay;
    }

    @Override
    public Tensor sample(Vector2 position) {
        double exp = DistanceDecay(_decay, Vector2.subtract(position, _center).lengthSquared());

        Tensor sample = _field.sample(position);

        return  Tensor.multiply(exp, sample);
    }

    static float DistanceDecay(double distanceSqr, double decay) {
        return (float) Math.exp(-decay * distanceSqr);
    }

}
