package Core.FileIO.Loader;

import Core.Subject.MeetingType;
import Core.Subject.NormalMode.GroupNormal;
import Core.Subject.NormalMode.SubjectNormal;
import Core.Subject.Time;
import GUI.MainGUI;
import GUI.SubjectsPanel.NormalMode.SubjectPanelNormal;
import GUI.SubjectsPanel.SubjectInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louay on 2/3/2017.
 */
public class FileParserNormal extends FileParser {

    public FileParserNormal(List<String> lines, MainGUI gui) throws Exception {
        super(lines, gui);
    }

    protected void startDecoding() {
        this.n = Integer.decode(this.getNextLine());
        for (int i = 0; i < n; i++) {
            SubjectNormal s = this.decodeSubject();
            this.panels.add(new SubjectPanelNormal(this.gui, s, this.gui.isAdvanced()));
        }
    }

    protected SubjectNormal decodeSubject() {
        String subjectName = this.getNextLine();
        String subjectInfoString = this.getNextLine();
        int g = Integer.decode(subjectInfoString.split("!")[0]);
        SubjectInfo subjectInfo = new SubjectInfo(subjectInfoString.split("!")[1]);
        ArrayList<GroupNormal> groups = new ArrayList<>();
        for (int j = 0; j < g; j++) {
            groups.add(this.decodeGroup(subjectInfo));
        }
        return new SubjectNormal(subjectName, groups, subjectInfo);
    }

    private GroupNormal decodeGroup(SubjectInfo info) {
        Time lecture = new Time(this.getNextLine(), MeetingType.LECTURE);
        Time secLecture = null;
        if (info.secLecExists) {
            secLecture = new Time(this.getNextLine(), MeetingType.SEC_LECTURE);
        }
        ArrayList<Time> tutorials = null;
        if (info.tutExists) {
            tutorials = new ArrayList<>();
            if (info.tutBiWeek) {
                tutorials.add(new Time(this.getNextLine(), MeetingType.TUT_HALF));
            } else {
                tutorials.add(new Time(this.getNextLine(), MeetingType.TUT_FULL));
                tutorials.add(new Time(this.getNextLine(), MeetingType.TUT_FULL));
            }
        }
        ArrayList<Time> labs = null;
        if (info.labExists) {
            labs = new ArrayList<>();
            if (info.labBiWeek) {
                labs.add(new Time(this.getNextLine(), MeetingType.LAB_HALF));
            } else {
                labs.add(new Time(this.getNextLine(), MeetingType.LAB_FULL));
                labs.add(new Time(this.getNextLine(), MeetingType.LAB_FULL));
            }
        }
        return new GroupNormal(lecture, secLecture, tutorials, labs);
    }
}
