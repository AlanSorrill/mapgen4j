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
import java.util.HashMap;

/**
 *
 * @author Alan
 */
public class TestMap01 extends MapGenerator {

    public BufferedImage data;

    @Override
    protected void onInit(Display d) {

    }

    @Override
    public HashMap<String, Class> getParamTypes() {
        HashMap<String, Class> map = new HashMap();
        map.put("Width", Integer.class);
        map.put("Height", Integer.class);
        map.put("TransversalScale", Double.class);
        map.put("valMax", Double.class);
        map.put("hrc", Integer.class);
        map.put("renderskip", Integer.class);
        map.put("frequency", Integer.class);
        return map;
    }
    Voronoi v;

    @Override
    public void generate() {
        int width = (Integer) this.getParam("Width");
        int height = (Integer) this.getParam("Height");
        data = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = data.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        prog = 1;
        this.notifyUpdate();
        g.setColor(Color.WHITE);
//        Voronoi v = new Voronoi();
//        v.setResolution(width, height);
//        v.setHorRefCount((int)this.getParam("hrc"));
//        v.init();
//        v.generate();
        v = new Voronoi();

        v.setEnableDistance(true);
        v.setFrequency((Integer) getParam("frequency"));

        double d;
        double scale = (Double) getParam("TransversalScale");
        double max = (Double) getParam("valMax");
        int rSkip = (Integer) getParam("renderskip");
        for (int y = 0; y < height; y += rSkip) {
            for (int x = 0; x < width; x += rSkip) {
                prog = (Math.round((((double) (y * width) + x) / (width * height)) * 100));
                d = v.getValue(x * 0.0001, y * 0.0001, 0);
                System.out.println(d);

                g.setColor(new Color(0, 0, 255 - Math.min((int) Math.round(getDecimalPercent(0, 1, d) * 255), 255)));
                g.fillRect(x, y, rSkip, rSkip);
                notifyUpdate();
                if (isCancelled()) {
                    return;
                }
            }
        }
        //g.drawImage(v.getImage(), 0, 0, null);
        this.notifyUpdate();
    }

    private double getDecimalPercent(double min, double max, double value) {
        value = value - min;
        max = max - min;
        return (min + (value / max));
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
        return "Not Initialized";
    }

    @Override
    public Object getDefaultParam(String key) {
        switch (key.toLowerCase()) {
            case "width":
                return 500;
            case "height":
                return 500;
            case "transversalscale":
                return 0.001d;
            case "hrc":
                return 10;
            case "valmax":
                return 10d;
            case "renderskip":
                return 1;
            case "frequency":
                return 100;
        }
        return null;
    }

    @Override
    public String getLocationDataString(int x, int y) {
        if(v!=null){
            return "Raw val: " + v.getValue(x, y, 0);
        }else{
            return "";
        }
    }

}
