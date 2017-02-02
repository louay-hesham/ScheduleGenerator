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

    protected abstract void initComponents();

    public abstract JPanel getMainPanel();

    public String getSubjectName() {
        return subjectName;
    }

    public abstract Subject getSubject();
}
