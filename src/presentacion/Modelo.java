package presentacion;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import logica.Camino;
import logica.Carro;
import logica.Sistema;

public class Modelo implements Runnable {

      private Image imagenCarro;
      private boolean animando;
      private Sistema sistema;
      private VistaPrincipal ventanaPrincipal;
      private VistaPreferencias ventanaPreferencias;
      private Thread hiloDibujo;

      //*********** Métodos ocultación de información
      public Sistema getSistema() {
            if (sistema == null) {
                  sistema = new Sistema();
            }
            return sistema;
      }

      public VistaPrincipal getVentanaPrincipal() {
            if (ventanaPrincipal == null) {
                  ventanaPrincipal = new VistaPrincipal(this);
            }
            return ventanaPrincipal;
      }

      //********** Métodos Punto de vista funcional
      public void adicionarPuntoRecorrido(int x, int y) {
            if (isAnimando()) {
                  return;
            }
            Point punto = new Point(x, y);
            getSistema().adicionarPuntoRecorrido(punto);
            getSistema().setDatosGuardados(false);
            dibujar();
      }

      public void dibujar() {
            String nombreArchivo;
            if (getSistema().getDatos() == null) {
                  nombreArchivo = "Sin titulo";
            } else {
                  if (getSistema().getDatos().getArchivo() == null) {
                        nombreArchivo = "Sin titulo";
                  } else {
                        nombreArchivo = getSistema().getDatos().getArchivo().getPath();
                  }
            }
            String estado = getSistema().isDatosGuardados() ? "" : " *";
            getVentanaPrincipal().setTitle("Mis Caminos - " + nombreArchivo + estado);

            Canvas papel = getVentanaPrincipal().getLienzo();
            Graphics lienzo = papel.getGraphics();
            BufferedImage dobleBuffer = new BufferedImage(papel.getWidth(), papel.getHeight(), BufferedImage.TYPE_INT_RGB);

            Graphics lapiz = dobleBuffer.getGraphics();
            lapiz.clearRect(0, 0, papel.getWidth(), papel.getHeight());

            dibujarCaminos(lapiz);
            if (isAnimando()) {
                  dibujarCarro(lapiz);
            }

            lienzo.drawImage(dobleBuffer, 0, 0, papel);
      }
      
      void crearNuevoTablero() {
            if (!isAnimando()) {
                  sistema = null;   // Informamos al recolector de basura que liberamos memoria para este objeto
                  System.gc();      // Invocamos al recolector de basura sin espera
                  animando = false;
            }
      }

      void modificarPreferencias() {
            int anchoPantalla = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int altoPantalla = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

            VistaPreferencias ventanaPreferencias;
            ventanaPreferencias = new VistaPreferencias(this);
            ventanaPreferencias.setSize(510, 285);
            ventanaPreferencias.setLocation((anchoPantalla - ventanaPreferencias.getWidth()) / 2, (altoPantalla - ventanaPreferencias.getHeight()) / 2);
            ventanaPreferencias.setVisible(true);
      }

