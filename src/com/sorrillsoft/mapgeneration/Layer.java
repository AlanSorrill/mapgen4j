/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import java.awt.Color;

/**
 *
 * @author alan
 */
public abstract class Layer<T> {
    public abstract void init(int seed);
    public abstract T getValue(int x, int y);
    public abstract Color getDisplayColor(int x, int y);
    public String toString(int x, int y){
        return this.getClass().getSimpleName() + " = " + getValue(x,y);
    }
}
