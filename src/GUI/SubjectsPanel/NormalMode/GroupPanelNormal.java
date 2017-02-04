package GUI.SubjectsPanel.NormalMode;

import Core.Subject.MeetingType;
import Core.Subject.NormalMode.GroupNormal;
import Core.Subject.Time;
import GUI.SubjectsPanel.SubjectInfo;

import javax.swing.*;
import java.util.ArrayList;

/*
 * Created by louay on 10/28/2016.
 */
public class GroupPanelNormal {

    private final SubjectInfo subjectInfo;
    private final SubjectPanelNormal subject;
    private JComboBox<String> lectureDayComboBox;
    private JSpinner lecturePeriodSpinner;
    private JButton deleteGroupButton;
    private JLabel lectureLabel;
    private JLabel secLecLabel;
    private JLabel tut1Label;
    private JLabel tut2Label;
    private JLabel lab1Label;
    private JLabel lab2Label;
    private JComboBox<String> secLectureDayComboBox;
    private JComboBox<String> tut1DayComboBox;
    private JComboBox<String> tut2DayComboBox;
    private JComboBox<String> lab1DayComboBox;
    private JComboBox<String> lab2DayComboBox;
    private JLabel lecturePeriodLabel;
    private JLabel secLecturePeriodLabel;
    private JLabel tut1PeriodLabel;
    private JLabel tut2PeriodLabel;
    private JLabel lab1PeriodLabel;
    private JLabel lab2PeriodLabel;
    private JSpinner secLecturePeriodSpinner;
    private JSpinner tut1PeriodSpinner;
    private JSpinner tut2PeriodSpinner;
    private JSpinner lab1PeriodSpinner;
    private JSpinner lab2PeriodSpinner;
    private JPanel mainPanel;

    /**
     * Creates new form GroupPanel
     *
     * @param subject     /
     * @param subjectInfo /
     */
    GroupPanelNormal(SubjectPanelNormal subject, SubjectInfo subjectInfo) {
        this.initComponents();
        this.subject = subject;
        this.subjectInfo = subjectInfo;

        secLectureDayComboBox.setVisible(subjectInfo.secLecExists);
        secLecturePeriodLabel.setVisible(subjectInfo.secLecExists);
        secLecturePeriodSpinner.setVisible(subjectInfo.secLecExists);
        secLecLabel.setVisible(subjectInfo.secLecExists);

        tut2Label.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2DayComboBox.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2PeriodSpinner.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2PeriodLabel.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        if (subjectInfo.tutBiWeek) {
            tut1Label.setText("Tutorial");
        } else {
            tut1Label.setText("Tutorial 1");
        }
        tut1Label.setVisible(subjectInfo.tutExists);
        tut1DayComboBox.setVisible(subjectInfo.tutExists);
        tut1PeriodSpinner.setVisible(subjectInfo.tutExists);
        tut1PeriodLabel.setVisible(subjectInfo.tutExists);

        lab2Label.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2DayComboBox.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2PeriodSpinner.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2PeriodLabel.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        if (subjectInfo.labBiWeek) {
            lab1Label.setText("Lab");
        } else {
            lab1Label.setText("Lab 1");
        }
        lab1Label.setVisible(subjectInfo.labExists);
        lab1DayComboBox.setVisible(subjectInfo.labExists);
        lab1PeriodSpinner.setVisible(subjectInfo.labExists);
        lab1PeriodLabel.setVisible(subjectInfo.labExists);
    }

    private void deleteGroupButtonActionPerformed() {
        this.subject.deleteGroup(this);
    }

    private void initComponents() {
        deleteGroupButton.addActionListener(e -> deleteGroupButtonActionPerformed());
        lectureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        secLectureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        tut1DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        tut2DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lab1DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lab2DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lecturePeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        secLecturePeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        tut1PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        tut2PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        lab1PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        lab2PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
    }

