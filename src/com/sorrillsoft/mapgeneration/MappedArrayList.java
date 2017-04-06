/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author alan
 */
public class MappedArrayList<V> implements Collection<V> {

    private HashMap<String, V> map = new HashMap();
    private ArrayList<V> list = new ArrayList();
    
    public void setIndex(V v, int index){
        V l;
        for(int i = 0;i<list.size();i++){
            l = list.get(i);
            
            if(l.equals(v)){
                list.remove(i);
                list.add(index, v);
            }
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<V> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] ts) {
        return list.toArray(ts);
    }

    @Override
    public boolean add(V e) {
        map.put(e.toString(), e);
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        map.remove(o.toString());
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> clctn) {
        return list.containsAll(clctn);
    }

    @Override
    public boolean addAll(Collection<? extends V> clctn) {
        boolean succ = true;
        for (V v : clctn) {
            if (!add(v)) {
                succ = false;
            }
        }
        return succ && list.addAll(clctn);
    }

    @Override
    public boolean removeAll(Collection<?> clctn) {
        boolean succ = true;
        for (Object v : clctn) {
            if (!remove(v)) {
                succ = false;
            }
        }
        return succ && list.removeAll(clctn);

    }

    @Override
    public boolean retainAll(Collection<?> clctn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        map.clear();
        list.clear();;
    }

}
