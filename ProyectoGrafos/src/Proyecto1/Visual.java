/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Proyecto1;

import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gabriela Diaz R
 */
public class Visual extends javax.swing.JFrame {

    /**
     * Creates new form Visual
     */
    protected static mxGraph grafico;
    private mxGraphComponent Componentes;
    JComboBox opcion1 = new JComboBox();
    JComboBox opcion2 = new JComboBox();
    DefaultTableModel n = new DefaultTableModel();
    JTable tableDistance = new JTable(n);
    DefaultTableModel n1 = new DefaultTableModel();
    JTable tableResultados = new JTable(n1);
    String resultado;
    Object parent;
    int numeroVertice = 0;
    boolean confirma = false;
    ArrayList<String> camino = new ArrayList<String>();
    ArrayList<ParVertice> pares = new ArrayList<ParVertice>();
    int listo=0;
    public Visual() {
        initComponents();
        initGUI();
    }

    private void initGUI() {
        grafico = new mxGraph();
        Componentes = new mxGraphComponent(grafico);
        Componentes.setBounds(20, 120, 400, 530);
        Componentes.getViewport().setBackground(Color.white);
        getContentPane().add(Componentes);
        getContentPane().setBackground(Color.white);

        setTitle("GRAFICADOR DE GRAFOS");
        //  setLayout(new FlowLayout(FlowLayout.CENTER));
        parent = grafico.getDefaultParent();
    }

    public String definicionGrafo() {
        int veri = 0;
        listo=0;
        ArrayList<ParVertice> par = new ArrayList<ParVertice>();
        String def = "G = { { ";
        for (int x = 0; x < Main.grafos.Nodos.size(); x++) {
            if (x != Main.grafos.Nodos.size() - 1) {
                def += Main.grafos.Nodos.get(x).valor + " , ";
            } else {
                def += Main.grafos.Nodos.get(x).valor + " } , { ";
            }
        }
        if (pares.size() != 0) {
            for (int y = 0; y < Main.grafos.Nodos.size(); y++) {

                for (int x = 0; x < Main.grafos.Nodos.get(y).conexiones.size(); x++) {

                    if (buscaPares2(par, Main.grafos.Nodos.get(y).valor, Main.grafos.Nodos.get(y).conexiones.get(x)) == false) {
                        if (veri == 0) {
                        if (x != Main.grafos.Nodos.get(y).conexiones.size() - 1) {
                            ParVertice pv1 = new ParVertice(Main.grafos.Nodos.get(y).valor, Main.grafos.Nodos.get(y).conexiones.get(x));
                            par.add(pv1);
                            def += " ( " + Main.grafos.Nodos.get(y).valor + " , " + Main.grafos.Nodos.get(y).conexiones.get(x) + " )  , ";
                        } else {
                            ParVertice pv1 = new ParVertice(Main.grafos.Nodos.get(y).valor, Main.grafos.Nodos.get(y).conexiones.get(x));
                            par.add(pv1);
                            def += " ( " + Main.grafos.Nodos.get(y).valor + " , " + Main.grafos.Nodos.get(y).conexiones.get(x) + " ) ";
                        }
                       }
                        else{
                          
                            ParVertice pv1 = new ParVertice(Main.grafos.Nodos.get(y).valor, Main.grafos.Nodos.get(y).conexiones.get(x));
                            par.add(pv1);
                            def += " , ( " + Main.grafos.Nodos.get(y).valor + " , " + Main.grafos.Nodos.get(y).conexiones.get(x) + " ) ";
                        
                    }
                          
                    }
                    
                }
                if(listo==1){
                    veri=1;
                    
                }

            }
        }

        def += " } } ";

        return def;
    }

    public void popOut() {

        resultado = String.valueOf(numeroVertice);
        grafico.getModel().beginUpdate();
        Vertice nuevo = new Vertice(resultado);
        Object v1 = grafico.insertVertex(parent, "v1", resultado, 0, 0, 60, 40, "shape=ellipse;strokeColor=black;fillColor=yellow;fontSize=20");
        numeroVertice += 1;
        nuevo.nodoVisual = v1;
        grafico.getModel().endUpdate();
        Main.grafos.Nodos.add(nuevo);
        jTextArea3.setText("");
        String definicionG = definicionGrafo();
        jTextArea3.append(definicionG);

    }

