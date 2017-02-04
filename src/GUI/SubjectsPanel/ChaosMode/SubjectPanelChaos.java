package GUI.SubjectsPanel.ChaosMode;

import Core.Subject.ChaosMode.SubjectChaos;
import Core.Subject.MeetingType;
import Core.Subject.Subject;
import Core.Subject.Time;
import GUI.SubjectsPanel.SubjectInfo;
import GUI.SubjectsPanel.SubjectPanel;

import javax.swing.*;
import java.util.ArrayList;

/*
 * Created by louay on 10/28/2016.
 */
public class SubjectPanelChaos extends SubjectPanel {

    private Meeting lectures, tutorials, labs;
    private JTabbedPane subjectTabbedPane;
    private JPanel mainPanel;

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
    public SubjectPanelChaos(String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiWeek) {
        super(subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiWeek);
        this.initComponents();
        this.lectures.newTimeButtonActionPerformed();
        if (this.tutorials != null){
            this.tutorials.newTimeButtonActionPerformed();
        }
        if (this.labs != null){
            this.labs.newTimeButtonActionPerformed();
        }
    }

    public SubjectPanelChaos(String subjectName, SubjectInfo info, SubjectChaos sub) {
        super(subjectName, info);
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

    protected void initComponents() {
        if (this.subjectInfo.secLecExists) {
            this.lectures = new Meeting(MeetingType.SEC_LECTURE);
        } else {
            this.lectures = new Meeting(MeetingType.LECTURE);
        }
        this.subjectTabbedPane.addTab("Lectures", lectures.getMainPanel());
        if (this.subjectInfo.tutExists) {
            if (this.subjectInfo.tutBiWeek) {
                this.tutorials = new Meeting(MeetingType.TUT_HALF);
            } else {
                this.tutorials = new Meeting(MeetingType.TUT_FULL);
            }

            this.subjectTabbedPane.addTab("Tutorials", tutorials.getMainPanel());
        }
        if (this.subjectInfo.labExists) {
            if (this.subjectInfo.labBiWeek) {
                this.labs = new Meeting(MeetingType.LAB_HALF);
            } else {
                this.labs = new Meeting(MeetingType.LAB_FULL);
            }
            this.subjectTabbedPane.addTab("Labs", labs.getMainPanel());
        }
    }

    public String getSubjectName() {
        return subjectName;
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
