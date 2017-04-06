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
public class Vector2 {

    public static Vector2 Zero = new Vector2(0, 0);

    public static Vector2 multiply(Vector2 v, int m) {
        return new Vector2(v.x * m, v.y * m);
    }

    public static Vector2 fromTheta(double theta, double h) {
        return new Vector2(Math.cos(theta) * h, Math.sin(theta) * h);
    }

    public static Vector2 add(Vector2 a, Vector2 b) {
        //System.out.println("adding " + a + " and " + b);
        return new Vector2(a.x + b.x, a.y + b.y);
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

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getTheta() {
        return Math.atan(y / x);
    }

    public static Vector2 subtract(Vector2 a, Vector2 b) {
        return new Vector2(a.x - b.x, a.y - b.y);
    }

    @Override
    public String toString() {
        return "Vector2{" + "x=" + x + ", y=" + y + '}';
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
        final Vector2 other = (Vector2) obj;
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

    public Vector2 getPerpVector() {
        return new Vector2(0-getY(),getX());
    }

}
