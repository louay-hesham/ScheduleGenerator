package GUI.ChaosMode;

import Core.Generator.ChaosMode.SubjectChaos;
import Core.Generator.Subject;
import Core.Generator.Time;
import Core.Generator.Time.MeetingType;
import GUI.SubjectInfo;
import GUI.SubjectPanel;

import javax.swing.*;
import java.util.ArrayList;

/*
 * Created by louay on 10/28/2016.
 */
public class SubjectPanelChaos extends SubjectPanel{

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
     * @param labBiWeek    /
     */
    public SubjectPanelChaos(String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiWeek) {
        super(subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiWeek);
        this.initComponents();
    }

    public SubjectPanelChaos(String subjectName, SubjectInfo info, SubjectChaos sub){
        super (subjectName, info, sub);
        this.initComponents();
        if (info.secLecExists){
            this.lectures.addMeetings(sub.getLectures(), sub.getSecLectures());
        } else {
            this.lectures.addMeetings(sub.getLectures());
        }
        if (info.tutExists){
            this.tutorials.addMeetings(sub.getTutorials());
        }
        if (info.labExists){
            this.labs.addMeetings(sub.getLabs());
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Subject getSubject(){
        return new SubjectChaos(this.subjectName,
                this.getLectureTimes(),
                this.getSecLectureTimes(),
                this.getTutorialTimes(),
                this.getLabsTimes());
    }

    protected void initComponents(){
        if (this.subjectInfo.secLecExists) {
            this.lectures = new Meeting(MeetingType.SEC_LECTURE);
        } else {
            this.lectures = new Meeting(MeetingType.LECTURE);
        }
        this.subjectTabbedPane.addTab("Lectures", lectures.getMainPanel());
        if (this.subjectInfo.tutExists){
            if (this.subjectInfo.tutBiWeek){
                this.tutorials = new Meeting(MeetingType.TUT_HALF);
            } else {
                this.tutorials = new Meeting(MeetingType.TUT_FULL);
            }

            this.subjectTabbedPane.addTab("Tutorials", tutorials.getMainPanel());
        }
        if (this.subjectInfo.labExists){
            if (this.subjectInfo.labBiWeek){
                this.labs = new Meeting(MeetingType.LAB_HALF);
            } else {
                this.labs = new Meeting(MeetingType.LAB_FULL);
            }
            this.subjectTabbedPane.addTab("Labs", labs.getMainPanel());
        }
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
