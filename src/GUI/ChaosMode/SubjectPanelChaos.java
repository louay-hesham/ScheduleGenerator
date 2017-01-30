package GUI.ChaosMode;

import Core.Generator.ChaosMode.SubjectChaos;
import Core.Generator.Time;
import Core.Generator.Time.MeetingType;
import Core.InfoHelpers.GroupInfo;
import Core.InfoHelpers.SubjectInfo;

import javax.swing.*;
import java.util.ArrayList;

/*
 * Created by louay on 10/28/2016.
 */
public class SubjectPanelChaos {

    private final SubjectInfo subjectInfo;
    private final String subjectName;
    private Meeting lectures, tutorials, labs;

    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Creates new form SubjectPanel
     *
     * @param subjectName  /
     * @param secLecExists /
     * @param tutExists    /
     * @param tutBiWeek    /
     * @param labExists    /
     * @param labBiweek    /
     */
    public SubjectPanelChaos(String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        subjectInfo = new SubjectInfo(secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        this.subjectName = subjectName;
        if (secLecExists) {
            this.lectures = new Meeting(MeetingType.SEC_LECTURE);
        } else {
            this.lectures = new Meeting(MeetingType.LECTURE);
        }
        this.subjectTabbedPane.addTab("Lectures", lectures.getMainPanel());
        if (tutExists){
            if (tutBiWeek){
                this.tutorials = new Meeting(MeetingType.TUT_HALF);
            } else {
                this.tutorials = new Meeting(MeetingType.TUT_FULL);
            }

            this.subjectTabbedPane.addTab("Tutorials", tutorials.getMainPanel());
        }
        if (labExists){
            if (tutBiWeek){
                this.labs = new Meeting(MeetingType.LAB_HALF);
            } else {
                this.labs = new Meeting(MeetingType.LAB_FULL);
            }
            this.subjectTabbedPane.addTab("Labs", labs.getMainPanel());
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addGroup(GroupInfo groupInfo) {
        if (this.subjectInfo.secLecExists) {
            lectures.addMeeting(groupInfo.lecture.day, groupInfo.lecture.period, groupInfo.secLecture.day, groupInfo.secLecture.period);
        } else {
            lectures.addMeeting(groupInfo.lecture.day, groupInfo.lecture.period);
        }

        if (this.subjectInfo.tutExists) {
            tutorials.addMeeting(groupInfo.tutorial1.day, groupInfo.tutorial1.period);
            if (!this.subjectInfo.tutBiWeek) {
                tutorials.addMeeting(groupInfo.tutorial2.day, groupInfo.tutorial2.period);
            }
        }

        if (this.subjectInfo.labExists) {
            labs.addMeeting(groupInfo.lab1.day, groupInfo.lab1.period);
            if (!this.subjectInfo.labBiWeek) {
                labs.addMeeting(groupInfo.lab2.day, groupInfo.lab2.period);
            }
        }
    }

    public void reset() {
        this.subjectTabbedPane.removeTabAt(0);
        if (subjectInfo.tutExists){
            this.subjectTabbedPane.removeTabAt(0);
        }
        if (subjectInfo.labExists){
            this.subjectTabbedPane.removeTabAt(0);
        }
        if (this.subjectInfo.secLecExists) {
            this.lectures = new Meeting(MeetingType.SEC_LECTURE);
        } else {
            this.lectures = new Meeting(MeetingType.LECTURE);
        }
        this.subjectTabbedPane.addTab("Lectures", lectures.getMainPanel());
        if (subjectInfo.tutExists){
            if (subjectInfo.tutBiWeek){
                this.tutorials = new Meeting(MeetingType.TUT_HALF);
            } else {
                this.tutorials = new Meeting(MeetingType.TUT_FULL);
            }

            this.subjectTabbedPane.addTab("Tutorials", tutorials.getMainPanel());
        }
        if (subjectInfo.labExists){
            if (subjectInfo.tutBiWeek){
                this.labs = new Meeting(MeetingType.LAB_HALF);
            } else {
                this.labs = new Meeting(MeetingType.LAB_FULL);
            }
            this.subjectTabbedPane.addTab("Labs", labs.getMainPanel());
        }
    }

    public SubjectChaos getSubject(){
        return new SubjectChaos(this.subjectName,
                this.getLectureTimes(),
                this.getSecLectureTimes(),
                this.getTutorialTimes(),
                this.getLabsTimes());
    }

    private ArrayList<Time> getLectureTimes(){
        return lectures.getMeetingTimes();
    }

    private ArrayList<Time> getSecLectureTimes(){
        return lectures.getSecLecTimes();
    }

    private ArrayList<Time> getTutorialTimes(){
        return tutorials == null? null : tutorials.getMeetingTimes();
    }

    private ArrayList<Time> getLabsTimes(){
        return labs == null? null :labs.getMeetingTimes();
    }

    private JTabbedPane subjectTabbedPane;
    private JPanel mainPanel;
}
