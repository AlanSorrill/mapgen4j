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
public class TangentTField extends TensorField {

    private Vector2 location;

    public TangentTField(Vector2 location) {
        this.location = location;
    }

    @Override
    public Tensor sample(Vector2 pos) {
        Vector2 perp = Vector2.subtract(pos, location).getPerpVector();
        return Tensor.FromRTheta(1, perp.getTheta());
    }

}
