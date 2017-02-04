package GUI.SubjectsPanel.ChaosMode;

import Core.Subject.MeetingType;
import Core.Subject.Time;

import javax.swing.*;
import java.util.ArrayList;

/*
 * Created by louay on 10/28/2016.
 */
public class Meeting {

    private final MeetingType meetingType;
    private final ArrayList<MeetingTime> meetings;

    private GroupLayout.ParallelGroup h;
    private GroupLayout.SequentialGroup v;
    private JButton newTimeButton;
    private JPanel timesPanel;
    private JPanel mainPanel;

    public Meeting(MeetingType meetingType) {
        this.meetingType = meetingType;
        this.meetings = new ArrayList<>();
        newTimeButton.addActionListener(e -> newTimeButtonActionPerformed());
        timesPanel.setLayout(new GroupLayout(timesPanel));
        GroupLayout timesPanelLayout = (GroupLayout) timesPanel.getLayout();
        this.h = timesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        this.v = timesPanelLayout.createSequentialGroup();
    }

    protected void newTimeButtonActionPerformed() {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetings.add(meetingTime);
        this.addMeetingToGUI(meetingTime);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ArrayList<Time> getMeetingTimes() {
        ArrayList<Time> times = new ArrayList<>();
        for (MeetingTime m : meetings) {
            times.add(m.getMeetingTime());
        }
        return times;
    }

    public ArrayList<Time> getSecLecTimes() {
        if (this.meetingType != MeetingType.SEC_LECTURE) {
            return null;
        }
        ArrayList<Time> times = new ArrayList<>();
        for (MeetingTime m : meetings) {
            times.add(m.getSecLecTime());
        }
        return times;
    }

    public void addMeetings(ArrayList<Time> times) {
        if (times == null) {
            return;
        }
        for (Time t : times) {
            this.addMeeting(t);
        }
    }

    public void addMeetings(ArrayList<Time> lecTimes, ArrayList<Time> secLecTimes) {
        for (int i = 0; i < lecTimes.size(); i++) {
            this.addMeeting(lecTimes.get(i), secLecTimes.get(i));
        }
    }

    private void addMeeting(Time t) {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetingTime.setMeetingTime(t.day, t.period);
        meetings.add(meetingTime);
        this.addMeetingToGUI(meetingTime);
    }

    private void addMeeting(Time lec, Time secLec) {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetingTime.setMeetingTime(lec.day, lec.period);
        meetingTime.setSecLecTime(secLec.day, secLec.period);
        this.meetings.add(meetingTime);
        this.addMeetingToGUI(meetingTime);
    }

    void deleteTime(MeetingTime time) {
        if (this.meetings.size() == 1) {

            JOptionPane.showMessageDialog(null,
                    "Must at least have one " + this.meetingType.getTypeStringSimplified() + ".",
                    "Can't Delete",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete this " + this.meetingType.getTypeStringSimplified() + "?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == 0) {
                int i = 0;
                for (int j = 0; j < this.meetings.size(); j++) {
                    if (time.equals(this.meetings.get(j))) {
                        i = j;
                        break;
                    }
                }
                this.meetings.remove(i);
                this.resetMeetingsGUI();
            }
        }
    }

    private void addMeetingToGUI(MeetingTime meetingTime) {
        GroupLayout timesPanelLayout = (GroupLayout) timesPanel.getLayout();

        this.h.addComponent(meetingTime.getMainPanel());
        this.v.addComponent(meetingTime.getMainPanel());

        timesPanelLayout.setHorizontalGroup(this.h);
        timesPanelLayout.setVerticalGroup(this.v);
    }

    private void resetMeetingsGUI() {

        timesPanel.removeAll();

        GroupLayout timesPanelLayout = new GroupLayout(timesPanel);

        this.h = timesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        this.v = timesPanelLayout.createSequentialGroup();

        for (MeetingTime meeting : meetings) {
            this.h.addComponent(meeting.getMainPanel());
            this.v.addComponent(meeting.getMainPanel());
        }
        timesPanelLayout.setHorizontalGroup(this.h);
        timesPanelLayout.setVerticalGroup(this.v);

        timesPanel.setLayout(timesPanelLayout);
        timesPanel.repaint();
    }
}
