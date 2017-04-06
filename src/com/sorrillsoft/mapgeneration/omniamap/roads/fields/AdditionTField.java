/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.omniamap.roads.fields;

import com.sorrillsoft.mapgeneration.omniamap.roads.Tensor;
import com.sorrillsoft.mapgeneration.omniamap.roads.TensorField;
import com.sorrillsoft.mapgeneration.omniamap.roads.Vector2;
import java.util.ArrayList;

/**
 *
 * @author alan
 */
public class AdditionTField extends TensorField {

    public ArrayList<TensorField> fields = new ArrayList();
    private TensorField[] tf;

    @Override
    public Tensor sample(Vector2 pos) {
        Tensor ret = new Tensor(0, 0);
        if (tf == null || tf.length != fields.size()) {
            tf = new TensorField[fields.size()];
        }
        for (TensorField f : fields.toArray(tf)) {
            ret = Tensor.add(ret, f.sample(pos));
        }
        return ret;
    }

}
