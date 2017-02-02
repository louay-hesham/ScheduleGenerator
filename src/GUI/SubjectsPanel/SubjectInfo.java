/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.SubjectsPanel;

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

    public String toString(){
        StringBuilder sb = new StringBuilder();
        if (this.secLecExists){
            sb.append("SEC_LEC ");
        } else {
            sb.append("LEC ");
        }

        if (this.tutExists){
            if (this.tutBiWeek){
                sb.append("TUT_HALF ");
            } else {
                sb.append("TUT_FULL ");
            }
        } else {
            sb.append("NO_TUT ");
        }

        if (this.labExists){
            if (this.labBiWeek){
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
