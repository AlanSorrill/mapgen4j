/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.omniamap;

import com.sorrillsoft.mapgeneration.Display;
import com.sorrillsoft.mapgeneration.Layer;
import com.sorrillsoft.mapgeneration.MapGenerator;
import com.sorrillsoft.mapgeneration.MappedArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author alan
 */
public class Omnia extends MapGenerator {

    MappedArrayList<Layer> layers = new MappedArrayList();

    @Override
    protected void onInit(Display d) {
        int seed = 1;
        layers.add(new Elevation());
        layers.add(new BiomTemp());

        for (Layer l : layers) {
            l.init(seed);
        }
    }

    @Override
    public HashMap<String, Class> getParamTypes() {
        HashMap map = new HashMap();
        return map;
    }

    @Override
    public Object getDefaultParam(String key) {
        return null;
    }

    @Override
    public void generate() {

        Layer[] layers = this.layers.toArray(new Layer[this.layers.size()]);
        int width = 500, height = 500;
        data = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                prog = (Math.round((((double) (y * width) + x) / (width * height)) * 100));
                for (Layer l : layers) {
                    addColorToData(x, y, l.getDisplayColor(x, y));
                    // data.setRGB(x, y, l.getDisplayColor(x, y).getRGB());
                }
                notifyUpdate();
            }
        }

    }
    private BufferedImage def;
    private Color odc;
    private int cr, cg, cb;

    //  c         c+x
    // ------ = ---------
    //  255        510
    //255cx+x)=510c
    //255x+255c=510c;
    //255x=510c-255c
    //x=
    private void addColorToData(int x, int y, Color c) {
        odc = new Color(data.getRGB(x, y));
        cr = addColorData(odc.getRed(), (int) Math.round(c.getRed() * ((double) c.getAlpha() / 255)));
        cg = addColorData(odc.getGreen(), (int) Math.round(c.getGreen() * ((double) c.getAlpha() / 255)));
        cb = addColorData(odc.getBlue(), (int) Math.round(c.getBlue() * ((double) c.getAlpha() / 255)));
        data.setRGB(x, y, new Color(cr, cg, cb).getRGB());
    }

    private int addColorData(int base, int add) {
        return (int) Math.round((((double) base / 255) + ((double) add / 255)) * 255);
    }

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
    private BufferedImage data = null;
    private float prog = 0f;
    private String status = "Not Initialized";

    @Override
    public BufferedImage getDisplayImage() {
        if (data != null) {
            return data;
        } else {
            return getDefaultImage();
        }
    }

    @Override
    public float getProgressPercent() {
        return prog;
    }

    @Override
    public String getStatusText() {
        return status;
    }

    @Override
    public String getLocationDataString(int x, int y) {
        StringBuilder sb = new StringBuilder();
        for(Layer l:layers){
            sb.append(l.toString(x,y)).append("\n");
        }
        return sb.toString();
    }

}
