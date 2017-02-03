package Core.FileIO.Loader;

import Core.Subject.ChaosMode.SubjectChaos;
import Core.Subject.MeetingType;
import Core.Subject.Time;
import GUI.MainGUI;
import GUI.SubjectsPanel.ChaosMode.SubjectPanelChaos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louay on 2/3/2017.
 */
public class FileParserChaos extends FileParser {

    public FileParserChaos(List<String> lines, MainGUI gui) throws Exception {
        super(lines, gui);
    }


    protected void startDecoding() throws Exception {
        this.n = Integer.decode(this.getNextLine());
        for (int i = 0; i < n; i++) {
            SubjectChaos s = this.decodeSubject();
            this.panels.add(new SubjectPanelChaos(s.getSubjectName(), s.getInfo(), s));
        }
    }

    protected SubjectChaos decodeSubject() throws Exception {
        String subjectName = this.getNextLine();
        ArrayList<Time> lectures = this.decodeMeetings();
        ArrayList<Time> secLectures = this.decodeMeetings();
        ArrayList<Time> tutorials = this.decodeMeetings();
        ArrayList<Time> labs = this.decodeMeetings();
        return new SubjectChaos(subjectName, lectures, secLectures, tutorials, labs);
    }

    private ArrayList<Time> decodeMeetings() throws Exception {
        String typeN = this.getNextLine();
        String typeString = typeN.split(" ")[0];
        MeetingType type;
        switch (typeString) {
            case "LEC":
                type = MeetingType.LECTURE;
                break;
            case "SEC_LEC":
                type = MeetingType.SEC_LECTURE;
                break;
            case "TUT":
                type = MeetingType.TUT_UNKNOWN;
                break;
            case "LAB":
                type = MeetingType.LAB_UNKNOWN;
                break;
            case "NO_SEC_LEC":
            case "NO_TUT":
            case "NO_LAB":
                return null;
            default:
                throw new Exception("Invalid file format");
        }
        int m = Integer.decode(typeN.split(" ")[1]);
        ArrayList<Time> meetings = new ArrayList<>();
        for (int j = 0; j < m; j++) {
            meetings.add(new Time(this.getNextLine(), type));
        }
        return meetings;
    }
}
