/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import java.awt.image.BufferedImage;
import java.util.HashMap;

/**
 *
 * @author Alan
 */
public abstract class MapGenerator {

    private Display display;

    public Display getDisplay() {
        return display;
    }

    public void setDisplay(Display display) {
        this.display = display;
    }

    public void init(Display d) {
        setDisplay(d);
        onInit(d);
    }

    private boolean paramsChanged = false;

    public boolean isParamsChanged() {
        return paramsChanged;
    }

    public void onParamsUpdated() {
        paramsChanged = false;
    }

    public void notifyParamsChanged() {
        paramsChanged = true;
    }

    protected abstract void onInit(Display d);

    public abstract HashMap<String, Class> getParamTypes();

    public abstract Object getDefaultParam(String key);

    public void setParams(HashMap<String, Object> params) {
        this.params = params;
    }

    public void setParam(String key, Object value) {
        params.put(key, value);
    }

    public String[] getParamList() {
        return params.keySet().toArray(new String[params.size()]);
    }

    public Object getParam(String s) {
        Object o = params.get(s);
        try {
            String so = (String) o;
            try {
                int i = Integer.parseInt(so);
                return i;
            } catch (NumberFormatException nfe) {

            }
            try {
                long l = Long.parseLong(so);
                return l;
            } catch (NumberFormatException nfe) {

            }
            try {
                double d = Double.parseDouble(so);
                return d;
            } catch (NumberFormatException nfe) {

            }
            if (so.equalsIgnoreCase("true") || so.equalsIgnoreCase("false")) {
                return Boolean.parseBoolean(so);
            }
            try {
                Enum e = Enum.valueOf(getParamTypes().get(s), so);
                return e;
            } catch (IllegalArgumentException iae) {

            }
            return so;
        } catch (ClassCastException cce) {
            return o;
        }
    }
    private HashMap<String, Object> params = new HashMap();

    public abstract void generate();

    public abstract BufferedImage getDisplayImage();

    public abstract float getProgressPercent();

    public abstract String getStatusText();

    public void notifyUpdate() {
        display.dPanel.setBackgroundImage(getDisplayImage());
        display.jScrollPane1.repaint();
        display.dPanel.repaint();
        display.setProgress(Math.round(getProgressPercent()));
        display.setTitle(this.getStatusText());
    }
    private boolean canceled = false;

    public boolean isCancelled() {
        return canceled;
    }

    public void unCancel() {
        canceled = false;
    }

    void cancel() {
        canceled = true;
    }

    HashMap<String, Object> getPropertiesMap() {
        return params;
    }

    public abstract String getLocationDataString(int x, int y) ;
}
