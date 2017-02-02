package Core.Generator;

/**
 * Created by louay on 1/30/2017.
 */

public class Time {

    public enum MeetingType{
        LECTURE, SEC_LECTURE, TUT_FULL, TUT_HALF, LAB_FULL, LAB_HALF
    }

    public final int day, from, to, period;
    public final MeetingType type;

    public Time() {
        this.day = 0;
        this.from = 0;
        this.to = 0;
        this.period = 0;
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
        this.period = (this.to / 2) + 1;
    }

    public Time (int day, int period, MeetingType type){
        this.period = period;
        this.day = day;
        this.type = type;
        this.to = (period * 2) - 1;
        switch (type){
            case LAB_HALF:
            case TUT_HALF:
            case SEC_LECTURE:
                this.from = this.to;
                break;
            case LAB_FULL:
            case TUT_FULL:
            case LECTURE:
            default:
                this.from = this.to - 1;
                break;
        }
    }

    public String getTypeString(){
        switch(this.type){
            case LECTURE:
                return "Lecture";
            case SEC_LECTURE:
                return "Secondary Lecture";
            case TUT_HALF:
            case TUT_FULL:
                return "Tutorial";
            case LAB_HALF:
            case LAB_FULL:
                return "Lab";
            default:
                return "Unknown";
        }
    }

    public MeetingType getType() {
        return type;
    }
}

