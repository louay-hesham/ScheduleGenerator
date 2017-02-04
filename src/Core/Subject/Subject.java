package Core.Subject;

import GUI.SubjectsPanel.SubjectInfo;

import java.util.ArrayList;

/**
 * Created by louay on 1/30/2017.
 */
public abstract class Subject {
    protected final String subjectName;
    protected final ArrayList<Time> timesInPermutation = new ArrayList<>();
    protected final SubjectInfo info;

    public Subject(String subjectName, SubjectInfo info) {
        this.subjectName = subjectName;
        this.info = info;
    }

    public abstract ArrayList<Time> getTimesInPermutation();

    public abstract void nextPermutation() throws Exception;

    public abstract String toString();

    public String getSubjectName() {
        return subjectName;
    }

    public SubjectInfo getInfo() {
        return info;
    }
}
