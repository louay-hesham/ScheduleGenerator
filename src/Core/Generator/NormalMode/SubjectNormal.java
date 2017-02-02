package Core.Generator.NormalMode;

import Core.Generator.Subject;
import Core.Generator.Time;

import java.util.ArrayList;

/**
 * Created by louay on 2/2/2017.
 */
public class SubjectNormal extends Subject {

    private final ArrayList<GroupNormal> groups;
    private int iGroup = -1;

    public SubjectNormal(String subjectName, ArrayList<GroupNormal> groups) {
        super(subjectName);
        this.groups = groups;
    }

    public void nextPermutation() throws Exception {
        if (this.iGroup == -1){
            this.iGroup = 0;
            this.groups.get(0).nextPermutation();
        } else {
            try {
                this.groups.get(this.iGroup).nextPermutation();
            } catch (Exception e) {
                this.iGroup++;
                if (this.iGroup == this.groups.size()){
                    this.iGroup = -1;
                    throw new Exception("All permutations done");
                }
            }
        }
    }

    public ArrayList<Time> getTimesInPermutation(){
        this.timesInPermutation.clear();
        this.timesInPermutation.addAll(this.groups.get(this.iGroup).getTimesInPermutation());
        return this.timesInPermutation;
    }

}
