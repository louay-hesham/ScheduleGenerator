package GUI.ResultsWindow;

import javax.swing.*;
import java.util.ArrayList;

/*
 * Created by louay on 10/28/2016.
 */
public class ResultMainGUI {
    @SuppressWarnings("")
    private final ArrayList<Schedule> schedules;
    private JPanel mainPanel;
    private JTabbedPane schedulesTabbedPane;

    /**
     * Creates new form ResultMainGui
     *
     * @param results ArrayList
     */
    public ResultMainGUI(ArrayList<String[][]> results) {
        schedules = new ArrayList<>();
        int i = 1;
        for (String[][] result : results) {
            Schedule schedule = new Schedule(result);
            schedules.add(schedule);
            schedulesTabbedPane.addTab("Schedule " + i, schedule.getMainPanel());
            i++;
        }
        JFrame frame = new JFrame("Generated Schedules");
        frame.setContentPane(this.mainPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
