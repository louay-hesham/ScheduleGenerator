package GUI;

import Core.Generator.Subject;

import javax.swing.*;

/**
 * Created by louay on 2/2/2017.
 */
public abstract class SubjectPanel {
    protected final SubjectInfo subjectInfo;
    protected final String subjectName;

    public SubjectPanel(String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        this.subjectInfo = new SubjectInfo(secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        this.subjectName = subjectName;
    }

    public SubjectPanel(String subjectName, SubjectInfo info, Subject sub) {
        this.subjectInfo = info;
        this.subjectName = subjectName;
    }

    protected abstract void initComponents();

    public abstract JPanel getMainPanel();

    public abstract Subject getSubject();

    public String getSubjectName() {
        return subjectName;
    }
}
