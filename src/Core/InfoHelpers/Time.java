/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.InfoHelpers;

import java.util.ArrayList;

/**
 * @author louay
 */
public class Time {

    public static boolean checkConflict(ArrayList<Time> times) {
        for (int i = 0; i < times.size(); i++) {
            for (int j = i + 1; j < times.size(); j++) {
                if (times.get(i).equals(times.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

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
