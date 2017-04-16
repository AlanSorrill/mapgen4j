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
            //g.drawImage(backgroundImage, 0, 0, getSiz()[0], getSiz()[1], null);
            zoom.draw(g);
        }
    }
    private ZoomManager zoom;
    private BufferedImage backgroundImage;

    public ZoomManager getZoomManager() {
        return zoom;
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    public void setBackgroundImage(BufferedImage background) {
        this.backgroundImage = background;
        if (zoom == null) {
            zoom = new ZoomManager(background, new int[]{getWidth(), getHeight()});
        }
        zoom.setImage(backgroundImage);
        setSiz(getWidth(), getHeight());
        repaint();
    }

    public class ZoomManager {

        private BufferedImage img;
        private int x, y, w, h;
        private int[] viewSize;

        public ZoomManager(BufferedImage i, int[] viewSize) {
            img = i;
            this.viewSize = viewSize;
            x = 0;
            y = 0;
            w = img.getWidth();
            h = img.getHeight();
        }

        public void setImage(BufferedImage i) {
            if (img.getWidth() != i.getWidth() || img.getHeight() != i.getHeight()) {
                w = i.getWidth();
                h = i.getHeight();
                x = 0;
                y = 0;
            }
            img = i;
        }

        public void draw(Graphics g) {
            g.drawImage(img, x, y, w, h, null);
        }

        public void move(int xm, int ym) {
            System.out.println("Moving port " + xm + ", " + ym);
            x -= xm;
            y -= ym;
            checkBounds();
        }

        private void checkBounds() {
            if (x > 0) {
            System.out.println("X>0!");
                x = 0;
            }
            if (y > 0) {
            System.out.println("X>0!");
                y = 0;
            }
            if (x + w < viewSize[0]) {
            System.out.println("X<"+viewSize[0]);
                x = viewSize[0] - w;
            }
            if (y + w < viewSize[1]) {
            System.out.println("y<"+viewSize[1]);
                y = viewSize[0] - h;
            }
        }

        public void zoomIn(double percent) {
            System.out.println("Zooming in " + percent + "%");
            percent = percent / 100;
            w += (int) Math.round((percent) * img.getWidth());
            h += (int) Math.round((percent) * img.getHeight());
            if (w < img.getWidth()) {
                w = img.getWidth();
            }
            if (h < img.getHeight()) {
                h = img.getHeight();
            }
            checkBounds();
        }
    }
}
