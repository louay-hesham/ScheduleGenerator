/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.NormalGenerator.Differentiator;
import Core.NormalGenerator.Generator;
import GUI.PostGenerator.ResultMainGUI;
import Core.Files.FileLoader;
import GUI.ChaosMode.SubjectPanelChaos;
import GUI.NormalMode.SubjectPanelNormal;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author louay
 */
public class MainGUI extends javax.swing.JFrame {

    private final ArrayList<SubjectPanelNormal> subjectsNormal;
    private final ArrayList<SubjectPanelChaos> subjectsChaos;
    private boolean chaosMode;

    /**
     * Creates new form MainGUI
     */
    public MainGUI() {
        super("Schedule Generator for Handasa Alex SSP");
        this.chaosMode = false;
        initComponents();
        labBiWeekCheckBox.setVisible(false);
        tutBiWeekCheckBox.setVisible(false);
        subjectsNormal = new ArrayList<>();
        subjectsChaos = new ArrayList<>();
    }

    public SubjectPanelNormal addSubject(String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        SubjectPanelNormal subject = new SubjectPanelNormal(this, subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        subjectsNormal.add(subject);
        subjectsTabbedPane.addTab(subjectName, subject);
        subjectNameTextField.setText("");
        return subject;
    }

    public String getMeetingType(String subjectName, int day, int period) throws Exception {
        for (SubjectPanelNormal subject : subjectsNormal) {
            if (subjectName.equals(subject.getSubjectName())) {
                try {
                    return subject.getMeetingType(day, period);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
        throw new Exception("Meeting Not Found!\r\nSubject name is: " + subjectName + "\r\n at day " + day + " at period " + period);
    }

    public void deleteSubject(SubjectPanelNormal subject) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete the subject?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == 0) {
            this.subjectsNormal.remove(subject);
            this.subjectsTabbedPane.remove(subject);
        }
    }

    private void startScheduleGeneration(String str) {
        ArrayList<String[][]> results = Generator.getGeneratedSchedules(str);
        try {
            Differentiator.differentiate(this, results);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            this.showErrorMessage(ex.getMessage(), "Error");
        } finally {
            generateButton.setText("Generate");
            new ResultMainGUI(results);
        }
    }

    private void saveToFile(String str) {
        String path = MainGUI.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = (new File(path)).getParentFile().getPath();
        String fileName = JOptionPane.showInputDialog(null, "Enter file name", "Save file", JOptionPane.INFORMATION_MESSAGE) + ".SGEN";
        path += (File.separator + fileName);
        try {
            PrintWriter writer = new PrintWriter(path, "UTF-8");
            writer.println(str);
            writer.println("بتعمل إيه عندك يا خلبوص؟");
            writer.println("e5la3 yad men hena");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(MainGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String generateInputString() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(this.subjectsTabbedPane.getTabCount()).append("\r\n\r\n");
        for (SubjectPanelNormal s : subjectsNormal) {
            sb.append(s.generateSubjectString()).append("\r\n");
        }
        return sb.toString();
    }

    private void showErrorMessage(String error, String title) {
        JOptionPane.showMessageDialog(null, error, title, JOptionPane.ERROR_MESSAGE);
    }

    private void convertTo7ebyMode() {
        if (this.chaosMode) {
            for (int i = 0; i < subjectsNormal.size(); i++) {
                this.subjectsTabbedPane.removeTabAt(0);
                subjectsChaos.add(subjectsNormal.get(i).getChaosVersion());
                this.subjectsTabbedPane.addTab(subjectsChaos.get(i).getSubjectName(), subjectsChaos.get(i));
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addSubjectButton = new javax.swing.JButton();
        subjectsTabbedPane = new javax.swing.JTabbedPane();
        subjectNameTextField = new javax.swing.JTextField();
        secondaryLectureCheckBox = new javax.swing.JCheckBox();
        tutorialCheckBox = new javax.swing.JCheckBox();
        tutBiWeekCheckBox = new javax.swing.JCheckBox();
        labCheckBox = new javax.swing.JCheckBox();
        labBiWeekCheckBox = new javax.swing.JCheckBox();
        generateButton = new javax.swing.JButton();
        loadFileButton = new javax.swing.JButton();
        saveFileButton = new javax.swing.JButton();
        chaosModeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        addSubjectButton.setText("Add Subject");
        addSubjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSubjectButtonActionPerformed(evt);
            }
        });

        secondaryLectureCheckBox.setText("Has a secondary lecture");

        tutorialCheckBox.setText("Tutorial");
        tutorialCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tutorialCheckBoxActionPerformed(evt);
            }
        });

        tutBiWeekCheckBox.setText("Every Two Weeks?");

        labCheckBox.setText("Lab");
        labCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                labCheckBoxActionPerformed(evt);
            }
        });

        labBiWeekCheckBox.setText("Every Two Weeks?");

        generateButton.setBackground(new java.awt.Color(51, 153, 0));
        generateButton.setText("Generate");
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        loadFileButton.setText("Load File");
        loadFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadFileButtonActionPerformed(evt);
            }
        });

        saveFileButton.setText("Save to file");
        saveFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveFileButtonActionPerformed(evt);
            }
        });

        chaosModeButton.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        chaosModeButton.setText("7eby Mode");
        chaosModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chaosModeButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(subjectsTabbedPane)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(labCheckBox)
                                            .addComponent(tutorialCheckBox))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tutBiWeekCheckBox)
                                            .addComponent(labBiWeekCheckBox)))
                                    .addComponent(secondaryLectureCheckBox)))
                            .addComponent(subjectNameTextField))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addSubjectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addComponent(chaosModeButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(loadFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(chaosModeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(tutorialCheckBox)
                                    .addComponent(tutBiWeekCheckBox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labBiWeekCheckBox)
                                    .addComponent(labCheckBox)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(subjectNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(secondaryLectureCheckBox))
                                    .addComponent(addSubjectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(saveFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(loadFileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(subjectsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addSubjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSubjectButtonActionPerformed
        if (subjectNameTextField.getText() == null || subjectNameTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Subject name can not be empty!", "Subject name error", JOptionPane.ERROR_MESSAGE);
        } else if (this.chaosMode) {
            SubjectPanelChaos subject = new SubjectPanelChaos(
                    subjectNameTextField.getText(),
                    secondaryLectureCheckBox.isSelected(),
                    tutorialCheckBox.isSelected(),
                    tutBiWeekCheckBox.isSelected(),
                    labCheckBox.isSelected(),
                    labBiWeekCheckBox.isSelected());
            subjectsChaos.add(subject);
            subjectsTabbedPane.addTab(subjectNameTextField.getText(), subject);
            subjectNameTextField.setText("");
        } else {
            SubjectPanelNormal subject = new SubjectPanelNormal(this,
                    subjectNameTextField.getText(),
                    secondaryLectureCheckBox.isSelected(),
                    tutorialCheckBox.isSelected(),
                    tutBiWeekCheckBox.isSelected(),
                    labCheckBox.isSelected(),
                    labBiWeekCheckBox.isSelected());
            subjectsNormal.add(subject);
            subjectsTabbedPane.addTab(subjectNameTextField.getText(), subject);
            subjectNameTextField.setText("");
        }
    }//GEN-LAST:event_addSubjectButtonActionPerformed

    private void tutorialCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tutorialCheckBoxActionPerformed
        tutBiWeekCheckBox.setVisible(tutorialCheckBox.isSelected());
    }//GEN-LAST:event_tutorialCheckBoxActionPerformed

    private void labCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_labCheckBoxActionPerformed
        labBiWeekCheckBox.setVisible(labCheckBox.isSelected());
    }//GEN-LAST:event_labCheckBoxActionPerformed

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        if (this.subjectsNormal.isEmpty()) {
            this.showErrorMessage("Must have at least one subject!", "Error");
        } else {
            try {
                this.startScheduleGeneration(generateInputString());
            } catch (Exception ex) {
                this.showErrorMessage(ex.getMessage(), "Error");
            }
        }
    }//GEN-LAST:event_generateButtonActionPerformed

    private void loadFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadFileButtonActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(null);
        String path;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            path = file.getAbsolutePath();
            System.out.println("\nYou chose to open this file: " + file.getName());
            System.out.println("In this location: " + path);
            FileLoader fileLoader = new FileLoader(file);
            fileLoader.loadFile(this);
        }
    }//GEN-LAST:event_loadFileButtonActionPerformed

    private void saveFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveFileButtonActionPerformed
        if (this.subjectsNormal.isEmpty()) {
            this.showErrorMessage("Must have at least one subject!", "Error");
        } else {
            try {
                this.saveToFile(generateInputString());
            } catch (Exception ex) {
                this.showErrorMessage(ex.getMessage(), "Error");
            }
        }
    }//GEN-LAST:event_saveFileButtonActionPerformed

    private void chaosModeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chaosModeButtonActionPerformed
        if (this.chaosMode) {
            JOptionPane.showMessageDialog(null, "Cannot go back to Normal Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            this.chaosMode = true;
            this.convertTo7ebyMode();
            this.chaosModeButton.setEnabled(false);
        }
    }//GEN-LAST:event_chaosModeButtonActionPerformed

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
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addSubjectButton;
    private javax.swing.JButton chaosModeButton;
    private javax.swing.JButton generateButton;
    private javax.swing.JCheckBox labBiWeekCheckBox;
    private javax.swing.JCheckBox labCheckBox;
    private javax.swing.JButton loadFileButton;
    private javax.swing.JButton saveFileButton;
    private javax.swing.JCheckBox secondaryLectureCheckBox;
    private javax.swing.JTextField subjectNameTextField;
    private javax.swing.JTabbedPane subjectsTabbedPane;
    private javax.swing.JCheckBox tutBiWeekCheckBox;
    private javax.swing.JCheckBox tutorialCheckBox;
    // End of variables declaration//GEN-END:variables
}
