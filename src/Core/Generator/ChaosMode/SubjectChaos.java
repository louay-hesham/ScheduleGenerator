package Core.Generator.ChaosMode;

import Core.Generator.Subject;
import Core.Generator.Time;

import java.util.ArrayList;

/**
 * Created by louay on 1/23/2017.
 */
public class SubjectChaos extends Subject {
    private final String subjectName;
    private final ArrayList<Time> lectures, secLectures, tutorials, labs;
    private int iLec = 0, iTut = 0, iLab = 0;

    public SubjectChaos(String subjectName, ArrayList<Time> lectures, ArrayList<Time> secLectures, ArrayList<Time> tutorials, ArrayList<Time> labs) {
        this.subjectName = subjectName;
        this.lectures = lectures;
        this.secLectures = secLectures;
        this.tutorials = tutorials;
        this.labs = labs;
    }

    public void nextPermutation() throws Exception {
        iLab++;
        if (labs == null || iLab == labs.size()) {
            iLab = 0;
            iTut++;
            if (tutorials == null || iTut == tutorials.size()) {
                iTut = 0;
                iLec++;
                if (iLec == lectures.size()) {
                    iLec = 0;
                    throw new Exception("All permutations done");
                }
            }
        }
    }

    public ArrayList<Time> getTimesInPermutation(){
        this.timesInPermutation.clear();
        this.timesInPermutation.add(this.lectures.get(iLec));
        if (this.secLectures != null){
            this.timesInPermutation.add(this.secLectures.get(iLec));
        }
        if (this.tutorials != null){
            this.timesInPermutation.add(this.tutorials.get(iLec));
        }
        if (this.labs != null){
            this.timesInPermutation.add(this.labs.get(iLec));
        }
        return this.timesInPermutation;
    }
}
