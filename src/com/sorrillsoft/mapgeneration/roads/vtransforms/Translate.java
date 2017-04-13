/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.vtransforms;

import com.sorrillsoft.mapgeneration.roads.Vector;
import com.sorrillsoft.mapgeneration.roads.Vertex;
import com.sorrillsoft.mapgeneration.roads.VertexTransform;

/**
 *
 * @author alan
 */
public class Translate extends VertexTransform {

    public Translate(Vector trans, int radius) {
        this.radius = radius;
        this.trans = trans;
        funct = new Function() {

            @Override
            public double eval(double x) {
                return 1 - Math.pow(x, 3);
            }
        };
    }
    private Vector trans;
    private int radius;
    private Function funct;

    @Override
    protected void transform(Vertex sub, Vector origin, int nodeDist) {
        double strength = (double) nodeDist / radius;
        if (strength >= 1) {
            strength = 1;
        }
        strength = funct.eval(strength); //f(x)=1-x^3
        
        //System.out.println("Strength " + strength);
        
        //Out = Sub + (trans*strength)
        Vector out = Vector.add(sub.getLocation(), Vector.multiply(trans, strength));
        sub.setLocation(out);
    }

    @Override
    public boolean isInRange(Vertex sub, Vector origin) {
        return origin.distanceTo(sub.getLocation()) <= radius;
    }

}
