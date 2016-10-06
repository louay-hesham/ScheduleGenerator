/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.Extras;

/**
 *
 * @author louay
 */
public class GroupInfo{
    
    public Time lecture, secLecture, tutorial1, tutorial2, lab1, lab2;

    public void setLecture(int day, int period) {
        this.lecture = new Time(day, period);
    }

    public void setSecLecture(int day, int period) {
        this.secLecture = new Time(day, period);
    }

    public void setTutorial1(int day, int period) {
        this.tutorial1 = new Time(day, period);
    }

    public void setTutorial2(int day, int period) {
        this.tutorial2 = new Time(day, period);
    }

    public void setLab1(int day, int period) {
        this.lab1 = new Time(day, period);
    }

    public void setLab2(int day, int period) {
        this.lab2 = new Time(day, period);
    }
    
    
    
    public class Time{

        public Time(int day, int period) {
            this.day = day;
            this.period = period;
        }
        
        public int day, period;
    }
}
