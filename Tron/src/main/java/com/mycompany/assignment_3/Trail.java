/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.assignment_3;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;

/**
 *
 * @author MJ HASAN
 */
public class Trail extends Sprite {

    protected int fx;
    protected int fy;
    protected Color c;
    
    public Trail(int x, int y, int fx, int fy , Color c){
        super(x , y, fx , fy , null);
        this.x = x;
        this.y = y;
        this.fx = fx;
        this.fy = fy;
        this.c = c;
    }
    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(c);
        g2D.setStroke(new BasicStroke(5.0f));
        g2D.drawLine(x, y, fx , fy);
        
    }
    

    public int getFX() {
        return fx;
    }

    public void setFX(int fx) {
        this.fx = fx;
    }

    public int getFY() {
        return fy;
    }

    public void setFY(int fy) {
        this.fy = fy;
    }
}
