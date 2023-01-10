/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend;

import Code.Simulador;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Jonathan
 */
public class Frame extends JFrame{
    private static Simulador simulador;
    
    public static void main(String[] args) {
        new Frame(simulador);    
    }
    
    public Frame(Simulador simuladorInput) {
        this.simulador=simuladorInput;
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException ex) {
                } catch (InstantiationException ex) {
                } catch (IllegalAccessException ex) {
                } catch (UnsupportedLookAndFeelException ex) {
                }
                JFrame frame = new JFrame("Animacion");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                Simulador balls =simulador;
                frame.add(balls);
                frame.setSize(800, 700);
                frame.setVisible(true);
                new Thread(new PCBEngine(balls)).start();
            }
        });
    }
}
