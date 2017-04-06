/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import com.sorrillsoft.mapgeneration.omniamap.roads.Tensor;
import com.sorrillsoft.mapgeneration.omniamap.roads.TensorField;
import com.sorrillsoft.mapgeneration.omniamap.roads.Vector2;
import com.sorrillsoft.mapgeneration.omniamap.roads.fields.AdditionTField;
import com.sorrillsoft.mapgeneration.omniamap.roads.fields.RadialTField;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author Alan
 */
public class TensorTestMap extends MapGenerator {

    public BufferedImage data;

    @Override
    protected void onInit(Display d) {
        AdditionTField af = new AdditionTField();
        //WeightedAverageTField wf = new WeightedAverageTField();
        //wf.blend(new ConstantTField(Tensor.FromRTheta(1,Math.PI/3)),5);
        af.fields.add(new RadialTField(new Vector2(10, 10)));
        //af.fields.add(new TangentTField(new Vector2(200, 200)));
        field = af;

    }
    private TensorField field;

    @Override
    public HashMap<String, Class> getParamTypes() {
        HashMap<String, Class> map = new HashMap();
        map.put("Width", Integer.class);
        map.put("Height", Integer.class);
        map.put("sampleDist", Integer.class);
        map.put("dVLength", Integer.class);
        return map;
    }

    @Override
    public void generate() {
        status = "collecting params";
        final int sampleDist = (Integer) getParam("sampleDist");
        final int width = (Integer) this.getParam("Width");
        final int height = (Integer) this.getParam("Height");
        int dVLength = (Integer) this.getParam("dVLength");
        status = "Creating data buffer";
        data = new BufferedImage(width + width * sampleDist, height + height * sampleDist, BufferedImage.TYPE_INT_ARGB);
        Graphics g = data.getGraphics();
        status = "Formatting blank data buffer";
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width + width * sampleDist, height + height * sampleDist);
        this.getDisplay().dPanel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                drawTrace(new Vector2(Math.random() * width, Math.random() * height), width, height, sampleDist, 1000);
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        drawVectors(g);
    }

    private void drawTrace(Vector2 start, int width, int height, int sampleDist, int max) {
        System.out.println("Tracing at " + start);
        Vector2 base = new Vector2(width * sampleDist / 2, height * sampleDist / 2);
        Vector2 trace = new Vector2(width * sampleDist / 2, height * sampleDist / 2);
        Rectangle bounds = new Rectangle(0, 0, width * sampleDist, height * sampleDist);
        Color c = Color.RED;
        int i = 0;
        while (bounds.contains(trace.getX(), trace.getY())) {
            if (isCancelled() || (i++ > max)) {
                break;
            }
            //prog = (int) ((i/t) * 100);
            data.setRGB((int) trace.getX(), (int) trace.getY(), c.getRGB());
            trace = Vector2.add(trace, field.sample(trace).EigenVector(Tensor.MAJOR));
            System.out.println(trace);
        }
    }

    private void drawVectors(Graphics g) {
        int sampleDist = (Integer) getParam("sampleDist");
        int width = (Integer) this.getParam("Width");
        int height = (Integer) this.getParam("Height");
        int dVLength = (Integer) this.getParam("dVLength");
        Tensor s;
        Color c;
        double vald;
        int val;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (isCancelled()) {
                    return;
                }
                prog = (Math.round((((double) (y * width) + x) / (width * height)) * 100));
                s = field.sample(new Vector2(x, y));
                s = Tensor.normalize(s);
                drawArrow(g, x * sampleDist, y * sampleDist, Vector2.multiply(s.EigenVector(Tensor.MAJOR), dVLength), Color.DARK_GRAY);
                drawArrow(g, x * sampleDist, y * sampleDist, Vector2.multiply(s.EigenVector(Tensor.MINOR), dVLength), Color.LIGHT_GRAY);

                //vald = (s.EigenVector(Tensor.MAJOR).length() / 2);
                //System.out.println(vald);
                //val = (int) Math.round(vald * 255);
                //c = new Color(val, val, val);
                //data.setRGB(x * sampleDist, y * sampleDist, c.getRGB());
                notifyUpdate();
            }
        }
    }
    private double arrowArmLength = 1 / 5;

    private void drawArrow(Graphics g, int x, int y, Vector2 val, Color c) {
        g.setColor(c);
        int tx = (int) Math.round(x + val.getX());
        int ty = (int) Math.round(y + val.getY());
        int tlx, tly, trx, trw;
        double t = val.getTheta();
        t = Math.PI * 2 - t;
        double armT = Math.PI / 6;
        Vector2 l = Vector2.fromTheta(t + armT, val.length() * arrowArmLength);
        tlx = (int) Math.round(tx + l.getX());
        tly = (int) Math.round(ty + l.getY());
        Vector2 r = Vector2.fromTheta(t - armT, val.length() * arrowArmLength);
        trx = (int) Math.round(tx + r.getX());
        trw = (int) Math.round(ty + r.getY());
        g.drawLine(x, y, tx, ty);

        //g.drawLine(tx, ty, trx, trw);
        //g.drawLine(tx, ty, tlx, tly);
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
            case "sampleDist":
                return (Integer) 10;
            case "dVLength":
                return (Integer) 5;
        }
        return null;
    }

    @Override
    public String getLocationDataString(int x, int y) {
        if (field != null) {
            Tensor t = field.sample(new Vector2(x, y));
            return "Raw val: " + t;
        } else {
            return "";
        }
    }

}
