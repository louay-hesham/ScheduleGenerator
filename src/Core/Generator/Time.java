package Core.Generator;

/**
 * Created by louay on 1/30/2017.
 */

public class Time {

    public enum MeetingType{
        LECTURE, SEC_LECTURE, TUT_FULL, TUT_HALF, LAB_FULL, LAB_HALF
    }

    public final int day, from, to;
    public final MeetingType type;

    public Time() {
        this.day = 0;
        this.from = 0;
        this.to = 0;
        type = MeetingType.LECTURE;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Time && ((Time) obj).day == this.day && ((Time) obj).from == this.from && ((Time) obj).to == this.to;
    }

    public Time(int day, int from, int to, MeetingType type) {
        this.day = day;
        this.from = from;
        this.to = to;
        this.type = type;
    }


}
