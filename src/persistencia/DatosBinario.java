/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Vector;

/**
 *
 * @author oaromerov
 */
public class DatosBinario extends DatosDTO{
    
    private DataInputStream lecturaArchivo;
    private DataOutputStream escrituraArchivo;

    public DatosBinario(){
        super();
    }

    @Override
    public Vector abrirArchivo() throws Exception{
        Vector vPuntos = new Vector();

        byte[] linea;
        int iPunto, x = 0, y = 0;
        int error, dato;
        Point punto;

        // abrir el archivo
        lecturaArchivo = new DataInputStream(new FileInputStream(DatosDTO.getArchivo()));
        
        // Lectura del archivo
        linea = new byte[8];
        error = lecturaArchivo.read(linea, 0, "CAMINOS ".length());
        if(error == -1){
            throw new Exception("Error de lectura del archivo.");
        }

        if(!new String(linea).equals("CAMINOS ")){
            throw new Exception("No es un archivo del caminos!");
        }

        linea = new byte[4];

        error = lecturaArchivo.read(linea, 0, "BIN ".length());
        if(error == -1){
            throw new Exception("Error de lectura del archivo.");
        }

        if(!new String(linea).equals("BIN ")){
            throw new Exception("No es un archivo de caminos binario!");
        }

        do {
            x = lecturaArchivo.readInt();
            y = lecturaArchivo.readInt();
            punto = new Point(x, y);
            vPuntos.add(punto);
        } while (lecturaArchivo.available() != 0);

        // Cerramos el archivo
        lecturaArchivo.close();       
        return vPuntos;
    }

    @Override
    public void guardarArchivo(Vector vPuntos) throws Exception{
        
        int iPunto, x, y;
        Point punto;

        if(vPuntos.size() < 1){
            throw new Exception("No hay nada que guardar!");
        }

        // abrir el archivo
        escrituraArchivo = new DataOutputStream(new FileOutputStream(DatosDTO.getArchivo()));
        // comenzamos a crear el contenido del archivo
        escrituraArchivo.writeBytes("CAMINOS ");
        escrituraArchivo.writeBytes("BIN ");

        for(iPunto = 0; iPunto < vPuntos.size(); iPunto++){
            punto = (Point)vPuntos.get(iPunto);
            x = (int)punto.getX();
            y = (int)punto.getY();
            escrituraArchivo.writeInt(x);
            escrituraArchivo.writeInt(y);
        }             
        escrituraArchivo.close();
    }

}
