package Core.Subject;

/**
 * Created by louay on 2/2/2017.
 */
public enum MeetingType {
    LECTURE, SEC_LECTURE, TUT_FULL, TUT_HALF, LAB_FULL, LAB_HALF, TUT_UNKNOWN, LAB_UNKNOWN;

    public String getTypeStringSimplified() {
        switch (this) {
            case LECTURE:
            case SEC_LECTURE:
                return "Lecture";
            case TUT_HALF:
            case TUT_FULL:
            case TUT_UNKNOWN:
                return "Tutorial";
            case LAB_HALF:
            case LAB_FULL:
            case LAB_UNKNOWN:
                return "Lab";
            default:
                return "Meeting";
        }
    }
}