    protected GroupNormal getGroup() {
        Time lecture = new Time(this.lectureDayComboBox.getSelectedIndex(),
                (int) this.lecturePeriodSpinner.getValue(),
                MeetingType.LECTURE);
        Time secLecture = null;
        if (this.subjectInfo.secLecExists) {
            int from, to;
            if ((int) this.secLecturePeriodSpinner.getValue() + 1 == (int) this.lecturePeriodSpinner.getValue()) {
                from = ((int) this.secLecturePeriodSpinner.getValue() * 2) - 1;
                to = from;
            } else {
                from = ((int) this.secLecturePeriodSpinner.getValue() * 2) - 2;
                to = from;
            }
            secLecture = new Time(this.secLectureDayComboBox.getSelectedIndex(), from, to, MeetingType.SEC_LECTURE);
        }
        ArrayList<Time> tutorials = null, labs = null;
        if (this.subjectInfo.tutExists) {
            tutorials = new ArrayList<>();
            if (this.subjectInfo.tutBiWeek) {
                Time tut = new Time(this.tut1DayComboBox.getSelectedIndex(), (int) this.tut1PeriodSpinner.getValue(), MeetingType.TUT_HALF);
                tutorials.add(tut);
            } else {
                Time tut1 = new Time(this.tut1DayComboBox.getSelectedIndex(), (int) this.tut1PeriodSpinner.getValue(), MeetingType.TUT_FULL);
                Time tut2 = new Time(this.tut2DayComboBox.getSelectedIndex(), (int) this.tut2PeriodSpinner.getValue(), MeetingType.TUT_FULL);
                tutorials.add(tut1);
                tutorials.add(tut2);
            }
        }
        if (this.subjectInfo.labExists) {
            labs = new ArrayList<>();
            if (this.subjectInfo.labBiWeek) {
                Time lab = new Time(this.lab1DayComboBox.getSelectedIndex(), (int) this.lab1PeriodSpinner.getValue(), MeetingType.LAB_HALF);
                labs.add(lab);
            } else {
                Time lab1 = new Time(this.lab1DayComboBox.getSelectedIndex(), (int) this.lab1PeriodSpinner.getValue(), MeetingType.LAB_FULL);
                Time lab2 = new Time(this.lab2DayComboBox.getSelectedIndex(), (int) this.lab2PeriodSpinner.getValue(), MeetingType.LAB_FULL);
                labs.add(lab1);
                labs.add(lab2);
            }
        }
        return new GroupNormal(lecture, secLecture, tutorials, labs);
    }

    protected void setGroup(GroupNormal group) {
        this.lectureDayComboBox.setSelectedIndex(group.getLecture().day);
        this.lecturePeriodSpinner.setValue(group.getLecture().period);
        if (this.subjectInfo.secLecExists) {
            this.secLectureDayComboBox.setSelectedIndex(group.getSecLecture().day);
            this.secLecturePeriodSpinner.setValue(group.getSecLecture().period);
        }

        if (this.subjectInfo.tutExists) {
            this.tut1DayComboBox.setSelectedIndex(group.getTutorials().get(0).day);
            this.tut1PeriodSpinner.setValue(group.getTutorials().get(0).period);
            if (!this.subjectInfo.tutBiWeek) {
                this.tut2DayComboBox.setSelectedIndex(group.getTutorials().get(1).day);
                this.tut2PeriodSpinner.setValue(group.getTutorials().get(1).period);
            }
        }

        if (this.subjectInfo.labExists) {
            this.lab1DayComboBox.setSelectedIndex(group.getLabs().get(0).day);
            this.lab1PeriodSpinner.setValue(group.getLabs().get(0).period);
            if (!this.subjectInfo.labBiWeek) {
                this.lab2DayComboBox.setSelectedIndex(group.getLabs().get(1).day);
                this.lab2PeriodSpinner.setValue(group.getLabs().get(1).period);
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
