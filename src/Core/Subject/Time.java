package Core.Subject;

/**
 * Created by louay on 1/30/2017.
 */

public class Time {

    public final int day, from, to, period;
    public final MeetingType type;

    public Time(int day, int from, int to, MeetingType type) {
        this.day = day;
        this.from = from;
        this.to = to;
        this.type = type;
        this.period = (this.to / 2) + 1;
    }

    public Time(int day, int period, MeetingType type) {
        this.period = period;
        this.day = day;
        this.type = type;
        this.to = (period * 2) - 1;
        switch (type) {
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

    public Time(String str, MeetingType type){
        String[] times = str.split(" ");
        this.day = Integer.decode(times[0]);
        this.from = Integer.decode(times[1]);
        this.to = Integer.decode(times[2]);
        this.period = (this.to / 2) + 1;
        switch(type){
            case LECTURE:
            case SEC_LECTURE:
            case TUT_FULL:
            case TUT_HALF:
            case LAB_FULL:
            case LAB_HALF:
            {
                this.type = type;
                break;
            }
            case TUT_UNKNOWN:
            {
                if (this.from == this.to){
                    this.type = MeetingType.TUT_HALF;
                } else {
                    this.type = MeetingType.TUT_FULL;
                }
                break;
            }
            case LAB_UNKNOWN:
            {
                if (this.from == this.to){
                    this.type = MeetingType.LAB_HALF;
                } else {
                    this.type = MeetingType.LAB_FULL;
                }
                break;
            }
            default:
                this.type = MeetingType.LECTURE;
                break;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Time && ((Time) obj).day == this.day && ((Time) obj).from == this.from && ((Time) obj).to == this.to;
    }

    public String getTypeString() {
        switch (this.type) {
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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.day + " " + this.from + " " + this.to + "\r\n");
        return sb.toString();
    }

}

