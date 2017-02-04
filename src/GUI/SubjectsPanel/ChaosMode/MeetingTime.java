package GUI.SubjectsPanel.ChaosMode;

import Core.Subject.MeetingType;
import Core.Subject.Time;

import javax.swing.*;

/*
 * Created by louay on 10/28/2016.
 */
class MeetingTime {

    private static int totalIDs = 0;

    private final boolean secLec;
    private final Meeting meeting;
    private final int ID;
    private final MeetingType type;
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

    /**
     * Creates new form MeetingTime
     *
     * @param meetingType /
     * @param meeting     /
     */
    MeetingTime(MeetingType meetingType, Meeting meeting) {
        this.type = meetingType;
        this.meeting = meeting;
        this.secLec = meetingType == MeetingType.SEC_LECTURE;
        String type;
        switch (meetingType) {
            case LECTURE:
            case SEC_LECTURE:
                type = "Lecture";
                break;

            case TUT_FULL:
            case TUT_HALF:
                type = "Tutorial";
                break;

            case LAB_FULL:
            case LAB_HALF:
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
        this.initComponents();
    }

    private void deleteMeetingButtonActionPerformed() {
        meeting.deleteTime(this);
    }

    private void initComponents() {
        deleteMeetingButton.addActionListener(e -> deleteMeetingButtonActionPerformed());
        meetingPeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        secLecPeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
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

    public Time getMeetingTime() {
        MeetingType type = this.type;
        if (this.type == MeetingType.SEC_LECTURE) {
            type = MeetingType.LECTURE;
        }
        int from, to;
        to = ((int) this.meetingPeriodSpinner.getValue() * 2) - 1;
        switch (this.type) {
            case LECTURE:
            case SEC_LECTURE:
            case LAB_FULL:
            case TUT_FULL:
                from = to - 1;
                break;
            case TUT_HALF:
            case LAB_HALF:
                from = to;
                break;
            default:
                from = to;
        }

        return new Time(this.meetingDay.getSelectedIndex(), from, to, type);
    }

    public Time getSecLecTime() {
        if (this.type != MeetingType.SEC_LECTURE) {
            return null;
        }
        int from, to;
        if ((int) this.secLecPeriodSpinner.getValue() + 1 == (int) this.meetingPeriodSpinner.getValue()) {
            from = ((int) this.secLecPeriodSpinner.getValue() * 2) - 1;
            to = from;
        } else {
            from = ((int) this.secLecPeriodSpinner.getValue() * 2) - 2;
            to = from;
        }
        return new Time(this.secLecDay.getSelectedIndex(), from, to, MeetingType.SEC_LECTURE);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
