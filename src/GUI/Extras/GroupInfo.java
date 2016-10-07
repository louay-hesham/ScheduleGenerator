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

    public GroupInfo() {
        lecture = new Time();
        secLecture = new Time();
        tutorial1 = new Time();
        tutorial2 = new Time();
        lab2 = new Time();
        lab2 = new Time();
    }

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

        public Time() {
            this.day = 0;
            this.period = 0;
        }

        public Time(int day, int period) {
            this.day = day;
            this.period = period;
        }
        
        public int day, period;
    }
}
