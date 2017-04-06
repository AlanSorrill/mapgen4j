/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.noise;

import com.sorrillsoft.mapgeneration.Noise;
import com.sorrillsoft.mapgeneration.Point;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Alan
 */
public class VoronoiTest extends Noise {

    private BufferedImage img;
    private int type = BufferedImage.TYPE_INT_ARGB;
    private int horRefCount = 50;

    public int getHorRefCount() {
        return horRefCount;
    }

    public void setHorRefCount(int horRefCount) {
        this.horRefCount = horRefCount;
    }

    public static VoronoiTest getDefault(int w, int h) {
        VoronoiTest v = new VoronoiTest();
        v.setHorRefCount(25);
        v.setResolution(w, h);
        v.setSeed(21);
        v.setType(BufferedImage.TYPE_INT_ARGB);
        v.init();
        return v;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void init() {
        int[] i = this.getResolution();
        img = new BufferedImage(i[0], i[1], type);

    }

    @Override
    public void generate() {
        int r[] = getResolution();
        int width = r[0];
        int height = r[1];
        ArrayList<Point> points = new ArrayList();
        for (int x = 0; x < width; x += width / this.getHorRefCount()) {
            for (int y = 0; y < height; y += height / this.getHorRefCount()) {
                points.add(new Point(x + getRandomInt(0 - getHorRefCount() / 2, getHorRefCount() / 2), y + getRandomInt(0 - getHorRefCount() / 2, getHorRefCount() / 2)));
            }
        }
        Graphics g = img.getGraphics();
        //g.setColor(Color.BLACK);
        //g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        for (Point p : points) {
            g.fillRect(p.getX(), p.getY(), 5, 5);
        }
        Point[][] map = new Point[width][height];
        float[][] vMap = new float[width][height];
        Point pix = new Point(0, 0);
        int cv;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                pix.setX(x);
                pix.setY(y);
                map[x][y] = getClosestPoint(pix, points);
                vMap[x][y] = pix.getDist(map[x][y]);
                cv = Math.round(vMap[x][y] / 30);
                //img.setRGB(x, y, new Color(cv,cv,cv).getRGB());
                img.setRGB(x, y, map[x][y].getColor().getRGB());
                System.out.println("Building color " + pix);
                this.setDisplayProgress(Math.round((((x*width)+y)/width*height)*100));
            }
        }
    }

    private Point getClosestPoint(Point p, ArrayList<Point> points) {
        Point closest = points.get(0);
        for (Point pt : points) {
            if (p.getDist(pt) < p.getDist(closest)) {
                closest = pt;
            }
        }
        return closest;
    }

    @Override
    public BufferedImage getImage() {
        return img;
    }

}
