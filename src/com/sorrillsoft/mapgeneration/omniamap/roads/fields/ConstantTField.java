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
public class ConstantTField extends TensorField{
    private Tensor constant;
    public ConstantTField(Tensor t) {
        constant = t;
    }

    @Override
    public Tensor sample(Vector2 pos) {
        return constant;
    }
    
}
