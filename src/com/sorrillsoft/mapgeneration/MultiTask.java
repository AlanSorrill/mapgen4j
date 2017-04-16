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
public class MultiTask<T> {

    public MultiTask(Task task, T[] data) {
        this.task = task;
        this.data = data;
    }
    private Task task;
    private T[] data;
    private int index = 0;

    public T getNext() {
        return data[index++];
    }

    public boolean hasNext() {
        return index < data.length;
    }

    public void start(int threads) {
        for(int i = 0;i<threads;i++){
            task.run(this);
        }
    }

    public float getProgress() {
        return ((float)index/data.length)*100;
    }

    
}
