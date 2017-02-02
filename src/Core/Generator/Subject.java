package Core.Generator;

import java.util.ArrayList;

/**
 * Created by louay on 1/30/2017.
 */
public abstract class Subject {
    protected final String subjectName;
    protected final ArrayList<Time> timesInPermutation = new ArrayList<>();

    public Subject(String subjectName) {
        this.subjectName = subjectName;
    }

    public abstract ArrayList<Time> getTimesInPermutation();

    public abstract void nextPermutation() throws Exception;

    public String getSubjectName() {
        return subjectName;
    }
}
