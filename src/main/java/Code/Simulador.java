/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Jonathan
 */
public class Simulador extends JPanel{
    List<Queue<PCB>> listaDeColas;
    List<Integer> quantumsPorProceso;
    List<Integer> quantumsPorColas;
    Queue<PCB> colaPlanificador;
    
    
    public Simulador(){
        this.listaDeColas=new ArrayList<>();
        this.quantumsPorColas=new ArrayList<>();
        this.quantumsPorProceso=new ArrayList<>();
        //generando colas y agregando en la lista
        for(int i=0;i<5;i++){
            Queue<PCB> colaTurno=new LinkedList<>();
            // este metodo cargar 6 pcb en la colaTurno
            this.cargarPCB(6,colaTurno,i);
            //agregamos a la lista
            this.listaDeColas.add(colaTurno);
            this.quantumsPorColas.add(1);
            this.quantumsPorProceso.add(1);
        }
    }
    
    
    public Simulador(int cantidadColas,int cantidadPCBs,List<Integer> quantumsCola,List<Integer> quantumsProceso){
        //generando colas y agregando en la lista
        this.listaDeColas=new ArrayList<>();
        this.quantumsPorColas=new ArrayList<>();
        this.quantumsPorProceso=new ArrayList<>();
        for(int i=0;i<cantidadColas;i++){
            Queue<PCB> colaTurno=new LinkedList<>();
            // este metodo cargar 5 pcb en la colaTurno
            this.cargarPCB(cantidadPCBs,colaTurno,i);
            //agregamos a la lista
            this.listaDeColas.add(colaTurno);
        }
        this.quantumsPorColas=quantumsCola;
        this.quantumsPorProceso=quantumsProceso;
    }
    public Simulador(int cantidadColas,List<Integer> quantumsCola,List<Integer> quantumsProceso){
        this.listaDeColas=new ArrayList<>();
        this.quantumsPorColas=new ArrayList<>();
        this.quantumsPorProceso=new ArrayList<>();
        //generando colas y agregando en la lista
        for(int i=0;i<cantidadColas;i++){
            Queue<PCB> colaTurno=new LinkedList<>();
            // este metodo cargar 5 pcb en la colaTurno
            this.cargarPCB(-1,colaTurno,i);
            //agregamos a la lista
            this.listaDeColas.add(colaTurno);
        }
        this.quantumsPorColas=quantumsCola;
        this.quantumsPorProceso=quantumsProceso;
    }
    public List<Queue<PCB>> getListaColas(){
        return this.listaDeColas;
    }

    public List<Integer> getQuantumsPorProceso() {
        return this.quantumsPorProceso;
    }

    public List<Integer> getQuantumsPorColas() {
        return this.quantumsPorColas;
    }
    
    private String mostrarColaPCB(int colaIndice) {
        String cadena = "Cola [" + colaIndice + "]=  ";
        int size=this.listaDeColas.get(colaIndice).size();
        for (int i = 0; i < size; i++) {
            PCB pcbAux=this.listaDeColas.get(colaIndice).poll();
            cadena =cadena + pcbAux.getPCB()+",";
            this.listaDeColas.get(colaIndice).add(pcbAux);
            
        }
        return cadena;
    }
    
    public String mostrarListaColas() {
        String cadena = "";
        
        for (int i = 0; i < this.listaDeColas.size(); i++) {
            
            cadena = cadena+this.mostrarColaPCB(i);
            cadena = cadena + "\n";
        }
        return cadena;
    }
    
    public static int random(int maxRange) {
        return (int) Math.round((Math.random() * maxRange));
    }
    
    private void cargarPCB(int cantidadPCB,Queue<PCB> colaActual,int indCola){
        if (cantidadPCB > 0) {
            //generamos 
            for (int i = 0; i < cantidadPCB; i++) {
                int rand_int1 = ((int) (Math.random()*(1000 - 5))) + 5;
                Color color=buscarColor();
                // creamos el pcb
                PCB pcbActual = new PCB(rand_int1,color.darker(),indCola);
                //agregamos en la cola actual
                colaActual.add(pcbActual);
            }
        }else{
            // cantidad de pcb no ingresado entonces nosotros generamos 
            // creamos desde 10 a 15 pcb para esta cola
            int cant=((int) (Math.random()*(10 - 5))) + 5;
            Random rand = new Random();
            for (int i = 0; i < cant; i++) {
                int rand_int2 = rand.nextInt(1000);
                Color color=buscarColor();
                
                // creamos el pcb
                PCB pcbActual = new PCB(rand_int2,color.darker(),indCola);
                //agregamos en la cola actual
                colaActual.add(pcbActual);
            }
        }
    }
    private Color buscarColor(){
        return new Color(this.random(255),this.random(255),this.random(255));
    }
    
    public Queue<PCB> simularPlanificador(){
        // contador quatums por proceso
        int cqp=0;
        // contador de quatums de colas
        int cqc=0;
        // contador de colas
        int k=0;
        int kq=0;
        // cola aux que referenciara a las colas
        Queue<PCB> colaHistorial=new LinkedList<>();
        Queue<PCB> colaActual=this.listaDeColas.get(k);
        PCB pcbActual=colaActual.poll();
        colaHistorial.add(pcbActual);
        while(kq<2000){
            cqp++;            
            if(cqp==this.quantumsPorProceso.get(k)){
                // sacamos la primera cola de la lista
                //colaActual=this.listaDeColas.get(k);
                // sacamos el pcb frontal de la cola (k)
                // y la eliminamos de la cola actual
                
                // encolar el pcb
                colaActual.offer(pcbActual);
                cqc++;
                if(cqc==this.quantumsPorColas.get(k)){
                   //si ya cumplio los quatum de la cola
                   // entonces k apuntara a la siguiente cola
                   k=this.siguienteCola(k);
                   // si cambio de cola entonces debe reiniciar 
                   // el contador de cola
                   cqc=0;
                   colaActual=this.listaDeColas.get(k);
                   pcbActual=colaActual.poll();
                   cqp=0;
                   
                }else{
                    // sacar otro pcb de la cola misma
                    pcbActual=colaActual.poll();
                    cqp=0;
                }                               
            }
            kq++;
            colaHistorial.add(pcbActual);
            
            
        }
        colaActual.offer(pcbActual);   
        return colaHistorial;
    }
    
    
    
    public int siguienteCola(int k){
        // si k es 4 entonces apunta a la ultima cola
        // si la lista de colas son 5 colas y la primera
        // cola inicia con posicion 0
        if(k==this.listaDeColas.size()-1){
            // si esta en la ultima cola, k debe apuntar 
            // a la primera cola
            return k=0;
        }
        // sino que retorne el siguiente de la cola
        return k+1;
    }
    public Queue<PCB> colaDePCB(int indiceDeCola){
        return this.listaDeColas.get(indiceDeCola);
    }
    
    public Iterable<PCB> pcbsDeLaCola(int indiceCola){
        Queue<PCB> listaPCBDeLaCola=this.listaDeColas.get(indiceCola);
        Iterable<PCB> iterablePCBDeLaCola=listaPCBDeLaCola;
        return iterablePCBDeLaCola;
    }
    
    
    @Override 
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < this.listaDeColas.size(); i++) {
            g2d.drawString("Q["+i+"]",(this.getWidth()-100),40+(i*50) );
            Iterable<PCB> iterableColaTurno = this.pcbsDeLaCola(i);
            for (PCB pcbTurno : iterableColaTurno) {
                pcbTurno.paint(g2d);               
            } 
        }
        g2d.drawString("PRUN",50 , 550);
        g2d.dispose();
    }
    
}
