package logica;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author estudiantes
 */
public class Carro {
    public int posX, posY;
    public int largo, ancho;
    public Color color;
    private double angulo;
    private Image imagen;


    private Camino linea;

    public double getAnguloGrados() {
        return angulo;
    }

    public void setAnguloGrados(double angulo) {
        this.angulo = angulo;
    }

    public Carro(){
        ancho = 36;
        largo = 80;
        color = Color.pink;
    }

    public void setPosicion(int longitudCaminoActual) {
        posX =  linea.xInicial + (int)(longitudCaminoActual* Math.cos(angulo*Math.PI/180));
        posY =  linea.yInicial - (int)(longitudCaminoActual * Math.sin(angulo*Math.PI/180));
    }

    public void setCamino(Camino linea) {
        this.linea = linea;
        angulo = linea.getAnguloGrados();
    }    
    
}


