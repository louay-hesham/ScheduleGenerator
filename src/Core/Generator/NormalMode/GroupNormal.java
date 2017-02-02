package Core.Generator.NormalMode;

import Core.Generator.Time;

import java.util.ArrayList;

/**
 * Created by louay on 2/2/2017.
 */
public class GroupNormal {

    private final ArrayList<Time> timesInPermutation = new ArrayList<>();
    private final ArrayList<Time>  tutorials, labs;
    private final Time lecture, secLecture;
    private int iTut = -1, iLab = -1;

    public GroupNormal(String subjectName, Time lecture,Time secLecture, ArrayList<Time> tutorials, ArrayList<Time> labs) {
        //super(subjectName);
        this.lecture = lecture;
        this.secLecture = secLecture;
        this.tutorials = tutorials;
        this.labs = labs;
    }

    public void nextPermutation() throws Exception {
        if (this.iTut == -1){
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


    public ArrayList<Time> getTimesInPermutation(){
        this.timesInPermutation.clear();
        this.timesInPermutation.add(this.lecture);
        if (this.secLecture != null){
            this.timesInPermutation.add(this.secLecture);
        }
        if (this.tutorials != null){
            this.timesInPermutation.add(this.tutorials.get(iTut));
        }
        if (this.labs != null){
            this.timesInPermutation.add(this.labs.get(iLab));
        }
        return this.timesInPermutation;
    }
}
