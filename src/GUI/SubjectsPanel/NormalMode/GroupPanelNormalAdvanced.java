package GUI.SubjectsPanel.NormalMode;

import Core.Subject.MeetingType;
import Core.Subject.NormalMode.GroupNormal;
import Core.Subject.Time;
import GUI.SubjectsPanel.SubjectInfo;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by louay on 2/5/2017.
 */
public class GroupPanelNormalAdvanced extends GroupPanel {
    private JPanel mainPanel;
    private JComboBox lectureDayComboBox;
    private JSpinner lectureFromSpinner;
    private JLabel tut1Label;
    private JComboBox tut1DayComboBox;
    private JSpinner tut1FromSpinner;
    private JComboBox tut2DayComboBox;
    private JSpinner tut2FromSpinner;
    private JButton deleteGroupButton;
    private JLabel lab1Label;
    private JComboBox lab1DayComboBox;
    private JSpinner lab1FromSpinner;
    private JComboBox lab2DayComboBox;
    private JSpinner lab2FromSpinner;
    private JLabel secLecLabel;
    private JComboBox secLectureDayComboBox;
    private JSpinner secLectureFromSpinner;
    private JSpinner lectureToSpinner;
    private JSpinner secLectureToSpinner;
    private JSpinner tut1ToSpinner;
    private JSpinner tut2ToSpinner;
    private JSpinner lab1ToSpinner;
    private JSpinner lab2ToSpinner;
    private JLabel secLectureFromLabel;
    private JLabel secLectureToLabel;
    private JLabel tut1FromLabel;
    private JLabel tut2Label;
    private JLabel lab2Label;
    private JLabel tut1ToLabel;
    private JLabel tut2FromLabel;
    private JLabel tut2ToLabel;
    private JLabel lab1FromLabel;
    private JLabel lab2FromLabel;
    private JLabel lab1ToLabel;
    private JLabel lab2ToLabel;

    GroupPanelNormalAdvanced(SubjectPanelNormal subject, SubjectInfo subjectInfo){
        super(subject, subjectInfo);
        this.initComponents();
    }

    protected void initComponents() {
        secLectureDayComboBox.setVisible(subjectInfo.secLecExists);
        secLectureFromLabel.setVisible(subjectInfo.secLecExists);
        secLectureToLabel.setVisible(subjectInfo.secLecExists);
        secLectureFromSpinner.setVisible(subjectInfo.secLecExists);
        secLectureToSpinner.setVisible(subjectInfo.secLecExists);
        secLecLabel.setVisible(subjectInfo.secLecExists);

        tut2Label.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2DayComboBox.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2FromSpinner.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2ToSpinner.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2FromLabel.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2ToLabel.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        if (subjectInfo.tutBiWeek) {
            tut1Label.setText("Tutorial");
        } else {
            tut1Label.setText("Tutorial 1");
        }
        tut1Label.setVisible(subjectInfo.tutExists);
        tut1DayComboBox.setVisible(subjectInfo.tutExists);
        tut1FromSpinner.setVisible(subjectInfo.tutExists);
        tut1ToSpinner.setVisible(subjectInfo.tutExists);
        tut1FromLabel.setVisible(subjectInfo.tutExists);
        tut1ToLabel.setVisible(subjectInfo.tutExists);

        lab2Label.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2DayComboBox.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2FromSpinner.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2ToSpinner.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2FromLabel.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2ToLabel.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        if (subjectInfo.labBiWeek) {
            lab1Label.setText("Lab");
        } else {
            lab1Label.setText("Lab 1");
        }
        lab1Label.setVisible(subjectInfo.labExists);
        lab1DayComboBox.setVisible(subjectInfo.labExists);
        lab1FromSpinner.setVisible(subjectInfo.labExists);
        lab1ToSpinner.setVisible(subjectInfo.labExists);
        lab1FromLabel.setVisible(subjectInfo.labExists);
        lab1ToLabel.setVisible(subjectInfo.labExists);

        deleteGroupButton.addActionListener(e -> deleteGroupButtonActionPerformed());
        lectureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        secLectureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        tut1DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        tut2DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lab1DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lab2DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lectureFromSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        lectureToSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        secLectureFromSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        secLectureToSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        tut1FromSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        tut1ToSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        tut2FromSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        tut2ToSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        lab1FromSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        lab1ToSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        lab2FromSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        lab2ToSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
    }

