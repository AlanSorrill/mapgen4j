/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import com.flowpowered.noise.module.source.Voronoi;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;

/**
 *
 * @author Alan
 */
public class TestMap02 extends MapGenerator {

    public BufferedImage data;

    @Override
    protected void onInit(Display d) {

    }

    @Override
    public HashMap<String, Class> getParamTypes() {
        HashMap<String, Class> map = new HashMap();
        map.put("Width", Integer.class);
        map.put("Height", Integer.class);
        map.put("split", Double.class);
        map.put("period", Double.class);
        return map;
    }
    Voronoi v;

    @Override
    public void generate() {
        status = "collecting params";
        int width = (Integer) this.getParam("Width");
        int height = (Integer) this.getParam("Height");
        status = "Creating data buffer";
        data = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = data.getGraphics();
        status = "Formatting blank data buffer";
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        v = new Voronoi();
        v.setEnableDistance(true);
        v.setFrequency(100);
        double period = (Double) getParam("period");
        double xPeriod = period, yPeriod = period;
        Color c;
        int val;
        double noiseVal;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isCancelled()) {
                    return;
                }
                prog = (Math.round((((double) (y * width) + x) / (width * height)) * 100));
                noiseVal = (v.getValue(x * xPeriod, y * yPeriod, 0));
                if (noiseVal / 2 < ((Double) getParam("split"))) {
                    noiseVal = 0;
                } else {
                    noiseVal = 2;
                }
                val = (int) Math.round((noiseVal / 2) * 255);
                c = new Color(val, val, val);
                data.setRGB(x, y, c.getRGB());
                notifyUpdate();
            }
        }

        this.notifyUpdate();
    }

    private BufferedImage def;

    private BufferedImage getDefaultImage() {
        if (def == null) {
            def = new BufferedImage(100, 100, BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = def.getGraphics();
            g.setColor(Color.black);
            g.fillRect(0, 0, 100, 100);
            g.setColor(Color.white);
            g.drawString("Not initialized", 0, 100);
        }
        return def;
    }

    @Override
    public BufferedImage getDisplayImage() {
        if (data != null) {
            return data;
        } else {
            return getDefaultImage();
        }
    }
    private float prog = 0;

    @Override
    public float getProgressPercent() {
        return prog;
    }

    @Override
    public String getStatusText() {
        return status;
    }
    private String status = "Not Initialized";

    @Override
    public Object getDefaultParam(String key) {
        switch (key.toLowerCase()) {
            case "width":
                return 500;
            case "height":
                return 500;
            case "split":
                return 0.5;
            case "period":
                return 0.001;
        }
        return null;
    }

    @Override
    public String getLocationDataString(int x, int y) {
        if (v != null) {
            return "Raw val: " + v.getValue(x, y, 0);
        } else {
            return "";
        }
    }

}
