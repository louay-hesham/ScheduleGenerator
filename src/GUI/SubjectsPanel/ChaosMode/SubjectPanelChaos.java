package GUI.SubjectsPanel.ChaosMode;

import Core.Subject.ChaosMode.SubjectChaos;
import Core.Subject.MeetingType;
import Core.Subject.Subject;
import Core.Subject.Time;
import GUI.MainGUI;
import GUI.SubjectsPanel.SubjectInfo;
import GUI.SubjectsPanel.SubjectPanel;

import javax.swing.*;
import java.util.ArrayList;

/*
 * Created by louay on 10/28/2016.
 */
public class SubjectPanelChaos extends SubjectPanel {

    private MeetingsPanel lectures, tutorials, labs;
    private JTabbedPane subjectTabbedPane;
    private JPanel mainPanel;
    private JButton deleteSubjectButton;

    /**
     * Creates new form SubjectPanel
     *
     * @param subjectName  /
     * @param secLecExists /
     * @param tutExists    /
     * @param tutBiWeek    /
     * @param labExists    /
     * @param labBiWeek    /
     */
    public SubjectPanelChaos(MainGUI gui, String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiWeek, boolean advanced) {
        super(gui, subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiWeek, advanced);
        this.initComponents();
        this.lectures.newTimeButtonActionPerformed();
        if (this.tutorials != null) {
            this.tutorials.newTimeButtonActionPerformed();
        }
        if (this.labs != null) {
            this.labs.newTimeButtonActionPerformed();
        }
    }

    public SubjectPanelChaos(MainGUI gui, String subjectName, SubjectInfo info, SubjectChaos sub, boolean advanced) {
        super(gui, subjectName, info, advanced);
        this.initComponents();
        if (info.secLecExists) {
            this.lectures.addMeetings(sub.getLectures(), sub.getSecLectures());
        } else {
            this.lectures.addMeetings(sub.getLectures());
        }
        if (info.tutExists) {
            this.tutorials.addMeetings(sub.getTutorials());
        }
        if (info.labExists) {
            this.labs.addMeetings(sub.getLabs());
        }
    }

    private ArrayList<Time> getLectureTimes() {
        return lectures.getMeetingTimes();
    }

    private ArrayList<Time> getSecLectureTimes() {
        return lectures.getSecLecTimes();
    }

    private ArrayList<Time> getTutorialTimes() {
        return tutorials == null ? null : tutorials.getMeetingTimes();
    }

    private ArrayList<Time> getLabsTimes() {
        return labs == null ? null : labs.getMeetingTimes();
    }

    private void deleteSubjectButtonActionPerformed() {
        this.gui.deleteSubject(this);
    }

    protected void initComponents() {
        if (this.subjectInfo.secLecExists) {
            this.lectures = new MeetingsPanel(MeetingType.SEC_LECTURE, this.advanced);
        } else {
            this.lectures = new MeetingsPanel(MeetingType.LECTURE, this.advanced);
        }
        this.subjectTabbedPane.addTab("Lectures", lectures.getMainPanel());
        if (this.subjectInfo.tutExists) {
            if (this.subjectInfo.tutBiWeek) {
                this.tutorials = new MeetingsPanel(MeetingType.TUT_HALF, this.advanced);
            } else {
                this.tutorials = new MeetingsPanel(MeetingType.TUT_FULL, this.advanced);
            }

            this.subjectTabbedPane.addTab("Tutorials", tutorials.getMainPanel());
        }
        if (this.subjectInfo.labExists) {
            if (this.subjectInfo.labBiWeek) {
                this.labs = new MeetingsPanel(MeetingType.LAB_HALF, this.advanced);
            } else {
                this.labs = new MeetingsPanel(MeetingType.LAB_FULL, this.advanced);
            }
            this.subjectTabbedPane.addTab("Labs", labs.getMainPanel());
        }
        this.deleteSubjectButton.addActionListener(e -> deleteSubjectButtonActionPerformed());
    }

    public void advancedModeConvert(){
        this.advanced = !this.advanced;
        this.lectures.advancedModeConvert();
        if (this.tutorials != null){
            this.tutorials.advancedModeConvert();
        }
        if (this.labs != null){
            this.labs.advancedModeConvert();
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Subject getSubject() {
        return new SubjectChaos(this.subjectName,
                this.getLectureTimes(),
                this.getSecLectureTimes(),
                this.getTutorialTimes(),
                this.getLabsTimes());
    }

    public String toString() {
        return this.getSubject().toString();
    }
}
