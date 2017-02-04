/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.SubjectsPanel;

import Core.Subject.MeetingType;
import Core.Subject.Time;

import java.util.ArrayList;

/**
 * @author louay
 */
public class SubjectInfo {

    public final boolean secLecExists, tutExists, tutBiWeek, labExists, labBiWeek;

    public SubjectInfo(boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        this.secLecExists = secLecExists;
        this.tutExists = tutExists;
        this.tutBiWeek = tutExists && tutBiWeek;
        this.labExists = labExists;
        this.labBiWeek = labExists && labBiweek;
    }

    public SubjectInfo(ArrayList<Time> secLectures, ArrayList<Time> tutorials, ArrayList<Time> labs) {
        if (secLectures != null && secLectures.size() != 0) {
            this.secLecExists = true;
        } else {
            this.secLecExists = false;
        }

        if (tutorials != null && tutorials.size() != 0) {
            this.tutExists = true;
            if (tutorials.get(0).getType() == MeetingType.TUT_HALF) {
                this.tutBiWeek = true;
            } else {
                this.tutBiWeek = false;
            }
        } else {
            this.tutExists = false;
            this.tutBiWeek = false;
        }

        if (labs != null && labs.size() != 0) {
            this.labExists = true;
            if (labs.get(0).getType() == MeetingType.LAB_HALF) {
                this.labBiWeek = true;
            } else {
                this.labBiWeek = false;
            }
        } else {
            this.labExists = false;
            this.labBiWeek = false;
        }
    }

    public SubjectInfo(String str) {
        String[] info = str.split(" ");
        switch (info[0]) {
            case "LEC":
                this.secLecExists = false;
                break;
            case "SEC_LEC":
            default:
                this.secLecExists = true;
                break;
        }

        switch (info[1]) {
            case "NO_TUT":
                this.tutExists = false;
                this.tutBiWeek = false;
                break;
            case "TUT_HALF":
                this.tutExists = true;
                this.tutBiWeek = true;
                break;
            case "TUT_FULL":
            default:
                this.tutExists = true;
                this.tutBiWeek = false;
                break;
        }

        switch (info[2]) {
            case "NO_LAB":
                this.labExists = false;
                this.labBiWeek = false;
                break;
            case "LAB_HALF":
                this.labExists = true;
                this.labBiWeek = true;
                break;
            case "LAB_FULL":
            default:
                this.labExists = true;
                this.labBiWeek = false;
                break;
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.secLecExists) {
            sb.append("SEC_LEC ");
        } else {
            sb.append("LEC ");
        }

        if (this.tutExists) {
            if (this.tutBiWeek) {
                sb.append("TUT_HALF ");
            } else {
                sb.append("TUT_FULL ");
            }
        } else {
            sb.append("NO_TUT ");
        }

        if (this.labExists) {
            if (this.labBiWeek) {
                sb.append("LAB_HALF");
            } else {
                sb.append("LAB_FULL");
            }
        } else {
            sb.append("NO_LAB");
        }
        sb.append("\r\n\r\n");
        return sb.toString();
    }
}
