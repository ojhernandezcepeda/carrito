
package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import logica.Sistema;


public class ControlVistaPreferencias implements ActionListener{
    private final VistaPreferencias ventanaPreferencias;
    private final Modelo modeloSistema;

    public ControlVistaPreferencias(VistaPreferencias ventana) {
        this.ventanaPreferencias = ventana;
        this.modeloSistema = ventana.getModeloSistema();
        actualizarModoDibujo();
        actualizarModoArchivo();
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() instanceof JRadioButton){
            JRadioButton radioBoton = (JRadioButton) e.getSource();
            if (radioBoton == ventanaPreferencias.getRbtnLineas()){
                modeloSistema.getSistema().setModoDibujo(Sistema.MODO_DIBUJO_TRAZO);
            }else if (radioBoton == ventanaPreferencias.getRbtnImagen()){
                modeloSistema.getSistema().setModoDibujo(Sistema.MODO_DIBUJO_IMAGEN);
            }else if (radioBoton == ventanaPreferencias.getRbtnTexto()){
                modeloSistema.getSistema().setModoArchivo(Sistema.ARCHIVO_TEXTO);
            }else if (radioBoton == ventanaPreferencias.getRbtnBinario()){
                modeloSistema.getSistema().setModoArchivo(Sistema.ARCHIVO_BINARIO);
            }else{
            }
            actualizarModoDibujo();
            actualizarModoArchivo();
        }
        else
        // Es el boton de cerrar
        {
            JButton boton = (JButton) e.getSource();

            if (boton == ventanaPreferencias.getBtnCerrar())
            // Este if no es necesario ya que es el unico JButton
            {
                // Cerramos la ventana
                ventanaPreferencias.dispose();
            }else
            // Pero en caso que no fuera el unico boton, seguimos comprobando cual
            // fue el que genero el evento
            {
            }
        }
    }

    private void actualizarModoDibujo() {
        if(modeloSistema.getSistema().getModoDibujo() == Sistema.MODO_DIBUJO_IMAGEN){
            ventanaPreferencias.getRbtnLineas().setSelected(false);
            ventanaPreferencias.getRbtnImagen().setSelected(true);
        }else{
            ventanaPreferencias.getRbtnLineas().setSelected(true);
            ventanaPreferencias.getRbtnImagen().setSelected(false);
        }
    }

    private void actualizarModoArchivo() {
        if(modeloSistema.getSistema().getModoArchivo() == Sistema.ARCHIVO_TEXTO){
            ventanaPreferencias.getRbtnTexto().setSelected(true);
            ventanaPreferencias.getRbtnBinario().setSelected(false);
        }else{
            ventanaPreferencias.getRbtnTexto().setSelected(false);
            ventanaPreferencias.getRbtnBinario().setSelected(true);
        }
    }



}
