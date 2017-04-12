/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author alan
 */
public class Network {

    private ArrayList<Vertex> verticies = new ArrayList();

    public Vertex[] getVerticies() {
        return verticies.toArray(new Vertex[verticies.size()]);
    }

    public void addStreamline(Vector[] vs, double minDist) {
        double dist;
        System.out.println("Adding streamlines " + Arrays.toString(vs));
        Vertex[] verts = verticies.toArray(new Vertex[verticies.size()]);
        Vertex vert;
        Vector v;
        Vertex lastVert = null;
        for (int i = 0; i < vs.length; i++) {
            v = vs[i];
            vert = null;
            for (Vertex ver : verts) {
                dist = v.distanceTo(ver.getLocation());
                if (dist < minDist) {
                    System.err.println(v + " is too close to " + ver.getLocation() + (dist));
                    //System.exit(0);
                    vert = ver;
                    break;
                }
            }
            if (vert == null) {
                vert = new Vertex(v);

                verticies.add(vert);
            }
            if (lastVert != null) {
                lastVert.addConnection(vert);
            }
            lastVert = vert;
        }
    }
}
