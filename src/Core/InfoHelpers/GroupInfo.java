/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.InfoHelpers;

/**
 * @author louay
 */
public class GroupInfo {

    public Time lecture, secLecture, tutorial1, tutorial2, lab1, lab2;

    public GroupInfo() {
        lecture = new Time();
        secLecture = new Time();
        tutorial1 = new Time();
        tutorial2 = new Time();
        lab1 = new Time();
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

    public String getMeetingType(int day, int period) throws Exception {
        if (this.lecture.day == day && this.lecture.period == period) {
            return "Lecture";
        } else if (this.secLecture.day == day && this.secLecture.period == period) {
            return "Secondary Lecture";
        } else if (this.tutorial1.day == day && this.tutorial1.period == period) {
            return "Tutorial";
        } else if (this.tutorial2.day == day && this.tutorial2.period == period) {
            return "Tutorial";
        } else if (this.lab1.day == day && this.lab1.period == period) {
            return "Lab";
        } else if (this.lab2.day == day && this.lab2.period == period) {
            return "Lab";
        } else {
            throw new Exception("Not present");
        }
    }
}
