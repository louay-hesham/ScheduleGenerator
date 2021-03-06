package GUI.SubjectsPanel.ChaosMode;

import Core.Subject.MeetingType;
import Core.Subject.Time;

import javax.swing.*;

/*
 * Created by louay on 10/28/2016.
 */
class MeetingTimeSimple extends MeetingTime{

    private JPanel mainPanel;
    private JComboBox meetingDay;
    private JComboBox secLecDay;
    private JSpinner meetingPeriodSpinner;
    private JSpinner secLecPeriodSpinner;
    private JButton deleteMeetingButton;
    private JLabel meetingTypeLabel;
    private JLabel secLecLabel;
    private JLabel secLecPeriodLabel;

    /**
     * Creates new form MeetingTime
     *
     * @param meetingType /
     * @param meeting     /
     */
    MeetingTimeSimple(MeetingType meetingType, MeetingsPanel meeting) {
        super(meetingType, meeting);
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
        this.initComponents();
    }

    protected void initComponents() {
        deleteMeetingButton.addActionListener(e -> deleteMeetingButtonActionPerformed());
        meetingPeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        secLecPeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
    }

    protected void setMeetingTime(Time t) {
        this.meetingDay.setSelectedIndex(t.day);
        this.meetingPeriodSpinner.setValue(t.period);
    }

    protected void setSecLecTime(Time t) {
        if (this.secLec) {
            this.secLecDay.setSelectedIndex(t.day);
            this.secLecPeriodSpinner.setValue(t.period);
        }
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
