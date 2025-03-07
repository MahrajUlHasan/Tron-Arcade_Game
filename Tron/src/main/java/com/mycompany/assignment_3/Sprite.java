/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_3;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;

public class Sprite {

    protected int x;
    protected int y;
    protected int w;
    protected int h;
    protected Image img;

    public Sprite(int x, int y, int width, int height, Image image) {
        this.x = x;
        this.y = y;
        this.w = width;
        this.h = height;
        this.img = image;
    }

    public void draw(Graphics g) {
        g.drawImage(img, x, y, w, h, null);
    }

    protected Rectangle collisionBox() {
        return new Rectangle(this.x, this.y, this.w, this.h);
    }

    public boolean collision(Sprite other) {

        if (other instanceof Trail) {
            Trail line = (Trail) other;
            Line2D otherLine = new Line2D.Double(line.x, line.y, line.fx, line.fy);
            return this.collisionBox().intersectsLine(otherLine);

        } else if (other instanceof Tron) {
            Tron otherTron = (Tron) other;
            Rectangle otherRect = new Rectangle(otherTron.x, otherTron.y, otherTron.w, otherTron.h);
            if (this == other) {
                return false;
            }
            return this.collisionBox().intersects(otherRect);
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return w;
    }

    public void setWidth(int width) {
        this.w = width;
    }

    public int getHeight() {
        return h;
    }

    public void setHeight(int height) {
        this.h = height;
    }

}
