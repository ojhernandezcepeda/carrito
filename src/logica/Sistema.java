/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logica;

import java.awt.Point;
import java.io.File;
import java.util.Vector;
import persistencia.DatosDTO;
import persistencia.DatosBinario;
import persistencia.DatosTexto;

/**
 *
 * @author oaromerov
 */
public class Sistema {
    private Vector vPuntos;
    private Vector vLineas;
    private Carro carrito;
    private DatosDTO datos;
    
    public static int MODO_DIBUJO_TRAZO = 0;
    public static int MODO_DIBUJO_IMAGEN = 1;
    private int modoDibujo;
    public static int ARCHIVO_TEXTO = 0;
    public static int ARCHIVO_BINARIO = 1;
    private int modoArchivo;
    private int numeroPuntos;
    private int numeroCaminos;
    private int velocidad;
    private boolean datosGuardados;
    
    public Sistema(){
        modoDibujo = MODO_DIBUJO_TRAZO;
        modoArchivo = ARCHIVO_TEXTO;
        restaurar();
    }

    private void restaurar(){
        vPuntos = new Vector();
        vLineas = new Vector();
        carrito = new Carro();
        datos = null;
        numeroPuntos = 0;
        numeroCaminos = 0;
        datosGuardados = true;
    }

    public int getModoArchivo() {
        return modoArchivo;
    }

    public void setModoArchivo(int modoArchivo) {
        this.modoArchivo = modoArchivo;
        setDatosGuardados(false);
        datos = null;
    }

    public boolean isDatosGuardados() {
        return datosGuardados;
    }

    public void setDatosGuardados(boolean datosGuardados) {
        this.datosGuardados = datosGuardados;                
    }

    public int getModoDibujo() {
        return modoDibujo;
    }

    public void setModoDibujo(int modoDibujo) {
        this.modoDibujo = modoDibujo;
    }

    public Carro getCarrito() {
        return carrito;
    }

