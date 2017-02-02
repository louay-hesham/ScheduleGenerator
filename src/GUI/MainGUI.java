package GUI;


import Core.Generator.Generator;
import Core.Generator.Subject;
import GUI.ChaosMode.SubjectPanelChaos;
import GUI.NormalMode.SubjectPanelNormal;
import GUI.Results.ResultMainGUI;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;


/*
 * Created by louay on 10/28/2016.
 */
public class MainGUI {

    private final ArrayList<SubjectPanel> subjects;
    private boolean chaosMode;
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

    private MainGUI() {
        this.chaosMode = false;
        labBiWeekCheckBox.setVisible(false);
        tutBiWeekCheckBox.setVisible(false);
        subjects = new ArrayList<>();
        this.initComponents();
    }

    public static void main(String[] args) {
        setUIFlavour();
        JFrame frame = new JFrame("Schedule Generator for Handasa SSP");
        frame.setContentPane(new MainGUI().mainPanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static void setUIFlavour() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addSubjectButtonActionPerformed() {
        if (subjectNameTextField.getText() == null || subjectNameTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Subject name can not be empty!", "Subject name error", JOptionPane.ERROR_MESSAGE);
        } else if (this.chaosMode) {
            SubjectPanel subject = new SubjectPanelChaos(
                    subjectNameTextField.getText(),
                    secondaryLectureCheckBox.isSelected(),
                    tutorialCheckBox.isSelected(),
                    tutBiWeekCheckBox.isSelected(),
                    labCheckBox.isSelected(),
                    labBiWeekCheckBox.isSelected());
            subjects.add(subject);
            subjectsTabbedPane.addTab(subjectNameTextField.getText(), subject.getMainPanel());
            subjectNameTextField.setText("");
        } else {
            SubjectPanel subject = new SubjectPanelNormal(this,
                    subjectNameTextField.getText(),
                    secondaryLectureCheckBox.isSelected(),
                    tutorialCheckBox.isSelected(),
                    tutBiWeekCheckBox.isSelected(),
                    labCheckBox.isSelected(),
                    labBiWeekCheckBox.isSelected());
            subjects.add(subject);
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
        if (this.subjects.isEmpty()) {
            this.showErrorMessage("Must have at least one subject!");
        } else {
            ArrayList<Subject> subjects = new ArrayList<>();
            for (SubjectPanel s : this.subjects) {
                subjects.add(s.getSubject());
            }
            Generator g = new Generator(subjects);
            new ResultMainGUI(g.getSchedules());
        }
    }

    //WIP for file loader
    private void loadFileButtonActionPerformed() {
        JFileChooser fileChooser = new JFileChooser();
        int returnVal = fileChooser.showOpenDialog(null);
        String path;
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            path = file.getAbsolutePath();
            System.out.println("\nYou chose to open this file: " + file.getName());
            System.out.println("In this location: " + path);
            /*FileLoader fileLoader = new FileLoader(file);
            fileLoader.loadFile(this);*/
        }
    }

    //WIP for file saver
    private void saveFileButtonActionPerformed() {
        if (this.subjects.isEmpty()) {
            this.showErrorMessage("Must have at least one subject!");
        } else {

        }
    }

    private void chaosModeButtonActionPerformed() {
        if (this.chaosMode) {
            JOptionPane.showMessageDialog(null, "Cannot go back to Normal Mode", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            this.chaosMode = true;
            this.convertTo7ebyMode();
            this.chaosModeButton.setEnabled(false);
            this.saveFileButton.setEnabled(false);
            this.loadFileButton.setEnabled(false);
        }
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

    //WIP for file loader
    public SubjectPanelNormal addSubject(String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        SubjectPanelNormal subject = new SubjectPanelNormal(this, subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        subjects.add(subject);
        subjectsTabbedPane.addTab(subjectName, subject.getMainPanel());
        subjectNameTextField.setText("");
        return subject;
    }

    public void deleteSubject(SubjectPanelNormal subject) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete the subject?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == 0) {
            this.subjects.remove(subject);
            this.subjectsTabbedPane.remove(subject.getMainPanel());
        }
    }

    private void showErrorMessage(String error) {
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void convertTo7ebyMode() {
        ArrayList<SubjectPanel> subjectsChaos = new ArrayList<>();
        for (SubjectPanel s : this.subjects) {
            this.subjectsTabbedPane.removeTabAt(0);
            SubjectPanel chaos = ((SubjectPanelNormal) s).getChaos();
            subjectsChaos.add(chaos);
            this.subjectsTabbedPane.addTab(s.getSubjectName(), chaos.getMainPanel());
        }
        this.subjects.clear();
        this.subjects.addAll(subjectsChaos);
    }
}