      void abrirTablero() {

            if (!getSistema().isDatosGuardados()) {
                  if (getSistema().getNumeroPuntos() > 0) {
                        String mensaje = "No se ha guardado su trabajo! \nDesea guardarlo?";
                        int resultado = JOptionPane.showConfirmDialog(getVentanaPrincipal(), mensaje, "Cuidado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        if (resultado == JOptionPane.YES_OPTION) {
                              return;
                              //guardarTablero();
                        }
                  }
            }

            JFileChooser selectorArchivo = new JFileChooser();
            selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
            File archivo;

            int resultado = selectorArchivo.showOpenDialog(getVentanaPrincipal());

            // si el usuario hizo clic en el botón Cancelar del cuadro de diálogo, regresar
            if (resultado == JFileChooser.CANCEL_OPTION) {
                  return;
            }

            // obtener el archivo seleccionado
            archivo = selectorArchivo.getSelectedFile();

            if (archivo == null || archivo.getName().equals("")) {
                  JOptionPane.showMessageDialog(getVentanaPrincipal(), "Nombre de archivo incorrecto!", "Error", JOptionPane.ERROR_MESSAGE);
            }

            try {
                  Vector puntos = getSistema().abrirArchivo(archivo);

                  for (int p = 0; p < puntos.size(); p++) {
                        getSistema().adicionarPuntoRecorrido((Point) puntos.get(p));
                        dibujar();
                  }
                  getSistema().setDatosGuardados(true);
            } catch (Exception ex) {
                  JOptionPane.showMessageDialog(getVentanaPrincipal(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                  //ex.printStackTrace();
            }

      }

      void guardarTablero() {
            // mostrar cuadro de diálogo para que el usuario pueda seleccionar el archivo
            JFileChooser selectorArchivo;
            File archivo;
            int resultado;

            if (!getSistema().isDatosGuardados()) {
                  selectorArchivo = new JFileChooser();
                  selectorArchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
                  resultado = selectorArchivo.showSaveDialog(getVentanaPrincipal());

                  // si el usuario hizo clic en el botón Cancelar del cuadro de diálogo, regresar
                  if (resultado == JFileChooser.CANCEL_OPTION) {
                        return;
                  }
                  // obtener el archivo seleccionado
                  archivo = selectorArchivo.getSelectedFile();

                  if (archivo == null || archivo.getName().equals("")) {
                        JOptionPane.showMessageDialog(getVentanaPrincipal(), "Nombre de archivo incorrecto!", "Error", JOptionPane.ERROR_MESSAGE);
                  } else {
                        if (archivo.exists()) {
                              String mensaje = "Este archivo ya existe! \nDesea sobreescribirlo?";
                              resultado = JOptionPane.showConfirmDialog(getVentanaPrincipal(), mensaje, "Cuidado", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                              if (resultado == JOptionPane.NO_OPTION) {
                                    return;
                              }
                        }
                  }
            } else {
                  archivo = getSistema().getArchivo();
            }

            try {
                  getSistema().guardarArchivo(archivo);
                  getSistema().setDatosGuardados(true);
            } catch (Exception ex) {
                  JOptionPane.showMessageDialog(getVentanaPrincipal(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

      }

      public void mostrarCreditos() {
            int anchoPantalla = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            int altoPantalla = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

            VistaAcerca ventanaAcerca;
            ventanaAcerca = new VistaAcerca();
            ventanaAcerca.setSize(390, 210);
            ventanaAcerca.setLocation((anchoPantalla - ventanaAcerca.getWidth()) / 2, (altoPantalla - ventanaAcerca.getHeight()) / 2);
            ventanaAcerca.setVisible(true);
      }

      public void iniciar() {
            crearNuevoTablero();
            getVentanaPrincipal().setSize(800, 600);
            getVentanaPrincipal().setVisible(true);
      }

      public void iniciarAnimacion() {
            setVelocidad(110 - getVentanaPrincipal().getSliVelocidad().getValue());
            getVentanaPrincipal().getBtnIniciar().setEnabled(false);
            getVentanaPrincipal().getBtnDetener().setEnabled(true);
            hiloDibujo = new Thread(this);
            hiloDibujo.start();
      }

      public void detenerAnimacion() {
            getVentanaPrincipal().getBtnIniciar().setEnabled(true);
            getVentanaPrincipal().getBtnDetener().setEnabled(false);
            animando = false;
            hiloDibujo = null;
            System.gc();
      }
      
      //***** Métodos correspondientes a la lógica de presentación
      
      public void animar() throws Exception {
            int caminos;
            int longitudTotalActual, longitudCaminoActual;
            int caminoActual, longitudAlcanzadaCaminoActual;
            int totalRecorrido = 0;
            Camino linea;

            animando = true;

            caminos = getSistema().getCaminos().size();

            // Calcular la longitud total del recorrido
            for (caminoActual = 0; caminoActual < caminos; caminoActual++) {
                  linea = (Camino) getSistema().getCaminos().get(caminoActual);
                  totalRecorrido += linea.getLongitud();
            }
            getVentanaPrincipal().getPbrRecorrido().setMaximum(totalRecorrido);
            //System.out.println("Total recorrido: " + totalRecorrido);
            getVentanaPrincipal().getPbrRecorrido().setValue(0);
            getVentanaPrincipal().getPbrRecorrido().setString("" + 0 + " / " + totalRecorrido);

            // Iniciar el recorrido
            if (getSistema().getCaminos().size() < 1) {
                  detenerAnimacion();
                  throw new Exception("No hay caminos que recorrer!");
            }

            caminoActual = 0;
            longitudAlcanzadaCaminoActual = 0;
            linea = (Camino) getSistema().getCaminos().get(caminoActual);

            longitudCaminoActual = (int) linea.getLongitud();

            for (longitudTotalActual = 0; longitudTotalActual <= totalRecorrido; longitudTotalActual++) {
                  if (!isAnimando()) {
                        return;
                  }
                  if (longitudAlcanzadaCaminoActual > longitudCaminoActual) {
                        caminoActual++;
                        linea = (Camino) getSistema().getCaminos().get(caminoActual);
                        longitudCaminoActual = (int) linea.getLongitud();
                        longitudAlcanzadaCaminoActual = 0;
                        girarCarroHacia(linea);
                        continue;
                  }

                  getSistema().getCarrito().setCamino(linea);
                  getSistema().getCarrito().setPosicion(longitudAlcanzadaCaminoActual);
                  dibujar();
                  esperar(getSistema().getVelocidad());
                  getVentanaPrincipal().getPbrRecorrido().setValue(longitudTotalActual);
                  getVentanaPrincipal().getPbrRecorrido().setString("" + longitudTotalActual + " / " + totalRecorrido);
                  longitudAlcanzadaCaminoActual++;
            }

      }

      public boolean isAnimando() {
            return animando;
      }

      public void esperar(int tiempo) // tiempo en milisegundos
      {
            try {
                  Thread.sleep(tiempo);
            } catch (InterruptedException ex) {
            }
      }

      public void dibujarCaminos(Graphics lapiz) {
            int xInicial = 0, yInicial = 0, xFinal, yFinal;
            int i;
            Camino linea;

            lapiz.setColor(Color.white);
            for (i = 0; i < getSistema().getCaminos().size(); i++) {
                  linea = (Camino) getSistema().getCaminos().get(i);
                  lapiz.drawLine(linea.xInicial, linea.yInicial, linea.xFinal, linea.yFinal);
            }
      }

      public void run() {
            try {
                  animar();
            } catch (Exception ex) {
                  JOptionPane.showMessageDialog(getVentanaPrincipal(), ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            getVentanaPrincipal().getBtnIniciar().setEnabled(true);
            getVentanaPrincipal().getBtnDetener().setEnabled(false);
      }

      public void setVelocidad(int i) {
            getSistema().setVelocidad(i);
      }

      public void dibujarCarro(Graphics lapiz) {

            Carro carrito = getSistema().getCarrito();
            int posX = carrito.posX;
            int posY = carrito.posY;

            int[] puntosX = new int[4];
            int[] puntosY = new int[4];
            //System.out.println("angulo: " + carrito.getAnguloGrados());

            // Reemplazar estos calculos para que el rectangulo este con inclinacion con la pendiente de la linea
            if (getSistema().getModoDibujo() == Sistema.MODO_DIBUJO_TRAZO) {
                  puntosX[0] = (int) (posX - ((carrito.largo / 2) * Math.cos((Math.PI / 180) * carrito.getAnguloGrados())) + (carrito.ancho / 2) * Math.sin((Math.PI / 180) * carrito.getAnguloGrados()) + Math.PI);
                  puntosX[1] = (int) (posX - ((carrito.largo / 2) * Math.cos((Math.PI / 180) * carrito.getAnguloGrados())) - (carrito.ancho / 2) * Math.sin((Math.PI / 180) * carrito.getAnguloGrados()) + Math.PI);
                  puntosX[2] = (int) (posX + ((carrito.largo / 2) * Math.cos((Math.PI / 180) * carrito.getAnguloGrados())) - (carrito.ancho / 2) * Math.sin((Math.PI / 180) * carrito.getAnguloGrados()) + Math.PI);
                  puntosX[3] = (int) (posX + ((carrito.largo / 2) * Math.cos((Math.PI / 180) * carrito.getAnguloGrados())) + (carrito.ancho / 2) * Math.sin((Math.PI / 180) * carrito.getAnguloGrados()) + Math.PI);

                  puntosY[0] = (int) (posY + ((carrito.largo / 2) * Math.sin((Math.PI / 180) * carrito.getAnguloGrados())) + (carrito.ancho / 2) * Math.cos((Math.PI / 180) * carrito.getAnguloGrados()) + Math.PI);
                  puntosY[1] = (int) (posY + ((carrito.largo / 2) * Math.sin((Math.PI / 180) * carrito.getAnguloGrados())) - (carrito.ancho / 2) * Math.cos((Math.PI / 180) * carrito.getAnguloGrados()) + Math.PI);
                  puntosY[2] = (int) (posY - ((carrito.largo / 2) * Math.sin((Math.PI / 180) * carrito.getAnguloGrados())) - (carrito.ancho / 2) * Math.cos((Math.PI / 180) * carrito.getAnguloGrados()) + Math.PI);
                  puntosY[3] = (int) (posY - ((carrito.largo / 2) * Math.sin((Math.PI / 180) * carrito.getAnguloGrados())) + (carrito.ancho / 2) * Math.cos((Math.PI / 180) * carrito.getAnguloGrados()) + Math.PI);

                  lapiz.setColor(Color.pink);
                  lapiz.fillPolygon(puntosX, puntosY, 4);
            } else {
                  Graphics2D g2;
                  g2 = (Graphics2D) lapiz;
                  AffineTransform rot = new AffineTransform();
                  rot.setToTranslation(posX - carrito.largo / 2, posY - carrito.ancho / 2);
                  rot.rotate(Math.toRadians(-carrito.getAnguloGrados()), carrito.largo / 2, carrito.ancho / 2);
                  g2.setTransform(rot);
                  lapiz.drawImage(getImagenCarro(), 0, 0, null);
            }
      }

      public Image getImagenCarro() {
            Carro carrito = getSistema().getCarrito();
            if (imagenCarro == null) {
                  imagenCarro = new ImageIcon(getClass().getResource("/imagenes/auto.png")).getImage();
                  carrito.largo = imagenCarro.getWidth(null);
                  carrito.ancho = imagenCarro.getHeight(null);
            }
            return imagenCarro;
      }

      public void girarCarroHacia(Camino siguienteLinea) {
            Carro carrito = getSistema().getCarrito();
            int anguloInicial = (int) carrito.getAnguloGrados();
            int anguloFinal = (int) siguienteLinea.getAnguloGrados();
            int angulo;
            int factorIncremento;

            // Buscamos si incrementamos o decrementamos el angulo
            factorIncremento = Sistema.calcularCambioSentido(anguloInicial, anguloFinal);
            // Ciclo de giro
            for (angulo = anguloInicial; angulo != anguloFinal; angulo += factorIncremento) {
                  angulo = Sistema.normalizarAngulo(angulo);
                  carrito.setAnguloGrados(angulo);
                  dibujar();
                  esperar(getSistema().getVelocidad());
            }
      }

      public void restablecerComponentes() {
            getVentanaPrincipal().getBtnIniciar().setEnabled(true);
            getVentanaPrincipal().getBtnDetener().setEnabled(false);
            getVentanaPrincipal().getSliVelocidad().setMinimum(10);
            getVentanaPrincipal().getSliVelocidad().setMaximum(100);
            getVentanaPrincipal().getSliVelocidad().setMajorTickSpacing(10);
            getVentanaPrincipal().getSliVelocidad().setPaintLabels(true);
            getVentanaPrincipal().getSliVelocidad().setPaintTicks(true);
            getVentanaPrincipal().getSliVelocidad().setValue(60);
            getVentanaPrincipal().getPbrRecorrido().setValue(0);
            getVentanaPrincipal().getPbrRecorrido().setString("No hay recorrido");
            getVentanaPrincipal().getLienzo().getGraphics().clearRect(0, 0, getVentanaPrincipal().getLienzo().getWidth(), getVentanaPrincipal().getLienzo().getHeight());
      }

      public void controlarVelocidad() {
            // Controlamos la velocidad de la animacion con el slider
            setVelocidad(110 - getVentanaPrincipal().getSliVelocidad().getValue());
      }

      public void redimensionarVentanaPrincipal() {
            int ancho, alto;
            ancho = getVentanaPrincipal().getContentPane().getWidth();
            alto = getVentanaPrincipal().getContentPane().getHeight();
            getVentanaPrincipal().getLienzo().setSize(ancho - (int) getVentanaPrincipal().getLienzo().getLocation().getX() - 10, alto - (int) getVentanaPrincipal().getLienzo().getLocation().getY() - 10);
            getVentanaPrincipal().getPbrRecorrido().setSize((int) getVentanaPrincipal().getLienzo().getSize().getWidth(), (int) getVentanaPrincipal().getPbrRecorrido().getSize().getHeight());
            getVentanaPrincipal().getSliVelocidad().setSize(alto, 50);
      }
}