    public Vector getCaminos() {
        return vLineas;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void adicionarPuntoRecorrido(Point punto) {
        if (numeroPuntos > 0 && !punto.equals(vPuntos.get(numeroPuntos - 1))) {
            vPuntos.add(punto);
            numeroPuntos++;
            procesarCamino();
        }else{
             vPuntos.add(punto);
             numeroPuntos++;
        }
    }

    public void procesarCamino() {
        Point punto;
        int xInicial = 0, yInicial = 0, xFinal = 0, yFinal = 0, i;

        if (numeroPuntos < 2) {
            return;
        }

        punto = (Point) vPuntos.get(numeroPuntos - 2);
        // apenas es el primer punto
        xInicial = (int) punto.getX();
        yInicial = (int) punto.getY();

        punto = (Point) vPuntos.get(numeroPuntos - 1);
        // apenas es el primer punto
        xFinal = (int) punto.getX();
        yFinal = (int) punto.getY();

        crearCamino(xInicial, yInicial, xFinal, yFinal);
    }

    public void crearCamino(int xInicial, int yInicial, int xFinal, int yFinal) {
        Camino linea;
        numeroCaminos++;
        linea = new Camino(xInicial, yInicial, xFinal, yFinal);
        vLineas.add(linea);
    }

    public void setVelocidad(int i) {
        velocidad = i;
    }

    public DatosDTO getDatos() {
        if(datos == null){
            if(modoArchivo == ARCHIVO_BINARIO){
                datos = new DatosBinario();
            }else{
                datos = new DatosTexto();
            }
        }
        return datos;
    }

    public Vector abrirArchivo(File nombreArchivo) throws Exception {
        Vector puntos;
        restaurar();
        DatosDTO.setArchivo(nombreArchivo);
        puntos = getDatos().abrirArchivo();
        return puntos;
    }

    public void guardarArchivo(File nombreArchivo) throws Exception {
        // mostrar error si el nombre del archivo es inválido
        DatosDTO.setArchivo(nombreArchivo);
        getDatos().guardarArchivo(vPuntos);
    }

    public int getNumeroPuntos() {
        return vPuntos.size();
    }

    public static int calcularCambioSentido(int anguloInicial, int anguloFinal)
    // Calculamos como debe empezar el giro dependiendo de los angulos
    {
        int factorIncremento = 1; // Determina si incrementamos o decrementamos el angulo
        int cuadranteInicial, cuadranteFinal;

        cuadranteInicial = ubicacionCuadrante(anguloInicial);
        cuadranteFinal = ubicacionCuadrante(anguloFinal);

        if(cuadranteInicial == cuadranteFinal)
        // Los angulos se encuentran en el mismo cuadrante
        {
            if(anguloInicial < anguloFinal){
                factorIncremento = 1; // Incrementamos
            }else{
                factorIncremento = -1; // Decrementamos
            }
        }
        else
        // Angulos en cuadrantes diferentes
        {
            if(cuadranteInicial == 1){
                if (cuadranteFinal == 2) {
                    factorIncremento = 1;
                } else if (cuadranteFinal == 3) {
                    // Se necesita calcular el opuesto ya que se podria girar en cualquier sentido
                    int anguloOpuesto = normalizarAngulo(anguloFinal-180);
                    if(anguloInicial > anguloOpuesto){
                        factorIncremento = 1;
                    }
                    else{
                        factorIncremento = -1;
                    }
                } else {
                    // Es el cuadranteFinal 4
                    factorIncremento = -1;
                }
            }
            else if(cuadranteInicial == 2){
                if (cuadranteFinal == 1) {
                    factorIncremento = -1;
                }
                else if (cuadranteFinal == 3) {
                    factorIncremento = 1;
                }
                else
                // Es el cuadranteFinal 4
                {
                    int anguloOpuesto = normalizarAngulo(anguloFinal - 180);
                    if (anguloInicial > anguloOpuesto) {
                        factorIncremento = 1;
                    } else {
                        factorIncremento = -1;
                    }
                }
            }
            else if(cuadranteInicial == 3){
                if (cuadranteFinal == 1) {
                    int anguloOpuesto = normalizarAngulo(anguloFinal-180);
                    if(anguloInicial > anguloOpuesto){
                        factorIncremento = 1;
                    }
                    else{
                        factorIncremento = -1;
                    }
                }
                else if (cuadranteFinal == 2) {
                    factorIncremento = -1;
                }
                else {
                    // Es el cuadranteFinal 4
                    factorIncremento = 1;
                }
            }
            else{
                // Es el cuadranteInicial 4
                if (cuadranteFinal == 1) {
                    factorIncremento = 1;
                } else if (cuadranteFinal == 2) {
                    int anguloOpuesto = normalizarAngulo(anguloFinal-180);
                    if(anguloInicial > anguloOpuesto){
                        factorIncremento = 1;
                    }
                    else{
                        factorIncremento = -1;
                    }
                }
                else {
                    // Es el cuadranteFinal 3
                    factorIncremento = -1;
                }
            }
        }
        return factorIncremento;
    }

    public static int ubicacionCuadrante(int anguloGrados)
    // Calculamos en que cuadrante se encuentra el angulo
    {
        int cuadrante;
        int correccionAngulo = normalizarAngulo(anguloGrados);

        if(correccionAngulo <= 90){
            cuadrante = 1;
        }else if(correccionAngulo <= 180){
            cuadrante = 2;
        }else if(correccionAngulo <= 270){
            cuadrante = 3;
        }else{
            cuadrante = 4;
        }
        return cuadrante;
    }

    public static int normalizarAngulo(int angulo)
    // Con esta funcion calculamos un angulo que este entre 0 y 359 grados centi
    {
        int anguloGrados = 0;
        if(angulo >= 360){
            anguloGrados = angulo % 360;
        }else if(angulo < 0){
            anguloGrados = 360 - (Math.abs(angulo) % 360);
        }else{
            anguloGrados = angulo;
        }
        return anguloGrados;
    }

    public File getArchivo() {
        return DatosDTO.getArchivo();
    }
    
}
