package Core.Subject.NormalMode;

import Core.Subject.ChaosMode.SubjectChaos;
import Core.Subject.Subject;
import Core.Subject.Time;
import GUI.SubjectsPanel.SubjectInfo;

import java.util.ArrayList;

/**
 * Created by louay on 2/2/2017.
 */
public class SubjectNormal extends Subject {

    private final ArrayList<GroupNormal> groups;
    private final SubjectInfo info;
    private int iGroup = -1;

    public SubjectNormal(String subjectName, ArrayList<GroupNormal> groups, SubjectInfo info) {
        super(subjectName);
        this.groups = groups;
        this.info = info;
    }

    public void nextPermutation() throws Exception {
        if (this.iGroup == -1) {
            this.iGroup = 0;
            this.groups.get(0).nextPermutation();
        } else {
            try {
                this.groups.get(this.iGroup).nextPermutation();
            } catch (Exception e) {
                this.iGroup++;
                if (this.iGroup == this.groups.size()) {
                    this.iGroup = -1;
                    throw new Exception("All permutations done");
                } else {
                    this.groups.get(this.iGroup).nextPermutation();
                }
            }
        }
    }

    public ArrayList<Time> getTimesInPermutation() {
        this.timesInPermutation.clear();
        this.timesInPermutation.addAll(this.groups.get(this.iGroup).getTimesInPermutation());
        return this.timesInPermutation;
    }

    public SubjectChaos getChaos() {
        ArrayList<Time> lectures = new ArrayList<>();
        ArrayList<Time> secLectures = null, tutorials = null, labs = null;
        if (this.info.secLecExists) {
            secLectures = new ArrayList<>();
        }
        if (this.info.tutExists) {
            tutorials = new ArrayList<>();
        }
        if (this.info.labExists) {
            labs = new ArrayList<>();
        }
        for (GroupNormal g : this.groups) {
            lectures.add(g.getLecture());
            if (this.info.secLecExists) {
                secLectures.add(g.getSecLecture());
            }
            if (this.info.tutExists) {
                tutorials.addAll(g.getTutorials());
            }
            if (this.info.labExists) {
                labs.addAll(g.getLabs());
            }
        }
        return new SubjectChaos(this.subjectName, lectures, secLectures, tutorials, labs);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (GroupNormal g : this.groups){
            sb.append(g.toString());
        }
        sb.append("\r\n");
        return sb.toString();
    }

    public SubjectInfo getInfo() {
        return info;
    }

    public ArrayList<GroupNormal> getGroups() {
        return groups;
    }
}
