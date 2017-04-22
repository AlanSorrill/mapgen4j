/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.roads.fields.scalar;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Alan
 */
public class ImageScalarField extends ScalarField {

    public ImageScalarField(BufferedImage img) {
        this.image = img;
    }
    private BufferedImage image;

    @Override
    public double sample(double x, double y) {
        Color c = new Color(image.getRGB((int) x, (int) y));
        return (c.getRed() + c.getGreen() + c.getBlue()) / 3d;
    }

}
