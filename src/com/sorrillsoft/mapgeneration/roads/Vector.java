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
public class Vector implements Closest<Vector> {

    public static Vector Zero = new Vector(0, 0);

    public int[] toInt() {
        return new int[]{(int) Math.round(x), (int) Math.round(y)};
    }

    public static Vector random(double xb, double yb) {
        return new Vector(Math.random() * xb, Math.random() * yb);
    }

    public void multiply(double m) {
        setX(getX() * m);
        setY(getY() * m);
    }

    public static Vector multiply(Vector v, double m) {
        return new Vector(v.x * m, v.y * m);
    }

    public static Vector multiply(Vector v, Vector m) {
        return new Vector(v.x * m.x, v.y * m.y);
    }

    public static Vector fromTheta(double theta, double h) {
        return new Vector(Math.cos(theta) * h, Math.sin(theta) * h);
    }

    public static Vector add(Vector a, Vector b) {
        //System.out.println("adding " + a + " and " + b);
        return new Vector(a.x + b.x, a.y + b.y);
    }

    public static Vector divide(Vector v, int size) {
        return new Vector(v.x / size, v.y / size);
    }

    public double distanceTo(Vector other) {
        return Math.sqrt(Math.pow(other.x - x, 2) + Math.pow(other.y - y, 2));
    }

    public void addVector(Vector ov) {
        setX(x + ov.x);
        setY(y + ov.y);
    }
    private double x;
    private double y;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getTheta() {
        return Math.atan(y / x);
    }

    public static Vector subtract(Vector a, Vector b) {
        return new Vector(a.x - b.x, a.y - b.y);
    }

    @Override
    public String toString() {
        return "Vector2{" + "x=" + x + ", y=" + y + '}';
    }

    public Vector toUnitVector() {
        return new Vector(x / this.length(), y / length());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 23 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
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
        final Vector other = (Vector) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }

    public double length() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public double lengthSquared() {
        return Math.pow(length(), 2);
    }

    public Vector getPerpVector() {
        return new Vector(0 - getY(), getX());
    }

    public Vector copy() {
        return new Vector(x, y);
    }

    @Override
    public Vector getClosest(Vector[] verts) {
        Vector c = verts[0];
        double dist = distanceTo(c);
        for (Vector v : verts) {
            if (distanceTo(v) < dist) {
                c = v;
            }
        }
        return c;
    }

}
