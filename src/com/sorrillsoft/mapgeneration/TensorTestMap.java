/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import com.sorrillsoft.mapgeneration.roads.Network;
import com.sorrillsoft.mapgeneration.roads.VFTrace;
import com.sorrillsoft.mapgeneration.roads.VFTrace.Streamline;
import com.sorrillsoft.mapgeneration.roads.Vector;
import com.sorrillsoft.mapgeneration.roads.Vertex;
import com.sorrillsoft.mapgeneration.roads.VertexTransform;
import com.sorrillsoft.mapgeneration.roads.fields.vector.AverageWeightedField;
import com.sorrillsoft.mapgeneration.roads.fields.vector.ConstantField;
import com.sorrillsoft.mapgeneration.roads.fields.vector.PerlinField;
import com.sorrillsoft.mapgeneration.roads.vtransforms.Translate;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
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
        field = new AverageWeightedField();

    }
    AverageWeightedField field;

    @Override
    public HashMap<String, Class> getParamTypes() {
        HashMap<String, Class> map = new HashMap();
        map.put("Width", Integer.class);
        map.put("Height", Integer.class);
        map.put("subDev", Integer.class);
        map.put("dVLength", Integer.class);
        map.put("streamCount", Integer.class);
        map.put("vertFrequency", Integer.class);
        map.put("paintField", Boolean.class);
        return map;
    }
    private int pw = -1;
    private int ph = -1;

    private int getWidth() {
        if (pw == -1) {
            pw = (Integer) this.getParam("Width");
        }
        return pw;
    }

    private int getHeight() {
        if (ph == -1) {
            ph = (Integer) this.getParam("Height");
        }
        return ph;
    }

    @Override
    public void generate() {
        status = "collecting params";
        final int width = getWidth();
        final int height = getHeight();
        status = "Creating data buffer";
        data = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = data.getGraphics();
        status = "Formatting blank data buffer";
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);

        //runSmoothTransformTest(g);
        runTensorTest(g);
    }

    private void runSmoothTransformTest(final Graphics g) {
        final Network net = new Network();
        int cw = getWidth() / 2;
        int ch = getHeight() / 2;
        int ic = 50;
        int inc = getWidth() / ic - 5;
        Vector[] bl = new Vector[ic + 1];
        for (int i = 0; i < bl.length; i++) {
            bl[i] = new Vector(inc * i + 5, ch);
        }

        net.addStreamline(bl, inc - 5);

        net.draw(g);
        this.notifyUpdate();
        this.getDisplay().dPanel.addMouseListener(new MouseListener() {
            int d;

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                d = e.getY();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Vertex v = net.getVertex(net.getVerticies().length / 2);
                VertexTransform t = new Translate(new Vector(0, e.getY() - d), 20);
                t.apply(v);
                g.setColor(Color.BLACK);
                g.fillRect(0, 0, getWidth(), getHeight());
                net.draw(g);
                notifyUpdate();
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    private void runTensorTest(Graphics g) {
        addTestNoiseFields();
        if ((boolean) getParam("paintField")) {
            paintField(g);
        }
        this.notifyUpdate();
        randomSample((int) getParam("streamCount"), (int) getParam("vertFrequency"));
    }
    private int[] sampleBounds;
    private int sampleRate;

    private void addTestNoiseFields() {
        int w = getWidth();
        int h = getHeight();
        //PointFalloffField pff = new PointFalloffField(new Vector(w / 2, h / 2), 200);
        //MultiplyField mf = new MultiplyField(new RadialField(new Vector(w / 2, h / 2)), pff);
//
//        PointFalloffField pff2 = new PointFalloffField(new Vector(350, 250), 150);
//        MultiplyField mf2 = new MultiplyField(new RadialField(new Vector(350, 250)), pff2);
//
        field.addField(new ConstantField(Vector.fromTheta(0, 10)), 10);
        //field.addField(mf, 10);
        //af.addField(mf, 10);
//        af.addField(mf2, 10);
        PerlinField pf = new PerlinField();
        field.addField(pf, 1);
    }

    public void paintField(Graphics g) {
        sampleBounds = new int[]{getWidth(), getHeight()};
        sampleRate = ((sampleBounds[0] + sampleBounds[1]) / 2) / ((Integer) this.getParam("subDev"));
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.WHITE);
        Vector v;
        Vector sv;
        int dr = 2;
        //if(true){return;}
        for (int y = 0; y <= sampleBounds[1]; y += sampleRate) {
            for (int x = 0; x <= sampleBounds[0]; x += sampleRate) {
                v = field.sample(x, y);
                sv = Vector.multiply(v, ((int) this.getParam("dVLength")));
                System.out.println(x + ", " + y);

                g.fillOval(x - dr, y - dr, dr * 2, dr * 2);
                g.drawLine(x, y, (int) Math.round(x + sv.getX()), (int) Math.round(y + sv.getY()));
            }
        }

    }

    public void randomSample(int streamCount, final int res) {
        Graphics2D g = data.createGraphics();
        VFTrace trace = new VFTrace(field);
        final Network net = new Network();
        boolean eign;
        int direction;

        for (int i = 0; i < streamCount; i++) {
            System.out.println("Tracing " + i + "/" + streamCount);
            prog = ((float) i / streamCount) * 100;
            this.notifyUpdate();
            eign = (Math.random() > 0.5);
            direction = ((Math.random() > 0.5) ? -1 : 1);
            trace.trace((Vector.random(getWidth(), getHeight())), res, direction, 50, eign);
        }
        Streamline[] traces = trace.getTraces();
        MultiTask<Streamline> mTask = new MultiTask<Streamline>(new Task() {

            @Override
            public void execute(Object data) {
                Streamline s = (Streamline) data;
                if (s == null) {
                    return;
                }
                try {
                    net.addStreamline(s.getData(), res - 1);
                } catch (NullPointerException npe) {
                    return;
                }
            }
        }, traces);
        mTask.start(1);//more than one thread causes instabilities
        while (mTask.hasNext()) {
            prog = mTask.getProgress();
            notifyUpdate();
        }
        int r = 2;
        Vector l;
        int[] li;
        int[] lli;
        Vertex v;
        Vertex[] verts = net.getVerticies();
        for (int i = 0; i < verts.length; i++) {
            System.out.println("Drawing vert " + i + "/" + verts.length);
            v = verts[i];
            l = v.getLocation();
            li = l.toInt();
            g.setColor(Color.magenta);
            for (Vertex cv : v.getConnections()) {
                if (cv == null) {
                    break;
                }
                lli = cv.getLocation().toInt();
                g.drawLine(li[0], li[1], lli[0], lli[1]);
            }
            g.setColor(v.getVertColor());
            g.fillOval(li[0] - r, li[1] - r, r * 2, r * 2);

            this.notifyUpdate();
        }

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
            case "subDev":
                return (Integer) 10;
            case "dVLength":
                return (Integer) 5;
            case "streamCount":
                return (Integer) 20;
            case "vertFrequency":
                return (Integer) 10;
            case "paintField":
                return true;
        }
        return null;
    }

    @Override
    public String getLocationDataString(int x, int y) {
        if (field != null) {
            Vector v = field.sample(x, y);
            return "Raw val: " + v;
        } else {
            return "";
        }
    }

}
