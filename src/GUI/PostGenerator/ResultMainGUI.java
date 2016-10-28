package GUI.PostGenerator;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by louay on 10/28/2016.
 */
public class ResultMainGUI {

    ArrayList<Schedule> schedules;

    /**
     * Creates new form ResultMainGui
     * @param results
     */
    public ResultMainGUI(ArrayList<String[][]> results) {
        schedules = new ArrayList<>();
        int i = 1;
        for (String[][] result : results){
            Schedule schedule = new Schedule(result);
            schedules.add(schedule);
            schedulesTabbedPane.addTab("Schedule " + i, schedule.getMainPanel());
            i++;
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private JPanel mainPanel;
    private JTabbedPane schedulesTabbedPane;
}
