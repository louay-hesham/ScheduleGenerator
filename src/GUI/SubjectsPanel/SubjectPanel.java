package GUI.SubjectsPanel;

import Core.Subject.Subject;
import GUI.MainGUI;

import javax.swing.*;

/**
 * Created by louay on 2/2/2017.
 */
public abstract class SubjectPanel {
    protected final SubjectInfo subjectInfo;
    protected final String subjectName;
    protected final MainGUI gui;
    protected boolean advanced;

    public SubjectPanel(MainGUI gui, String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek, boolean advanced) {
        this.subjectInfo = new SubjectInfo(secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        this.subjectName = subjectName;
        this.gui = gui;
        this.advanced = advanced;
    }

    //constructor for file loader
    public SubjectPanel(MainGUI gui, String subjectName, SubjectInfo info, boolean advanced) {
        this.subjectInfo = info;
        this.subjectName = subjectName;
        this.gui = gui;
        this.advanced = advanced;
    }

    protected abstract void initComponents();

    public abstract void advancedModeConvert();

    public abstract JPanel getMainPanel();

    public abstract Subject getSubject();

    public abstract String toString();

    public String getSubjectName() {
        return subjectName;
    }
}
