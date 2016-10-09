/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Extras;

import java.util.ArrayList;

/**
 *
 * @author louay
 */
public class Time {
    
    public static boolean checkConflict(ArrayList<Time> times){
        for (int i=0; i<times.size(); i++){
            for (int j=i+1; j< times.size(); j++){
                if (times.get(i).equals(times.get(j))){
                    return true;
                }
            }
        }
        return false;
    }

    public Time() {
        this.day = 0;
        this.period = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Time) {
            return ((Time) obj).hashCode() == this.hashCode();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.day;
        hash = 71 * hash + this.period;
        return hash;
    }

    public Time(int day, int period) {
        this.day = day;
        this.period = period;
    }

    public int day, period;
}
