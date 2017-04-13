/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads;

/**
 *
 * @author alan
 */
public interface Closest<T> {
    public T getClosest(T[] verts);
}