    public void popOut2() {

        jFrame2.setSize(150, 200);
        jFrame2.setLocationRelativeTo(null);
        jFrame2.setResizable(false);
        jFrame2.setLayout(null);
        jFrame2.getContentPane().setBackground(Color.white);
        opcion1.setBounds(30, 35, 70, 20);
        opcion2.setBounds(30, 75, 70, 20);
        JButton boton = new JButton("OK");
        boton.setBounds(30, 105, 70, 20);
        jFrame2.add(boton);

        JLabel titulo = new JLabel();
        JLabel titulo2 = new JLabel();
        titulo.setText("Origen= ");
        titulo.setBounds(30, 0, 50, 50);
        titulo2.setText("Destino= ");
        titulo2.setBounds(30, 40, 90, 50);

        jFrame2.add(titulo);
        jFrame2.add(titulo2);
        opcion1.removeAllItems();
        opcion2.removeAllItems();
        for (Vertice llenado : Main.grafos.Nodos) {
            opcion1.addItem(llenado.valor);
            opcion2.addItem(llenado.valor);
        }
        jFrame2.add(opcion1);
        jFrame2.add(opcion2);
        jFrame2.setVisible(true);

        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent ae) {
                if (buscaPares(opcion1.getSelectedItem().toString(), opcion2.getSelectedItem().toString()) == false) {
                    Vertice tmp = Main.grafos.Buscar2(opcion1.getSelectedItem().toString());
                    Vertice tmp2 = Main.grafos.Buscar2(opcion2.getSelectedItem().toString());
                    ParVertice pv = new ParVertice(opcion1.getSelectedItem().toString(), opcion2.getSelectedItem().toString());
                    pares.add(pv);
                    Main.grafos.AgregarArista(tmp.valor, tmp2.valor, 1);
                    grafico.insertEdge(parent, null,"", tmp.nodoVisual, tmp2.nodoVisual, "fontSize=15;fontColor=black;strokeColor=black;endArrow=none");
                    jTextArea3.setText("");
                    String definicionG = definicionGrafo();
                    jTextArea3.append(definicionG);
                    jFrame2.setVisible(false);
                }
            }
        });
    }

    public Boolean buscaPares(String origen, String destino) {
        for (ParVertice pv : pares) {
            if (pv.vertice1.equals(origen) && pv.vertice2.equals(destino) || pv.vertice1.equals(destino) && pv.vertice2.equals(origen)) {
                return true;
            }
        }
        return false;
    }

    public Boolean buscaPares2(ArrayList<ParVertice> tmp, String origen, String destino) {
        for (ParVertice pv : tmp) {
            if (pv.vertice1.equals(origen) && pv.vertice2.equals(destino) || pv.vertice1.equals(destino) && pv.vertice2.equals(origen)) {
             
                return true;
            }
        }
        listo=1;
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jFrame2 = new javax.swing.JFrame();
        jFrame3 = new javax.swing.JFrame();
        jFrame4 = new javax.swing.JFrame();
        jFrame5 = new javax.swing.JFrame();
        jLabel4 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(800, 720));
        getContentPane().setLayout(null);

        jLabel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Resultados", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N
        getContentPane().add(jLabel4);
        jLabel4.setBounds(450, 400, 300, 230);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proyecto1/cross.png"))); // NOI18N
        jButton1.setText("Nodo");
        jButton1.setToolTipText("");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(510, 50, 190, 30);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proyecto1/line.png"))); // NOI18N
        jButton2.setText("Linea");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(510, 150, 190, 30);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proyecto1/erare.png"))); // NOI18N
        jButton3.setText("Erase");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton3MouseClicked(evt);
            }
        });
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(510, 100, 190, 30);

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proyecto1/check.png"))); // NOI18N
        jButton4.setText("Generar Respuesta");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton4MouseClicked(evt);
            }
        });
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(460, 440, 280, 30);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(460, 230, 90, 30);

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proyecto1/cross.png"))); // NOI18N
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton5MouseClicked(evt);
            }
        });
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton5);
        jButton5.setBounds(560, 230, 50, 29);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(460, 270, 270, 100);

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(460, 480, 280, 120);

        jLabel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Definicion", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N
        getContentPane().add(jLabel5);
        jLabel5.setBounds(20, 10, 400, 90);

        jLabel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Camino", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N
        getContentPane().add(jLabel6);
        jLabel6.setBounds(450, 210, 300, 190);

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proyecto1/check.png"))); // NOI18N
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton6MouseClicked(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(620, 230, 50, 29);

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Proyecto1/erare.png"))); // NOI18N
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton7MouseClicked(evt);
            }
        });
        getContentPane().add(jButton7);
        jButton7.setBounds(680, 230, 50, 30);

        jTextArea3.setColumns(20);
        jTextArea3.setRows(5);
        jScrollPane3.setViewportView(jTextArea3);

        getContentPane().add(jScrollPane3);
        jScrollPane3.setBounds(30, 40, 380, 40);

        jLabel7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Propiedades", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N
        getContentPane().add(jLabel7);
        jLabel7.setBounds(450, 10, 300, 190);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        // TODO add your handling code here:

        popOut();


    }//GEN-LAST:event_jButton1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked
        // TODO add your handling code here:
        popOut2();

    }//GEN-LAST:event_jButton2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MouseClicked
        // TODO add your handling code here:
        jTextArea2.setText("");
        int gradoGrafo = Main.grafos.gradoGrafo();
        jTextArea2.append("GRADO DEL GRAFO = " + gradoGrafo + "\n");
        int sumaGrado = Main.grafos.sumaGrafo();
        jTextArea2.append("SUMA DE LOS GRADOS DE LOS VERTICES = " + sumaGrado + "\n");
        int menorVertice = Main.grafos.menorVertice();
        jTextArea2.append("GRADO MENOR ENTRE LOS VERTICES = " + menorVertice + "\n");
        Boolean Ciclos = Main.grafos.verificacion();
        if (Ciclos == true) {
            jTextArea2.append("===== EXISTEN CICLOS EN EL GRAFO ===== \n");
        } else {
            jTextArea2.append("===== NO EXISTEN CICLOS EN EL GRAFO ===== \n");
        }
    }//GEN-LAST:event_jButton4MouseClicked

    private void jButton3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MouseClicked
        // TODO add your handling code here:
        grafico.selectAll();
        grafico.removeCells();
        Main.grafos.Nodos.clear();
        pares.clear();
        camino.clear();
        numeroVertice = 0;
        jTextArea1.setText("");
        jTextArea2.setText("");
        jTextArea3.setText("");
        jTextField1.setText("");

    }//GEN-LAST:event_jButton3MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MouseClicked
        Boolean existe = Main.grafos.Buscar(jTextField1.getText());
        if (camino.contains(jTextField1.getText()) == false && existe == true) {
            camino.add(jTextField1.getText());
            jTextArea1.append(jTextField1.getText() + "\n");

        }
        jTextField1.setText("");


    }//GEN-LAST:event_jButton5MouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MouseClicked
        if (camino.size() != 0) {

            Boolean caminoValido = Main.grafos.caminoValido(camino);
            if (caminoValido == true) {
                for (int x = 0; x < camino.size() - 1; x++) {
                    Vertice remarcar1;
                    Vertice remarcar2;
                    remarcar1 = Main.grafos.Buscar2(camino.get(x));
                    remarcar2 = Main.grafos.Buscar2(camino.get(x + 1));
                    grafico.insertEdge(parent, null, "", remarcar1.nodoVisual, remarcar2.nodoVisual, "endArrow=none;strokeColor=yellow");
                }
            } else {
                JOptionPane.showMessageDialog(null, "CAMINO NO VALIDO", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        camino.clear();
        jTextArea1.setText("");
    }//GEN-LAST:event_jButton6MouseClicked

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseClicked
        camino.clear();
        for (ParVertice pv : pares) {
            Vertice tmp = Main.grafos.Buscar2(pv.vertice1);
            Vertice tmp2 = Main.grafos.Buscar2(pv.vertice2);
            grafico.insertEdge(parent, null, "", tmp.nodoVisual, tmp2.nodoVisual, "fontSize=15;fontColor=black;strokeColor=gray;endArrow=none");
            jTextArea1.setText("");
            jTextField1.setText("");

        }
    }//GEN-LAST:event_jButton7MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Visual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JFrame jFrame3;
    private javax.swing.JFrame jFrame4;
    private javax.swing.JFrame jFrame5;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
