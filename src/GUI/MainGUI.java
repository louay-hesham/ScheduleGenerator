package GUI;


import Core.Files.FileLoader;
import Core.CPlusPlusGenerator.Differentiator;
import Core.CPlusPlusGenerator.Generator;
import GUI.ChaosMode.SubjectPanelChaos;
import GUI.NormalMode.SubjectPanelNormal;
import GUI.PostGenerator.ResultMainGUI;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/*
 * Created by louay on 10/28/2016.
 */
public class MainGUI {

    private final ArrayList<SubjectPanelNormal> subjectsNormal;
    private final ArrayList<SubjectPanelChaos> subjectsChaos;
    private boolean chaosMode;

    private static void setUIFlavour() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        setUIFlavour();
        JFrame frame = new JFrame("Schedule Generator for Handasa SSP");
        frame.setContentPane(new MainGUI().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void addSubjectButtonActionPerformed() {
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
            subjectsTabbedPane.addTab(subjectNameTextField.getText(), subject.getMainPanel());
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
            subjectsTabbedPane.addTab(subjectNameTextField.getText(), subject.getMainPanel());
            subjectNameTextField.setText("");
        }
    }

    private void tutorialCheckBoxActionPerformed() {
        tutBiWeekCheckBox.setVisible(tutorialCheckBox.isSelected());
    }

    private void labCheckBoxActionPerformed() {
        labBiWeekCheckBox.setVisible(labCheckBox.isSelected());
    }

    private void generateButtonActionPerformed() {
        if (this.subjectsNormal.isEmpty()) {
            this.showErrorMessage("Must have at least one subject!");
        } else {
            try {
                this.startScheduleGeneration(generateInputString());
            } catch (Exception ex) {
                this.showErrorMessage(ex.getMessage());
            }
        }
    }

    private void loadFileButtonActionPerformed() {
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
    }

    private void saveFileButtonActionPerformed() {
        if (this.subjectsNormal.isEmpty()) {
            this.showErrorMessage("Must have at least one subject!");
        } else {
            try {
                this.saveToFile(generateInputString());
            } catch (Exception ex) {
                this.showErrorMessage(ex.getMessage());
            }
        }
    }

    private void chaosModeButtonActionPerformed() {
        if (this.chaosMode) {
            JOptionPane.showMessageDialog(null, "Cannot go back to Normal Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            this.chaosMode = true;
            this.convertTo7ebyMode();
            this.chaosModeButton.setEnabled(false);
            this.generateButton.setEnabled(false);
        }
    }

    private MainGUI() {
        this.chaosMode = false;
        labBiWeekCheckBox.setVisible(false);
        tutBiWeekCheckBox.setVisible(false);
        subjectsNormal = new ArrayList<>();
        subjectsChaos = new ArrayList<>();
        this.initComponents();
    }

    private void initComponents() {
        addSubjectButton.addActionListener(e -> addSubjectButtonActionPerformed());
        tutorialCheckBox.addActionListener(e -> tutorialCheckBoxActionPerformed());
        labCheckBox.addActionListener(e -> labCheckBoxActionPerformed());
        generateButton.addActionListener(e -> generateButtonActionPerformed());
        loadFileButton.addActionListener(e -> loadFileButtonActionPerformed());
        saveFileButton.addActionListener(e -> saveFileButtonActionPerformed());
        chaosModeButton.addActionListener(e -> chaosModeButtonActionPerformed());
    }

    public SubjectPanelNormal addSubject(String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        SubjectPanelNormal subject = new SubjectPanelNormal(this, subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        subjectsNormal.add(subject);
        subjectsTabbedPane.addTab(subjectName, subject.getMainPanel());
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
            this.subjectsTabbedPane.remove(subject.getMainPanel());
        }
    }

    private void startScheduleGeneration(String str) {
        ArrayList<String[][]> results = Generator.getGeneratedSchedules(str);
        try {
            Differentiator.differentiate(this, results);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            this.showErrorMessage(ex.getMessage());
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

    private void showErrorMessage(String error) {
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void convertTo7ebyMode() {
        if (this.chaosMode) {
            for (int i = 0; i < subjectsNormal.size(); i++) {
                this.subjectsTabbedPane.removeTabAt(0);
                subjectsChaos.add(subjectsNormal.get(i).getChaosVersion());
                this.subjectsTabbedPane.addTab(subjectsChaos.get(i).getSubjectName(), subjectsChaos.get(i).getMainPanel());
            }
        }
    }

    private JPanel mainPanel;
    private JTextField subjectNameTextField;
    private JCheckBox secondaryLectureCheckBox;
    private JCheckBox tutorialCheckBox;
    private JCheckBox tutBiWeekCheckBox;
    private JCheckBox labCheckBox;
    private JCheckBox labBiWeekCheckBox;
    private JButton addSubjectButton;
    private JButton generateButton;
    private JButton chaosModeButton;
    private JButton saveFileButton;
    private JButton loadFileButton;
    @SuppressWarnings("unused")
    private JLabel subjectNameLabel;
    private JTabbedPane subjectsTabbedPane;
}
