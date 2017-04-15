/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads;

import com.sorrillsoft.mapgeneration.roads.vtransforms.Translate;
import java.awt.Color;
import java.awt.Graphics;
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

    private Vertex mergeVerts(Vertex from, Vertex to) {
        int rad = 4;
        Vector newLocation = Vector.subtract(from.getLocation(), to.getLocation());
        VertexTransform ft = new Translate(newLocation,rad);
        ft.apply(from);
        ft.apply(to);
        return to;
    }

    public void addStreamline(Vector[] vs, double minDist) {
        double dist;
        System.out.println("Adding streamlines " + Arrays.toString(vs));
        Vertex[] verts = verticies.toArray(new Vertex[verticies.size()]);
        Vertex vert;
        Vector v;
        Vertex lastVert = null;
        Vertex close;
        for (int i = 0; i < vs.length; i++) {
            v = vs[i];
            vert = new Vertex(v);

            for (Vertex ver : verts) {
                dist = v.distanceTo(ver.getLocation());
                if (dist < minDist) {
                    //System.err.println(v + " is too close to " + ver.getLocation() + (dist));
                    //System.exit(0);
                    vert = mergeVerts(vert, ver);
                    break;
                }
            }
            close = vert.getClosest(verts);
            if (close != null && close.getLocation().distanceTo(vert.getLocation()) < minDist) {
                vert = close;
            } else {
                verticies.add(vert);
            }
            if (lastVert != null) {
                lastVert.addConnection(vert);
            }
            lastVert = vert;
        }
    }

    public void draw(Graphics g) {
        int r = 2;
        Vector l;
        int[] li;
        int[] lli;
        Vertex v;
        Vertex[] verts = getVerticies();
        for (int i = 0; i < verts.length; i++) {
            System.out.println("Drawing vert " + i + "/" + verts.length);
            v = verts[i];
            l = v.getLocation();
            li = l.toInt();
            g.setColor(Color.magenta);
            for (Vertex cv : v.getConnections()) {
                if (cv == null) {
                    break;
                }
                lli = cv.getLocation().toInt();
                g.drawLine(li[0], li[1], lli[0], lli[1]);
            }
            g.setColor(v.getVertColor());
            g.fillOval(li[0] - r, li[1] - r, r * 2, r * 2);
        }
    }

    public Vertex getVertex(int i) {
        return verticies.get(i);
    }
}
