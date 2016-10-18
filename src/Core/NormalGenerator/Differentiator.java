/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.NormalGenerator;

import GUI.MainGUI;
import java.util.ArrayList;

/**
 *
 * @author louay
 */
public class Differentiator {

    public static void differentiate(MainGUI gui, ArrayList<String[][]> schedules) {
        for (int k = 0; k < schedules.size(); k++) {
            String[][] schedule = schedules.get(k);
            boolean corrupted = false;
            for (int i = 0; i < schedule.length; i++) {
                if (corrupted || schedule[i] == null) {
                    corrupted = true;
                    break;
                }
                for (int j = 0; j < schedule[i].length; j++) {
                    if (schedule[i][j] == null) {
                        corrupted = true;
                        break;
                    }
                    if (!schedule[i][j].equals("___")) {
                        schedule[i][j] = schedule[i][j].replaceAll("_", " ");
                        try {
                            schedule[i][j] += ("!" + gui.getMeetingType(schedule[i][j], j, (i / 2) + 1));
                        } catch (Exception ex) {
                            if (ex.getMessage().startsWith("Meeting Not Found!")){
                                schedule[i][j] += ("!" + "Unknown meeting type");
                            } else {
                                corrupted = true;
                                break;
                            }
                        }
                    } else {
                        schedule[i][j] = "";
                    }
                }
            }
            if (corrupted) {
                schedules.remove(schedule);
                k--;
            }
        }
    }
}
