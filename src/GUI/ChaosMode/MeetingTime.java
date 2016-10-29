package GUI.ChaosMode;

import javax.swing.*;

/*
 * Created by louay on 10/28/2016.
 */
public class MeetingTime {

    private static int totalIDs = 0;

    private final boolean secLec;
    private final Meeting meeting;
    private final int ID;

    private void deleteMeetingButtonActionPerformed() {
        meeting.deleteTime(this);
    }

    /**
     * Creates new form MeetingTime
     *
     * @param meetingType /
     * @param meeting     /
     */
    MeetingTime(Meeting.MeetingType meetingType, Meeting meeting) {
        this.meeting = meeting;
        this.secLec = meetingType == Meeting.MeetingType.LECTURE_WITH_SEC;
        String type;
        switch (meetingType) {
            case LECTURE:
            case LECTURE_WITH_SEC:
                type = "Lecture";
                break;

            case TUTORIAL:
                type = "Tutorial";
                break;

            case LAB:
                type = "Lab";
                break;

            default:
                type = "Unknown type";
                break;
        }
        this.secLecLabel.setVisible(secLec);
        this.secLecDay.setVisible(secLec);
        this.secLecPeriodLabel.setVisible(secLec);
        this.secLecPeriodSpinner.setVisible(secLec);
        this.meetingTypeLabel.setText(type);
        this.ID = MeetingTime.totalIDs;
        MeetingTime.totalIDs++;
        deleteMeetingButton.addActionListener(e -> deleteMeetingButtonActionPerformed());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    void setMeetingTime(int day, int period) {
        this.meetingDay.setSelectedIndex(day);
        this.meetingPeriodSpinner.setValue(period);
    }

    void setSecLecTime(int day, int period) {
        if (this.secLec) {
            this.secLecDay.setSelectedIndex(day);
            this.secLecPeriodSpinner.setValue(period);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.ID;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MeetingTime other = (MeetingTime) obj;
        return this.ID == other.ID;
    }

    private JPanel mainPanel;
    private JComboBox meetingDay;
    private JComboBox secLecDay;
    private JSpinner meetingPeriodSpinner;
    private JSpinner secLecPeriodSpinner;
    private JButton deleteMeetingButton;
    private JLabel meetingTypeLabel;
    private JLabel secLecLabel;
    private JLabel meetingPeriodLabel;
    private JLabel secLecPeriodLabel;
}