    protected GroupNormal getGroup(){
        Time lecture = new Time(this.lectureDayComboBox.getSelectedIndex(),
                (int) this.lectureFromSpinner.getValue() - 1,
                (int) this.lectureToSpinner.getValue() - 1,
                MeetingType.LECTURE);
        Time secLecture = null;
        if (this.subjectInfo.secLecExists) {
            secLecture = new Time(this.secLectureDayComboBox.getSelectedIndex(),
                    (int) this.secLectureFromSpinner.getValue() - 1,
                    (int) this.secLectureToSpinner.getValue() - 1,
                    MeetingType.SEC_LECTURE);
        }

        ArrayList<Time> tutorials = null, labs = null;
        if (this.subjectInfo.tutExists) {
            tutorials = new ArrayList<>();
            Time tut1 = new Time(this.tut1DayComboBox.getSelectedIndex(),
                    (int) this.tut1FromSpinner.getValue() - 1,
                    (int) this.tut1ToSpinner.getValue() - 1,
                    MeetingType.TUT_UNKNOWN);
            tutorials.add(tut1);
            if (!this.subjectInfo.tutBiWeek) {
                Time tut2 = new Time(this.tut2DayComboBox.getSelectedIndex(),
                        (int) this.tut2FromSpinner.getValue() - 1,
                        (int) this.tut2ToSpinner.getValue() - 1,
                        MeetingType.TUT_UNKNOWN);
                tutorials.add(tut2);
            }
        }

        if (this.subjectInfo.labExists) {
            labs = new ArrayList<>();
            Time lab1 = new Time(this.lab1DayComboBox.getSelectedIndex(),
                    (int) this.lab1FromSpinner.getValue() - 1,
                    (int) this.lab1ToSpinner.getValue() - 1,
                    MeetingType.LAB_UNKNOWN);
            labs.add(lab1);
            if (!this.subjectInfo.labBiWeek) {
                Time lab2 = new Time(this.lab2DayComboBox.getSelectedIndex(),
                        (int) this.lab2FromSpinner.getValue() - 1,
                        (int) this.lab2ToSpinner.getValue() - 1,
                        MeetingType.LAB_UNKNOWN);
                labs.add(lab2);
            }
        }
        return new GroupNormal(lecture, secLecture, tutorials, labs);
    }

    protected void setGroup(GroupNormal group) {
        this.lectureDayComboBox.setSelectedIndex(group.getLecture().day);
        this.lectureFromSpinner.setValue(group.getLecture().from + 1);
        this.lectureToSpinner.setValue(group.getLecture().to + 1);
        if (this.subjectInfo.secLecExists) {
            this.secLectureDayComboBox.setSelectedIndex(group.getSecLecture().day);
            this.secLectureFromSpinner.setValue(group.getSecLecture().from + 1);
            this.secLectureToSpinner.setValue(group.getSecLecture().to + 1);
        }

        if (this.subjectInfo.tutExists) {
            this.tut1DayComboBox.setSelectedIndex(group.getTutorials().get(0).day);
            this.tut1FromSpinner.setValue(group.getTutorials().get(0).from + 1);
            this.tut1ToSpinner.setValue(group.getTutorials().get(0).to + 1);
            if (!this.subjectInfo.tutBiWeek) {
                this.tut2DayComboBox.setSelectedIndex(group.getTutorials().get(1).day);
                this.tut2FromSpinner.setValue(group.getTutorials().get(1).from + 1);
                this.tut2ToSpinner.setValue(group.getTutorials().get(1).to + 1);
            }
        }

        if (this.subjectInfo.labExists) {
            this.lab1DayComboBox.setSelectedIndex(group.getLabs().get(0).day);
            this.lab1FromSpinner.setValue(group.getLabs().get(0).from + 1);
            this.lab1ToSpinner.setValue(group.getLabs().get(0).to + 1);
            if (!this.subjectInfo.labBiWeek) {
                this.lab2DayComboBox.setSelectedIndex(group.getLabs().get(1).day);
                this.lab2FromSpinner.setValue(group.getLabs().get(1).from + 1);
                this.lab2ToSpinner.setValue(group.getLabs().get(1).to + 1);
            }
        }
    }

    @Override
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
