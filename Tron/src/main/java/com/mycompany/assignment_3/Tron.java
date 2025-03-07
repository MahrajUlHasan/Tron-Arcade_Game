/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_3;

import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author MJ HASAN
 */
public class Tron extends Sprite {

//    public ArrayList<Trail> trails;
    private int inc;
    private int direction;
//    private int lastDirection;

    public Tron(int x, int y, int w, int h, Image img, int d) {
        super(x, y, w, h, img);
        inc = 1;
        direction = d;
//        this.lastDirection = d;
    }

    @Override
    protected Rectangle collisionBox() {
        switch (direction) {
            case 0:
                return new Rectangle(x, y, w, (h / 2));
            case 1:
                return new Rectangle(x + (w / 2), y, (w / 2), h);
            case 2:
                return new Rectangle(x, y + (h / 2), w, (h / 2));
            case 3:
                return new Rectangle(x, y, (w / 2), h);
            default:
                return new Rectangle(x, y, w, h);
        }
    }

    public boolean moveRight() {

        this.x += inc;
        if (direction != 1) {
            this.setDirection(1);
        }
        return true;
    }

    public boolean moveLeft() {

        this.x -= inc;
        if (direction != 3) {
            setDirection(3);
        }
        return true;
    }

    public boolean moveUp() {
        this.y -= inc;
        if (direction != 0) {
            setDirection(0);
        }
        return true;
    }

    public boolean moveDown() {
        this.y += inc;

        if (direction != 2) {
            setDirection(2);
        }
        return true;
    }

    public boolean autoMove() {
        switch (direction) {
            case 0:
                return this.moveUp();
            case 1:
                return this.moveRight();
            case 2:
                return this.moveDown();
            case 3:
                return this.moveLeft();
            default:
                return false;
        }
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int d) {
        // this.lastDirection = direction;
        direction = d;
    }

    public boolean validPosition() {
        return this.x >= 0 && this.x + this.w <= 800 && this.y >= 0 && this.y + this.h <= 800;
    }

}
