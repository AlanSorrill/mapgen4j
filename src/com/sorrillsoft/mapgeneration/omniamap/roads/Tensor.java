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
public class Tensor {

    public static final Tensor ZERO = new Tensor(0, 0);

    public Tensor(double a, double b) {
        A = a;
        B = b;
    }

    private double A = 0;
    private double B = 0;

    /**
     * Get the value of A
     *
     * @return the value of A
     */
    public double getA() {
        return A;
    }

    /**
     * Get the value of B
     *
     * @return the value of B
     */
    public double getB() {
        return B;
    }

    /**
     * Set the value of A
     *
     * @param A new value of A
     */
    public void setA(double A) {
        this.A = A;
    }

    /**
     * Set the value of B
     *
     * @param B new value of B
     */
    public void setB(double B) {
        this.B = B;
    }

    @Override
    public String toString() {
        return "Tensor{" + "A=" + A + ", B=" + B + '}';
    }

    public static Tensor normalize(Tensor t) {
        return new Tensor(1 / t.getA(), 1 / t.getB());
    }

    public static Tensor FromRTheta(double r, double theta) {
        return new Tensor(r * Math.cos(2 * theta), r * Math.sin(2 * theta));
    }

    public static Tensor FromXY(Vector2 xy) {
        double xy2 = -2 * xy.getX() * xy.getY();
        double diffSquares = xy.getY() * xy.getY() - xy.getY() * xy.getY();
        return normalize(new Tensor(diffSquares, xy2));
    }

    public static Tensor add(Tensor left, Tensor right) {
        return new Tensor(left.A + right.A, left.B + right.B);
    }

    public static Tensor multiply(double left, Tensor right) {
        return new Tensor(left * right.A, left * right.B);
    }
    public static final int MAJOR = 1;
    public static final int MINOR = 0;

    public Vector2 EigenVector(int type) {
        Vector2 major;
        Vector2 minor;
        if (Math.abs(B) < 0.0000001f) {
            if (Math.abs(A) < 0.0000001f) {
                major = Vector2.Zero;
                minor = Vector2.Zero;
            } else {
                major = new Vector2(1, 0);
                minor = new Vector2(0, 1);
            }
        } else {
            double e[] = EigenValues();

            major = new Vector2((float) B, (float) (e[0] - A));
            minor = new Vector2((float) B, (float) (e[1] - A));
        }
        if (type == MAJOR) {
            return major;
        } else {
            return minor;
        }
    }

    public double[] EigenValues() {
        double eval = Math.sqrt(A * A + B * B);

        double e1 = eval;
        double e2 = -eval;
        return new double[]{e1, e2};
    }

}
