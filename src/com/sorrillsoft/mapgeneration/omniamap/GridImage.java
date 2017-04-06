/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sorrillsoft.mapgeneration.omniamap;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 *
 * @author Alan
 */
public class GridImage {

        private BufferedImage img;
        private Graphics g;
        private int cellsX;
        private int cellsY;
        private int[] cellSize;

        public GridImage(int width, int height, int cellsX, int cellsY, Color bg) {
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g = img.getGraphics();
            g.setColor(bg);
            g.fillRect(0, 0, width, height);
            this.cellsX = cellsX;
            this.cellsY = cellsY;
            cellSize = new int[]{width / cellsX, height / cellsY};
            System.out.println("Creating grid image " + width + " by + " + height + "    cells[" + cellsX + "x"+cellsY +"]     cellsize: " + Arrays.toString(cellSize));
        }

        public int getCellsX() {
            return cellsX;
        }

        public int getCellsY() {
            return cellsY;
        }

        public void fillCell(int x, int y, Color c) {
            int px = x * cellSize[0];
            int py = y * cellSize[1];
            g.setColor(c);
            g.fillRect(px, py, cellSize[0], cellSize[1]);
        }

        public BufferedImage getImg() {
            return img;
        }
        
    }
