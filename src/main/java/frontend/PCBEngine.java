/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import Code.PCB;
import Code.Simulador;
import java.awt.Dimension;
import java.awt.Point;
import java.lang.reflect.Array;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Jonathan
 */
public class PCBEngine implements Runnable{
    private Simulador simuladorPadre;
    
    public PCBEngine(Simulador simulador){
        this.simuladorPadre=simulador;
    }
    
    public Simulador getPadreSimulador(){
        return this.simuladorPadre;
    }
    
    public static int random(int maxRange) {
        return (int) Math.round((Math.random() * maxRange));
    }
    
    @Override
    public void run() {
        int width = this.getPadreSimulador().getWidth();
        int height = this.getPadreSimulador().getHeight();
        int x ;
        int y ;
        for (int i = 0; i < this.simuladorPadre.getListaColas().size(); i++) {
            Iterable<PCB> colaTurno = this.simuladorPadre.pcbsDeLaCola(i);
            int x1=(width-100) -(simuladorPadre.colaDePCB(i).size() *50);
            y = 40 +(i * 50);
            int j = 0;
            for (PCB pcb : colaTurno) {
                x = x1 + (j * 50);
                Dimension size = pcb.getSize();
                if (x + size.width > width) {
                    x = width - size.width;
                }
                if (y + size.height > height) {
                  y = height - size.height;
                  System.out.println("regrafico el y del pcb");
               }
               j++;
               pcb.setLocation(new Point(x, y));
            }
        }
        
        Queue<PCB> historial=simuladorPadre.simularPlanificador();
        PCB pcbActual;
        Point punto;
        while (simuladorPadre.isVisible()) {
            pcbActual = historial.poll();
            punto=pcbActual.getLocation();
            while (pcbActual.getLocation().x > 50) {
                // Repaint the balls pen...
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        simuladorPadre.repaint();
                    }
                });
                movingIzquierdaX(pcbActual); 
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            while (pcbActual.getLocation().y < 500) {
                // Repaint the balls pen...
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        simuladorPadre.repaint();
                    }
                });
                movingIzquierdaY(pcbActual);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                }
            }
            while(pcbActual==historial.peek()){
                pcbActual=historial.poll();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PCBEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            actualizarPCB(pcbActual, punto);
        } 
    }
    
    public void actualizarPCB(PCB pcb, Point origin){
        Queue<PCB> cola=simuladorPadre.colaDePCB(pcb.indexCola());
        var array=cola.toArray();
        int indicePCB=indicePCBEnLaCola(pcb);
        int indiceActual=indicePCB;
        int indicePosterior=indiceActual+1;
        if(indicePosterior>=array.length){indicePosterior=0;};
        PCB pcbPost=(PCB)array[indicePCB];
        PCB pcbActual=(PCB)array[indicePCB];
        Point puntoPost;
        while (indicePCB != indicePosterior) {
            pcbPost = (PCB) array[indicePosterior];
            puntoPost = pcbPost.getLocation();
            pcbPost.setLocation(origin);
            pcbActual = pcbPost;
            origin = puntoPost;
            indicePosterior++;
            if (indicePosterior == array.length) {
                indicePosterior = 0;
            }
        }
        if(indicePosterior==indicePCB){
            pcbPost = (PCB) array[indicePosterior];
            pcbPost.setLocation(origin);
        }
    }
    
    private int indicePCBEnLaCola(PCB pcb){
        Queue<PCB> cola=simuladorPadre.colaDePCB(pcb.indexCola());
        var array=cola.toArray();
        for(int i=0;i<array.length;i++){
            if(pcb==array[i]){
                return i;
            }
        }
        return -1;
    }
    public void move(PCB ball) {

        Point p = ball.getLocation();
        Point speed = ball.getSpeed();
        Dimension size = ball.getSize();

        int vx = speed.x;
        int vy = speed.y;

        int x = p.x;
        int y = p.y;

        if (x + vx < 0 || x + size.width + vx > simuladorPadre.getWidth()) {
            vx *= -1;
        }
        if (y + vy < 0 || y + size.height + vy > simuladorPadre.getHeight()) {
            vy *= -1;
        }
        x += vx;
        y += vy;

        ball.setSpeed(new Point(vx, vy));
        ball.setLocation(new Point(x, y));
        
    }
    
    public void movingIzquierdaX(PCB ball){
        Point p = ball.getLocation();
        Point speed = ball.getSpeed();
        Dimension size = ball.getSize();

        int vx = speed.x;
        int vy = speed.y;

        int x = p.x;
        int y = p.y;

        if (x + vx < 0 || x + size.width + vx > simuladorPadre.getWidth()) {
            vx *= -1;
        }
        x += vx;
        ball.setSpeed(new Point(vx, vy));
        ball.setLocation(new Point(x, y));

    }
    public void movingIzquierdaY(PCB ball){
        Point p = ball.getLocation();
        Point speed = ball.getSpeed();
        Dimension size = ball.getSize();

        int vx = speed.x;
        int vy = speed.y;

        int x = p.x;
        int y = p.y;

        if (y + vy < 0 || y + size.height + vy > simuladorPadre.getHeight()) {
            vy *= -1;
        }
        y += vy;
        ball.setSpeed(new Point(vx, vy));
        ball.setLocation(new Point(x, y));
    }
}
