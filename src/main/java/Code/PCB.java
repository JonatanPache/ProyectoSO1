/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Code;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JLabel;

/**
 *
 * @author Jonathan
 */
public class PCB {
    private Color color;
    private Point location;
    private Dimension size;
    private Point speed;
    private int number;
    private int indexCola;
    
    public PCB(int num,Color color,int indCola){
        setColor(color);
        indexCola=indCola;
        speed = new Point(-5, 5);
        size = new Dimension(30, 30);
        this.number=num;
        location=new Point(0,0);
    }
    
    public int indexCola(){
        return this.indexCola;
    }
    public String getPCB(){
        return Integer.toString(this.number);
    }
    
    public Dimension getSize() {
        return size;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public Color getColor() {
        return color;
    }

    public Point getLocation() {
        return location;
    }

    public Point getSpeed() {
        return speed;
    }

    public void setSpeed(Point speed) {
        this.speed = speed;
    }

    protected void paint(Graphics2D g2d) {

        Point p = getLocation();
        if (p != null) {
            g2d.setColor(getColor());
            Dimension size = getSize();
            g2d.fill3DRect(p.x, p.y, size.width, size.height,true);
            g2d.drawString(this.getPCB(), p.x, p.y);
        }

    }
    
    public static int random(int maxRange) {
        return (int) Math.round((Math.random() * maxRange));
    }
}
