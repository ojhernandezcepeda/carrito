package logica;


import java.io.Serializable;


public class Camino implements Serializable{

    public  int xInicial, xFinal, yInicial, yFinal;
    private double longitud;
    private double angulo;

    public double getAnguloGrados() {
        int x, y;
        x = xFinal-xInicial;
        y = yFinal-yInicial;
        angulo = Math.acos(x/getLongitud());
        if(y < 0){
            angulo = angulo*180/Math.PI;
        }else{
            angulo = 360 - angulo*180/Math.PI;
        }
        return angulo;
    }

    public double getLongitud() {
        longitud = Math.sqrt(Math.pow(xFinal-xInicial, 2) + Math.pow(yFinal-yInicial, 2));
        return longitud;
    }
    
    public Camino(int xIni, int yIni, int xFin, int yFin)
    {
        xInicial = xIni;
        xFinal = xFin;
        yInicial = yIni;
        yFinal = yFin;
        //System.out.println("Punto Inicial - X: " + xInicial + " Y: " + yInicial);
        //System.out.println("Punto   Final - X: "+xFinal+" Y: "+yFinal);
        //System.out.println("Longitud: " + getLongitud()+", Angulo: " + getAnguloGrados());
    }    
}
