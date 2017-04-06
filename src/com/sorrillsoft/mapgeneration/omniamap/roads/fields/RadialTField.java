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
public class RadialTField extends TensorField{
    private Vector2 center;

    public RadialTField(Vector2 center) {
        this.center = center;
    }
    
    @Override
    public Tensor sample(Vector2 pos) {
        return Tensor.FromXY(Vector2.subtract(pos, center));
    }
    
}
