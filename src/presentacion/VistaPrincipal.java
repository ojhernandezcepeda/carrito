package presentacion;


import java.awt.Canvas;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JProgressBar;
import javax.swing.JSlider;


public class VistaPrincipal extends javax.swing.JFrame {

    private ControlVistaPrincipal control;
    private final Modelo modelo;
    
    /** Creates new form CarritoAnimacion */
    public VistaPrincipal(Modelo aThis) {
        modelo = aThis;
        initComponents();
        capturaEventos();
    }    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this .method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sliVelocidad = new javax.swing.JSlider();
        lienzo = new java.awt.Canvas();
        pbrRecorrido = new javax.swing.JProgressBar();
        btnIniciar = new javax.swing.JButton();
        btnDetener = new javax.swing.JButton();
        barraMenu = new javax.swing.JMenuBar();
        mnuArchivo = new javax.swing.JMenu();
        mniNuevo = new javax.swing.JMenuItem();
        mniAbrir = new javax.swing.JMenuItem();
        mniGuardar = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        mniPreferencias = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mniSalir = new javax.swing.JMenuItem();
        mnuCreditos = new javax.swing.JMenu();
        mniAcerca = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);
        getContentPane().add(sliVelocidad);
        sliVelocidad.setBounds(120, 10, 90, 23);

        lienzo.setBackground(new java.awt.Color(0, 0, 0));
        getContentPane().add(lienzo);
        lienzo.setBounds(10, 90, 380, 190);

        pbrRecorrido.setStringPainted(true);
        getContentPane().add(pbrRecorrido);
        pbrRecorrido.setBounds(10, 60, 170, 17);

        btnIniciar.setText(">");
        btnIniciar.setName(""); // NOI18N
        getContentPane().add(btnIniciar);
        btnIniciar.setBounds(10, 10, 50, 23);

        btnDetener.setText("||");
        getContentPane().add(btnDetener);
        btnDetener.setBounds(60, 10, 50, 23);

        mnuArchivo.setText("Archivo");

        mniNuevo.setText("Nuevo");
        mnuArchivo.add(mniNuevo);

        mniAbrir.setText("Abrir");
        mnuArchivo.add(mniAbrir);

        mniGuardar.setText("Guardar");
        mnuArchivo.add(mniGuardar);
        mnuArchivo.add(jSeparator2);

        mniPreferencias.setText("Preferencias");
        mnuArchivo.add(mniPreferencias);
        mnuArchivo.add(jSeparator1);

        mniSalir.setText("Salir");
        mnuArchivo.add(mniSalir);

        barraMenu.add(mnuArchivo);

        mnuCreditos.setText("Creditos");

        mniAcerca.setText("Acerca de...");
        mnuCreditos.add(mniAcerca);

        barraMenu.add(mnuCreditos);

        setJMenuBar(barraMenu);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    public ControlVistaPrincipal getControl() {
        if(control == null){
            control = new ControlVistaPrincipal(this);
        }
        return control;
    }
    
    private void capturaEventos() {
        lienzo.addMouseListener(getControl());
        btnIniciar.addActionListener(getControl());
        btnDetener.addActionListener(getControl());
        sliVelocidad.addChangeListener(getControl());
        this.addComponentListener(getControl());
        mniNuevo.addActionListener(getControl());
        mniAbrir.addActionListener(getControl());
        mniGuardar.addActionListener(getControl());
        mniPreferencias.addActionListener(getControl());
        mniSalir.addActionListener(getControl());
        mniAcerca.addActionListener(getControl());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar barraMenu;
    private javax.swing.JButton btnDetener;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private java.awt.Canvas lienzo;
    private javax.swing.JMenuItem mniAbrir;
    private javax.swing.JMenuItem mniAcerca;
    private javax.swing.JMenuItem mniGuardar;
    private javax.swing.JMenuItem mniNuevo;
    private javax.swing.JMenuItem mniPreferencias;
    private javax.swing.JMenuItem mniSalir;
    private javax.swing.JMenu mnuArchivo;
    private javax.swing.JMenu mnuCreditos;
    private javax.swing.JProgressBar pbrRecorrido;
    private javax.swing.JSlider sliVelocidad;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnDetener() {
        return btnDetener;
    }

    public JButton getBtnIniciar() {
        return btnIniciar;
    }

    public JProgressBar getPbrRecorrido() {
        return pbrRecorrido;
    }

    public JSlider getSliVelocidad() {
        return sliVelocidad;
    }

    public JMenuItem getMniNuevo() {
        return mniNuevo;
    }

    public JMenuItem getMniAbrir() {
        return mniAbrir;
    }

    public JMenuItem getMniGuardar() {
        return mniGuardar;
    }

    public JMenuItem getMniPreferencias() {
        return mniPreferencias;
    }

    public JMenuItem getMniSalir() {
        return mniSalir;
    }
    
    public JMenu getMnuAcerca() {
        return mnuCreditos;
    }
    
    public Canvas getLienzo() {
        return lienzo;
    }

    public Modelo getModelo() {
        return modelo;
    }
    
}
