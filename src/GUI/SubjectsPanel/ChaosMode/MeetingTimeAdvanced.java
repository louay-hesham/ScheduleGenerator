package GUI.SubjectsPanel.ChaosMode;

import Core.Subject.MeetingType;
import Core.Subject.Time;

import javax.swing.*;

/**
 * Created by louay on 2/6/2017.
 */
public class MeetingTimeAdvanced extends MeetingTime{
    private JPanel mainPanel;
    private JLabel meetingTypeLabel;
    private JComboBox meetingDay;
    private JSpinner meetingFromSpinner;
    private JButton deleteMeetingButton;
    private JComboBox secLecDay;
    private JLabel secLecLabel;
    private JLabel secLecFromLabel;
    private JSpinner secLecFromSpinner;
    private JSpinner meetingToSpinner;
    private JLabel secLecToLabel;
    private JSpinner secLecToSpinner;

    MeetingTimeAdvanced (MeetingType meetingType, MeetingsPanel meeting) {
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
        this.secLecFromLabel.setVisible(secLec);
        this.secLecToLabel.setVisible(secLec);
        this.secLecFromSpinner.setVisible(secLec);
        this.secLecToSpinner.setVisible(secLec);
        this.meetingTypeLabel.setText(type);
        this.initComponents();
    }

    protected void initComponents() {
        deleteMeetingButton.addActionListener(e -> deleteMeetingButtonActionPerformed());
        meetingFromSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        meetingToSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        secLecFromSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
        secLecToSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 14, 1));
    }

    protected void setMeetingTime(Time t) {
        this.meetingDay.setSelectedIndex(t.day);
        this.meetingFromSpinner.setValue(t.from);
        this.meetingToSpinner.setValue(t.to);
    }

    protected void setSecLecTime(Time t) {
        if (this.secLec) {
            this.secLecDay.setSelectedIndex(t.day);
            this.secLecFromSpinner.setValue(t.from);
            this.secLecToSpinner.setValue(t.to);
        }
    }

    public Time getMeetingTime() {
        MeetingType type = this.type;
        if (this.type == MeetingType.SEC_LECTURE) {
            type = MeetingType.LECTURE;
        }
        return new Time(this.meetingDay.getSelectedIndex(),
                (int)this.meetingFromSpinner.getValue(),
                (int)this.meetingToSpinner.getValue(),
                type);
    }

    public Time getSecLecTime() {
        if (this.type != MeetingType.SEC_LECTURE) {
            return null;
        }

        return new Time(this.secLecDay.getSelectedIndex(),
                (int)this.secLecFromSpinner.getValue(),
                (int)this.secLecToSpinner.getValue(),
                MeetingType.SEC_LECTURE);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
