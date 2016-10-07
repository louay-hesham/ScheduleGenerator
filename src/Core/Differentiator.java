/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core;

import GUI.PreGenerator.MainGUI;
import java.util.ArrayList;

/**
 *
 * @author louay
 */
public class Differentiator {
    public static void differentiate(MainGUI gui, ArrayList<String[][]> schedules) throws Exception{
        for (String[][] schedule : schedules){
            for (int i=0; i<schedule.length; i++){
                for (int j=0; j<schedule[i].length; j++){
                    schedule[i][j] += gui.getMeetingType(schedule[i][j], j, (i/2)+1 );
                }
            }
        }
    }
}
