/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package companydbmanagerant.view.Main;

import java.awt.BorderLayout;

/**
 *
 * @author PHC
 */
public class MainForm extends javax.swing.JPanel {

    private final FormDashboard formDashboard;
    /**
     * Creates new form MainForm
     */
    public MainForm() {
        initComponents();
        formDashboard = new FormDashboard();
        // 레이아웃 설정, 예를 들면:
        setLayout(new BorderLayout());
        // FormDashBoard를 MainForm에 추가
        add(formDashboard, BorderLayout.CENTER);
     
    }

    public FormDashboard getFormDashboard() {
        return formDashboard;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
