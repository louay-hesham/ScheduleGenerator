/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.InfoHelpers;

/**
 * @author louay
 */
public class Time {
    Time() {
        this.day = 0;
        this.period = 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Time && ((Time) obj).day == this.day && ((Time) obj).period == this.period;
    }

    Time(int day, int period) {
        this.day = day;
        this.period = period;
    }

    public int day, period;
}
