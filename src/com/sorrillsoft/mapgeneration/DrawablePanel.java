/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Alan
 */
public class DrawablePanel extends JPanel {

    private int[] size = new int[]{-1, -1};

    public void setSiz(int x, int y) {
        size = new int[]{x, y};
    }

    public int[] getSiz() {
        try {
            int[] o = new int[2];
            if (size[0] == -1) {
                o[0] = getBackgroundImage().getWidth();
            } else {
                o[0] = size[0];
            }
            if (size[1] == -1) {
                o[1] = getBackgroundImage().getHeight();
            } else {
                o[1] = size[1];
            }
            return o;
        } catch (NullPointerException npe) {
            return new int[]{0, 0};
        }
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getSiz()[0], getSiz()[1], null);
        }
    }
    private BufferedImage backgroundImage;

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(BufferedImage background) {
        this.backgroundImage = background;
        
        setSiz(getWidth(), getHeight());
        repaint();
    }
}
