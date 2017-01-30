package GUI.ChaosMode;

import Core.Generator.Time;
import Core.Generator.Time.MeetingType;

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

    private void deleteMeetingButtonActionPerformed() {
        meeting.deleteTime(this);
    }

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

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initComponents(){
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

    public Time getMeetingTime(){
        return new Time(this.meetingDay.getSelectedIndex(),
                        (int)this.meetingPeriodSpinner.getValue(),
                        (int)this.meetingPeriodSpinner.getValue(),
                        this.type
                        );
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
