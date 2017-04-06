/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import com.flowpowered.noise.NoiseQuality;
import com.flowpowered.noise.module.source.Perlin;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Alan
 */
public class TestMapPerlin extends MapGenerator {

    public BufferedImage data;

    @Override
    protected void onInit(Display d) {

    }

    @Override
    public HashMap<String, Class> getParamTypes() {
        HashMap<String, Class> map = new HashMap();
        map.put("Width", Integer.class);
        map.put("Height", Integer.class);
        map.put("Period", Double.class);
        map.put("Split", Double.class);
        map.put("HeightField", Boolean.class);

        for (Method m : Perlin.class.getMethods()) {
            if (m.getName().contains("set") && !m.getName().equalsIgnoreCase("setsourcemodule")) {
                map.put(m.getName().replace("set", ""), m.getParameterTypes()[0]);
            }
        }
        return map;
    }

    private HashMap<String, Method> getMethods(Class c) {
        HashMap<String, Method> map = new HashMap();
        for (Method m : c.getMethods()) {
            map.put(m.getName(), m);
        }
        return map;
    }
    Perlin noiseGenerator = new Perlin();

    private void updateParams() {
        period = (Double) this.getParam("Period");
        split = (Double) getParam("Split");
        HashMap<String, Method> map = getMethods(noiseGenerator.getClass());
        Method m = null;
        for (String k : this.getParamList()) {
            if (map.containsKey("set" + k)) {
                try {
                    m = map.get("set" + k);
                    System.out.println("Calling method " + m.getName());
                    m.invoke(noiseGenerator, getParam(k));
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    System.err.println("Failed to call method " + m.getName());
                    Logger.getLogger(TestMapPerlin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    private double period = 0.01;
    private double split = 255 / 2;

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
        updateParams();
        Color c;
        int val;
        double noiseVal;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isCancelled()) {
                    return;
                }
                if (this.isParamsChanged()) {
                    updateParams();
                    this.onParamsUpdated();
                    System.out.println("Updated Params mid run");
                }
                prog = (Math.round((((double) (y * width) + x) / (width * height)) * 100));
                noiseVal = (noiseGenerator.getValue(x * period, y * period, 0));
                val = (int) Math.round((noiseVal / 2) * 255);
                val = Math.min(val, 255);
                val = Math.max(val, 0);
                if ((Boolean) getParam("HeightField")) {
                    c = new Color(val, val, val);
                } else if (val > split) {
                    c = new Color(0, val, 0);
                } else {
                    c = new Color(0, 0, val);
                }
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
        Class c = this.getParamTypes().get(key);
        if (c.isAssignableFrom(NoiseQuality.class)) {
            return NoiseQuality.STANDARD;
        }
        switch (key.toLowerCase()) {
            case "width":
                return 500;
            case "height":
                return 500;
            case "split":
                return (double) 255 / 2;
            case "period":
                return 0.001;
            case "heightfield":
                return false;
            default:
                switch (getParamTypes().get(key).getName().toLowerCase()) {
                    case "integer":
                        return 1;
                    case "int":
                        return 1;
                    case "string":
                        return "";
                    case "double":
                        return "0.0d";
                    case "float":
                        return "0.0f";

                    default:
                        return null;
                }
        }
    }
    @Override
    public String getLocationDataString(int x, int y) {
        if(this.noiseGenerator!=null){
            return "Raw val: " + this.noiseGenerator.getValue(x, y, 0);
        }else{
            return "";
        }
    }

}
