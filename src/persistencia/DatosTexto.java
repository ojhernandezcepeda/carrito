/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Vector;

/**
 *
 * @author oaromerov
 */
public class DatosTexto extends DatosDTO{

    private FileReader lecturaArchivo;
    private FileWriter escrituraArchivo;

    public DatosTexto(){
        super();
    }

    @Override
    public Vector abrirArchivo() throws Exception{
        Vector vPuntos = new Vector();
        BufferedReader buffered;
        String linea;
        String strContenido;
        boolean isX = true;
        int iPunto, x = 0, y = 0;
        int iInicio, iFinal;
        char caracter;
        Point punto;

        // abrir el archivo
        lecturaArchivo = new FileReader(DatosDTO.getArchivo());
        buffered = new BufferedReader(lecturaArchivo);
        // Lectura del archivo
        strContenido = buffered.readLine(); // El archivo se guarda en una sola linea
        if(strContenido == null){
            throw new Exception("Error de lectura del archivo.");
        }
                
        // Cerramos el archivo
        buffered.close();        
        lecturaArchivo.close();

        // Interpretacion del contenido        
        iInicio = 0;
        iFinal = iInicio + "CAMINOS ".length();
        linea = strContenido.substring(iInicio, iFinal);
        
        if(!linea.equals("CAMINOS ")){
            throw new Exception("No es un archivo del caminos!");
        }
        iInicio = iFinal;
        iFinal = iInicio + "TEXT ".length();
        linea = strContenido.substring(iInicio, iFinal);
        if(!linea.equals("TEXT ")){
            throw new Exception("Este archivo de caminos no es de texto!");
        }

        do{
            iInicio = iFinal;
            iFinal = iInicio + 1;
            do{
                caracter = strContenido.charAt(iFinal);
                iFinal++;
            }while(caracter != ',' && iFinal < strContenido.length());

            if(iFinal != strContenido.length()){
                linea = strContenido.substring(iInicio, iFinal-1);
            }else{
                linea = strContenido.substring(iInicio, iFinal);
            }

            if(isX){
                x = new Integer(linea).intValue();
                isX = false;
            }else{
                y = new Integer(linea).intValue();
                punto = new Point(x, y);
                vPuntos.add(punto);
                isX = true;
            }
            
        }while(iFinal < strContenido.length());       
        return vPuntos;
    }

    @Override
    public void guardarArchivo(Vector vPuntos) throws Exception{
        StringBuffer strContenido = new StringBuffer();
        int iPunto, x, y;
        Point punto;

        if(vPuntos.size() < 1){
            throw new Exception("No hay nada que guardar!");
        }
        
        // abrir el archivo
        escrituraArchivo = new FileWriter(DatosDTO.getArchivo());
        // comenzamos a crear el contenido del archivo
        strContenido.append("CAMINOS ");
        strContenido.append("TEXT ");

        for(iPunto = 0; iPunto < vPuntos.size(); iPunto++){
            punto = (Point)vPuntos.get(iPunto);
            x = (int)punto.getX();
            strContenido.append(new Integer(x).toString());
            strContenido.append(",");
            y = (int)punto.getY();
            strContenido.append(new Integer(y).toString());
            if(iPunto < vPuntos.size() - 1){
                strContenido.append(",");
            }
        }                
        // guardamos el contenido creado
        escrituraArchivo.write(strContenido.toString());
        escrituraArchivo.close();
    }

}
