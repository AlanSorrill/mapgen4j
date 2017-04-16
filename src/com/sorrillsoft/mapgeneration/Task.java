/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration;

/**
 *
 * @author alan
 */
public abstract class Task<T> {

    public void run(final MultiTask<T> mt) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                while (mt.hasNext()) {
                    execute(mt.getNext());
                }
            }
        };
        Thread t = new Thread(r);
        t.start();
    }

    public abstract void execute(T data);
}
