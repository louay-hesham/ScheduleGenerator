package Core.Generator.ChaosMode;

import Core.Generator.Subject;
import Core.Generator.Time;

import java.util.ArrayList;

/**
 * Created by louay on 1/23/2017.
 */
public class SubjectChaos extends Subject {
    private final ArrayList<Time> lectures, secLectures, tutorials, labs;
    private int iLec = -1, iTut = -1, iLab = -1;

    public SubjectChaos(String subjectName, ArrayList<Time> lectures, ArrayList<Time> secLectures, ArrayList<Time> tutorials, ArrayList<Time> labs) {
        super(subjectName);
        this.lectures = lectures;
        this.secLectures = secLectures;
        this.tutorials = tutorials;
        this.labs = labs;
    }

    public void nextPermutation() throws Exception {
        if (this.iLec == -1) {
            this.iLec = 0;
            this.iTut = 0;
            this.iLab = 0;
        } else {
            iLab++;
            if (labs == null || iLab == labs.size()) {
                iLab = 0;
                iTut++;
                if (tutorials == null || iTut == tutorials.size()) {
                    iTut = 0;
                    iLec++;
                    if (iLec == lectures.size()) {
                        iLec = -1;
                        iTut = -1;
                        iLab = -1;
                        throw new Exception("All permutations done");
                    }
                }
            }
        }
    }

    public ArrayList<Time> getTimesInPermutation() {
        this.timesInPermutation.clear();
        this.timesInPermutation.add(this.lectures.get(iLec));
        if (this.secLectures != null) {
            this.timesInPermutation.add(this.secLectures.get(iLec));
        }
        if (this.tutorials != null) {
            this.timesInPermutation.add(this.tutorials.get(iTut));
        }
        if (this.labs != null) {
            this.timesInPermutation.add(this.labs.get(iLab));
        }
        return this.timesInPermutation;
    }

    public ArrayList<Time> getLectures() {
        return lectures;
    }

    public ArrayList<Time> getSecLectures() {
        return secLectures;
    }

    public ArrayList<Time> getTutorials() {
        return tutorials;
    }

    public ArrayList<Time> getLabs() {
        return labs;
    }
}
