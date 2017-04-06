/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import java.awt.Color;

/**
 *
 * @author Alan
 */
public class Point {

    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getDist(Point b) {
        return (int) Math.round(Math.sqrt(b.getX() - getX() + b.getY() - getY()));
    }
    
    private Color color;
    
    public void setRandomColor(){
        setColor(new Color(Noise.getRandomStaticInt(0, 255),Noise.getRandomStaticInt(0, 255),Noise.getRandomStaticInt(0, 255)));
    }
    

    public Color getColor() {
        if(color==null){
            setRandomColor();
        }
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
