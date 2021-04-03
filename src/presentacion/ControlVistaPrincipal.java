package presentacion;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public final class ControlVistaPrincipal implements MouseListener, ActionListener, ComponentListener, ChangeListener {

    private final VistaPrincipal ventanaPrincipal;
    private final Modelo modelo;

    public ControlVistaPrincipal(VistaPrincipal ventana) {
        this.ventanaPrincipal = ventana;
        modelo = ventana.getModelo();
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        getModelo().adicionarPuntoRecorrido(e.getX(), e.getY());
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void actionPerformed(ActionEvent e) {
        int respuesta;
        if (e.getSource() instanceof JButton) {
            JButton boton = (JButton) e.getSource();

            if (boton == ventanaPrincipal.getBtnIniciar()) {
                getModelo().iniciarAnimacion();
            } else {
                getModelo().detenerAnimacion();
            }
        } else if (e.getSource() instanceof JMenuItem) {
            JMenuItem menuItem = (JMenuItem) e.getSource();
            if (menuItem == ventanaPrincipal.getMniNuevo()) {
                if (getModelo().isAnimando()) {
                    respuesta = JOptionPane.showConfirmDialog(ventanaPrincipal, "Desea detener la animacion?", "Animando", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (respuesta == JOptionPane.NO_OPTION) {
                        return;
                    }
                }
                getModelo().detenerAnimacion();
                getModelo().restablecerComponentes();
                getModelo().crearNuevoTablero();
            } else if (menuItem == ventanaPrincipal.getMniAbrir()) {
                getModelo().abrirTablero();
            } else if (menuItem == ventanaPrincipal.getMniGuardar()) {
                getModelo().guardarTablero();
            } else if (menuItem == ventanaPrincipal.getMniPreferencias()) {
                getModelo().modificarPreferencias();
            } else if (menuItem == ventanaPrincipal.getMniSalir()) {
                System.exit(0);
            } else {// Es el menu de acerca
                getModelo().mostrarCreditos();
            }
        } else //No hacemos nada
        {
        }

    }

    public void componentResized(ComponentEvent e) {
        getModelo().redimensionarVentanaPrincipal();
    }

    public void componentMoved(ComponentEvent e) {

    }

    public void componentShown(ComponentEvent e) {

    }

    public void componentHidden(ComponentEvent e) {

    }

    public void stateChanged(ChangeEvent e) {
        getModelo().controlarVelocidad();
    }

    public Modelo getModelo() {
        return modelo;
    }

}
