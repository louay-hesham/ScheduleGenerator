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
public class SubjectInfo {

    public final boolean secLecExists, tutExists, tutBiWeek, labExists, labBiWeek;

    public SubjectInfo(boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        this.secLecExists = secLecExists;
        this.tutExists = tutExists;
        this.tutBiWeek = tutExists && tutBiWeek;
        this.labExists = labExists;
        this.labBiWeek = labExists && labBiweek;
    }

}
