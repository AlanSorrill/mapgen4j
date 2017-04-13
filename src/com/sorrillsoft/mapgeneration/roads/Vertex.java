/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author alan
 */
public class Vertex implements Closest<Vertex> {

    private static int instanceCount = 0;

    public Vertex(Vector v) {
        location = v;
        instanceId = instanceCount++;
    }
    private int instanceId;
    private Vector location;
    private ArrayList<Vertex> connections = new ArrayList();

    public int getInstanceId() {
        return instanceId;
    }

    public void addConnection(Vertex v) {
        if (v.equals(this)) {
            return;
        }
        connections.add(v);
        if (!v.isConnectedTo(this)) {
            v.addConnection(this);
        }
    }

    public Vertex[] getConnections() {
        return connections.toArray(new Vertex[connections.size()]);
    }

    public int getConnectionCount() {
        return connections.size();
    }

    public Vector getLocation() {
        return location;
    }

    public void setLocation(Vector location) {
        this.location = location;
    }

    public Color getVertColor() {
        switch (((connections.size() >= 0) ? connections.size() : -1)) {
            case -1:
                return Color.BLACK;
            case 0:
                return Color.RED;
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.GREEN;
            case 3:
                return Color.BLUE;
            case 4:
                return Color.lightGray;
            default:
                return Color.WHITE;
        }
    }

    public boolean isConnectedTo(Vertex other) {
        for (Vertex v : connections) {
            if (v == null) {
                return false;
            }
            if (v.getInstanceId() == other.getInstanceId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.instanceId;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vertex other = (Vertex) obj;
        if (this.instanceId != other.instanceId) {
            return false;
        }
        return true;
    }

    @Override
    public Vertex getClosest(Vertex[] verts) {
        try {
            Vertex c = verts[0];
            double dist = getLocation().distanceTo(c.getLocation());
            for (Vertex v : verts) {
                if (getLocation().distanceTo(v.getLocation()) < dist) {
                    c = v;
                }
            }
            return c;
        } catch (ArrayIndexOutOfBoundsException aaiobe) {
            return null;
        }
    }
    

}
