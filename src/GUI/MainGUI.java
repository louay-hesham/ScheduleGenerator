package GUI;


import Core.FileIO.Loader.FileLoader;
import Core.FileIO.Saver.FileSaver;
import Core.Generator.Generator;
import Core.Subject.Subject;
import GUI.ResultsWindow.ResultMainGUI;
import GUI.SubjectsPanel.ChaosMode.SubjectPanelChaos;
import GUI.SubjectsPanel.NormalMode.SubjectPanelNormal;
import GUI.SubjectsPanel.SubjectPanel;

import javax.swing.*;
import java.util.ArrayList;


/*
 * Created by louay on 10/28/2016.
 */
public class MainGUI {

    private final ArrayList<SubjectPanel> subjects;
    private boolean chaosMode, advanced;
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
    private JButton goAdvancedButton;

    private MainGUI() {
        this.chaosMode = false;
        this.advanced = false;
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
        } else {
            SubjectPanel subject;
            if (this.chaosMode) {
                subject = new SubjectPanelChaos( this,
                        this.subjectNameTextField.getText(),
                        this.secondaryLectureCheckBox.isSelected(),
                        this.tutorialCheckBox.isSelected(),
                        this.tutBiWeekCheckBox.isSelected(),
                        this.labCheckBox.isSelected(),
                        this.labBiWeekCheckBox.isSelected());
            } else {
                subject = new SubjectPanelNormal(this,
                        this.subjectNameTextField.getText(),
                        this.secondaryLectureCheckBox.isSelected(),
                        this.tutorialCheckBox.isSelected(),
                        this.tutBiWeekCheckBox.isSelected(),
                        this.labCheckBox.isSelected(),
                        this.labBiWeekCheckBox.isSelected());
            }
            this.subjects.add(subject);
            this.subjectsTabbedPane.addTab(subjectNameTextField.getText(), subject.getMainPanel());
            this.resetInputFields();
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

    private void loadFileButtonActionPerformed() {
        try {
            FileLoader fl = new FileLoader(this);
            ArrayList<SubjectPanel> loadedSubjects = fl.getPanels();
            this.subjects.addAll(loadedSubjects);
            for (SubjectPanel s : loadedSubjects) {
                subjectsTabbedPane.addTab(s.getSubjectName(), s.getMainPanel());
            }
        } catch (Exception e) {
            this.showErrorMessage(e.getMessage());
        }
    }

    private void saveFileButtonActionPerformed() {
        if (this.subjects.isEmpty()) {
            this.showErrorMessage("Must have at least one subject!");
        } else {
            FileSaver fs = new FileSaver(this.subjects, this.chaosMode ? "7eby" : "Normal");
            fs.saveFile();
        }
    }

    private void chaosModeButtonActionPerformed() {
        if (this.showConversionDialog() == 0) {
            this.convert();
        }
    }

    private void advancedButtonActionPerformed(){
        this.advanced = !this.advanced;
        this.goAdvancedButton.setText(this.advanced? "Disable Advanced Mode" : "Enable Advanced Mode");
        for (SubjectPanel s : this.subjects){
            s.advancedModeConvert();
        }
    }

    private void convert() {
        if (!this.chaosMode) {
            this.convertTo7ebyMode();
        } else {
            this.convertToNormalMode();
        }
    }

    private int showConversionDialog() {
        int choice;
        if (!this.chaosMode) {
            choice = JOptionPane.showConfirmDialog(null,
                    "Going to 7eby mode, you can't go back unless the program resets.\r\nAre you sure you want to continue?",
                    "Converting to 7eby mode",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
        } else {
            choice = JOptionPane.showConfirmDialog(null,
                    "Going to normal mode (website registration), the program must reset to do this.\r\nAre you sure you want to continue?",
                    "Converting to normal mode",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);
        }
        return choice;
    }

    private void initComponents() {
        addSubjectButton.addActionListener(e -> addSubjectButtonActionPerformed());
        tutorialCheckBox.addActionListener(e -> tutorialCheckBoxActionPerformed());
        labCheckBox.addActionListener(e -> labCheckBoxActionPerformed());
        generateButton.addActionListener(e -> generateButtonActionPerformed());
        loadFileButton.addActionListener(e -> loadFileButtonActionPerformed());
        saveFileButton.addActionListener(e -> saveFileButtonActionPerformed());
        chaosModeButton.addActionListener(e -> chaosModeButtonActionPerformed());
        goAdvancedButton.addActionListener(e -> advancedButtonActionPerformed());
    }

    private void resetInputFields() {
        this.subjectNameTextField.setText("");
        this.secondaryLectureCheckBox.setSelected(false);
        this.tutorialCheckBox.setSelected(false);
        this.tutBiWeekCheckBox.setSelected(false);
        this.labCheckBox.setSelected(false);
        this.labBiWeekCheckBox.setSelected(false);
    }

    private void reset() {
        for (int i = 0; i < this.subjects.size(); i++) {
            this.subjectsTabbedPane.removeTabAt(0);
        }
        this.subjects.clear();
        this.resetInputFields();
    }

    private void showErrorMessage(String error) {
        JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void convertToNormalMode() {
        if (this.chaosMode) {
            this.chaosMode = false;
            this.chaosModeButton.setText("Convert to 7eby mode");
            this.reset();
        }
    }

    public void convertTo7ebyMode() {
        if (!this.chaosMode) {
            this.chaosMode = true;
            this.chaosModeButton.setText("Convert to normal mode");
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

    public void deleteSubject(SubjectPanel subject) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete the subject?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == 0) {
            this.subjects.remove(subject);
            this.subjectsTabbedPane.remove(subject.getMainPanel());
        }
    }
}
