package Core.Subject.NormalMode;

import Core.Subject.Time;

import java.util.ArrayList;

/**
 * Created by louay on 2/2/2017.
 */
public class GroupNormal {

    private final ArrayList<Time> timesInPermutation = new ArrayList<>();
    private final ArrayList<Time> tutorials, labs;
    private final Time lecture, secLecture;
    private int iTut = -1, iLab = -1;

    public GroupNormal(Time lecture, Time secLecture, ArrayList<Time> tutorials, ArrayList<Time> labs) {
        this.lecture = lecture;
        this.secLecture = secLecture;
        this.tutorials = tutorials;
        this.labs = labs;
    }

    public void nextPermutation() throws Exception {
        if (this.iTut == -1) {
            this.iTut = 0;
            this.iLab = 0;
        } else {
            iLab++;
            if (labs == null || iLab == labs.size()) {
                iLab = 0;
                iTut++;
                if (tutorials == null || iTut == tutorials.size()) {
                    iTut = -1;
                    iLab = -1;
                    throw new Exception("All permutations done");
                }
            }
        }
    }

    public ArrayList<Time> getTimesInPermutation() {
        this.timesInPermutation.clear();
        this.timesInPermutation.add(this.lecture);
        if (this.secLecture != null) {
            this.timesInPermutation.add(this.secLecture);
        }
        if (this.tutorials != null) {
            this.timesInPermutation.add(this.tutorials.get(iTut));
        }
        if (this.labs != null) {
            this.timesInPermutation.add(this.labs.get(iLab));
        }
        return this.timesInPermutation;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.lecture.toString());
        if (this.secLecture != null){
            sb.append(this.secLecture.toString());
        }
        if (this.tutorials != null && this.tutorials.size() != 0 ){
            for (Time t : this.tutorials){
                sb.append(t.toString());
            }
        }
        if (this.labs != null && this.labs.size() != 0 ){
            for (Time t : this.labs){
                sb.append(t.toString());
            }
        }
        sb.append("\r\n");
        return sb.toString();
    }

    public ArrayList<Time> getTutorials() {
        return tutorials;
    }

    public ArrayList<Time> getLabs() {
        return labs;
    }

    public Time getLecture() {
        return lecture;
    }

    public Time getSecLecture() {
        return secLecture;
    }
}
