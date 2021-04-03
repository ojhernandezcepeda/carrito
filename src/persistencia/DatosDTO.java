/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package persistencia;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;

/**
 *
 * @author oaromerov
 */
public abstract class DatosDTO {
    protected static File archivo;
    
    public abstract Vector abrirArchivo() throws Exception;
    public abstract void guardarArchivo(Vector vPuntos) throws Exception;

    public static File getArchivo() {
        return archivo;
    }

    public static void setArchivo(File archivo){
        DatosDTO.archivo = archivo;
    }
    
    
}
