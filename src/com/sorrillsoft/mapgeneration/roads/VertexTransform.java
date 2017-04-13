/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads;

import java.util.ArrayList;

/**
 *
 * @author alan
 */
public abstract class VertexTransform {

    public void apply(Vertex sub) {
        ArrayList<Integer> al = new ArrayList();
        al.add(sub.getInstanceId());
        apply(sub, sub.getLocation(), al,0);
    }

    public void apply(Vertex sub, Vector origin, ArrayList<Integer> done, int nodeDist) {
        for (Vertex v : sub.getConnections()) {
            if (!done.contains(v.getInstanceId())) {
                done.add(v.getInstanceId());
                apply(v, origin, done, nodeDist+1);

            }
        }
        transform(sub, origin,nodeDist);
    }

    protected abstract void transform(Vertex subject, Vector original, int nodeDist);

    public boolean isInRange(Vertex sub, Vector origin) {
        return true;
    }
    public abstract class Function{
        public abstract double eval(double x);
    }

}
