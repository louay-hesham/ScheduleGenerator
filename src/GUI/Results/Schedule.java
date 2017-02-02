package GUI.Results;

import javax.swing.*;

/*
 * Created by louay on 10/28/2016.
 */
class Schedule {

    private final String[][] schedule;

    /**
     * Creates new form Schedule
     *
     * @param schedule /
     */
    Schedule(String[][] schedule) {
        this.schedule = schedule;
        MultiLineTableCellRenderer renderer = new MultiLineTableCellRenderer();
        this.timetable.setDefaultRenderer(String.class, renderer);
        setTableModel();
        int lines = 2;
        this.timetable.setRowHeight(this.timetable.getRowHeight() * (lines + 1));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void setTableModel() {

        Object[][] model = new Object[14][8];
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 8; j++) {
                if (j == 0) {
                    model[i][j] = i + 1;
                } else if (j != 7) {
                    model[i][j] = schedule[i][j - 1];
                } else {
                    model[i][j] = " ";
                }
            }
        }

        timetable.setModel(new javax.swing.table.DefaultTableModel(
                model,
                new String[]{
                        "Period", "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"
                }
        ) {
            final Class[] types = new Class[]{
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            final boolean[] canEdit = new boolean[]{
                    false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    private JPanel mainPanel;
    private JTable timetable;
}
