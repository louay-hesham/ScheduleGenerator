package Core.Generator;

/**
 * Created by louay on 1/30/2017.
 */

public class Time {

    public enum Type{
        LECTURE, SEC_LECTURE, TUT_FULL, TUT_HALF, LAB_FULL, LAB_HALF
    }

    public final int day, from, to;
    public final Type type;

    Time() {
        this.day = 0;
        this.from = 0;
        this.to = 0;
        type = Type.LECTURE;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Time && ((Time) obj).day == this.day && ((Time) obj).from == this.from && ((Time) obj).to == this.to;
    }

    Time(int day, int period, int to, Type type) {
        this.day = day;
        this.from = period;
        this.to = to;
        this.type = type;
    }


}

