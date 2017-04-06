/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.omniamap.roads;

/**
 *
 * @author alan
 */
public abstract class TensorField {
    public abstract Tensor sample(Vector2 pos);
    
}
