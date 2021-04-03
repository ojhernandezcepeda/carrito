/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VistaPreferencias.java
 *
 * Created on 18/04/2011, 05:57:08 PM
 */

package presentacion;

import javax.swing.JButton;
import javax.swing.JRadioButton;

/**
 *
 * @author oaromerov
 */
public class VistaPreferencias extends javax.swing.JDialog {
    private final Modelo modeloSistema;
    private ControlVistaPreferencias control;

    public ControlVistaPreferencias getControl() {
        if(control == null){
            control = new ControlVistaPreferencias(this);
        }
        return control;
    }

    public VistaPreferencias(Modelo aThis) {
        modeloSistema = aThis;        
        this.setModal(true);
        initComponents();
        asignarListener();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCerrar = new javax.swing.JButton();
        pnlGuardando = new javax.swing.JPanel();
        rbtnTexto = new javax.swing.JRadioButton();
        rbtnBinario = new javax.swing.JRadioButton();
        pnlAnimacion = new javax.swing.JPanel();
        rbtnLineas = new javax.swing.JRadioButton();
        rbtnImagen = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Preferencias");
        setAlwaysOnTop(true);
        setName("ventanaPreferencias"); // NOI18N
        setResizable(false);
        getContentPane().setLayout(null);

        btnCerrar.setText("Cerrar");
        getContentPane().add(btnCerrar);
        btnCerrar.setBounds(330, 210, 160, 40);

        pnlGuardando.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Guardando", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18))); // NOI18N

        rbtnTexto.setText("Archivo con texto plano");

        rbtnBinario.setText("Archivo binario");

        javax.swing.GroupLayout pnlGuardandoLayout = new javax.swing.GroupLayout(pnlGuardando);
        pnlGuardando.setLayout(pnlGuardandoLayout);
        pnlGuardandoLayout.setHorizontalGroup(
            pnlGuardandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGuardandoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rbtnTexto)
                .addGap(54, 54, 54)
                .addComponent(rbtnBinario)
                .addContainerGap(72, Short.MAX_VALUE))
        );
        pnlGuardandoLayout.setVerticalGroup(
            pnlGuardandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlGuardandoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlGuardandoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnTexto)
                    .addComponent(rbtnBinario))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(pnlGuardando);
        pnlGuardando.setBounds(10, 110, 480, 90);

        pnlAnimacion.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(""), "Animando", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 18))); // NOI18N

        rbtnLineas.setText("Dibujar con trazo de lineas");

        rbtnImagen.setText("Dibujar una imagen");

        javax.swing.GroupLayout pnlAnimacionLayout = new javax.swing.GroupLayout(pnlAnimacion);
        pnlAnimacion.setLayout(pnlAnimacionLayout);
        pnlAnimacionLayout.setHorizontalGroup(
            pnlAnimacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnimacionLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rbtnLineas)
                .addGap(37, 37, 37)
                .addComponent(rbtnImagen)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        pnlAnimacionLayout.setVerticalGroup(
            pnlAnimacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAnimacionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAnimacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtnLineas)
                    .addComponent(rbtnImagen))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        getContentPane().add(pnlAnimacion);
        pnlAnimacion.setBounds(10, 10, 480, 90);

        getAccessibleContext().setAccessibleParent(this);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void asignarListener() {
        btnCerrar.addActionListener(getControl());
        rbtnTexto.addActionListener(getControl());
        rbtnBinario.addActionListener(getControl());
        rbtnImagen.addActionListener(getControl());
        rbtnLineas.addActionListener(getControl());
    }
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCerrar;
    private javax.swing.JPanel pnlAnimacion;
    private javax.swing.JPanel pnlGuardando;
    private javax.swing.JRadioButton rbtnBinario;
    private javax.swing.JRadioButton rbtnImagen;
    private javax.swing.JRadioButton rbtnLineas;
    private javax.swing.JRadioButton rbtnTexto;
    // End of variables declaration//GEN-END:variables

    public JButton getBtnCerrar() {
        return btnCerrar;
    }

    public JRadioButton getRbtnBinario() {
        return rbtnBinario;
    }

    public JRadioButton getRbtnImagen() {
        return rbtnImagen;
    }

    public JRadioButton getRbtnLineas() {
        return rbtnLineas;
    }

    public JRadioButton getRbtnTexto() {
        return rbtnTexto;
    }

    public Modelo getModeloSistema() {
        return modeloSistema;
    }
    
}
