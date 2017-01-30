package GUI.ChaosMode;

import Core.Generator.Time;
import Core.Generator.Time.MeetingType;

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

    private void newTimeButtonActionPerformed() {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetings.add(meetingTime);
        this.addMeetingToGUI(meetingTime);
    }

    public Meeting(MeetingType meetingType) {
        this.meetingType = meetingType;
        this.meetings = new ArrayList<>();
        newTimeButton.addActionListener(e -> newTimeButtonActionPerformed());
        timesPanel.setLayout(new GroupLayout(timesPanel));
        GroupLayout timesPanelLayout = (GroupLayout) timesPanel.getLayout();
        this.h = timesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        this.v = timesPanelLayout.createSequentialGroup();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public ArrayList<Time> getMeetingTimes(){
        ArrayList<Time> times = new ArrayList<>();
        for (MeetingTime m : meetings){
            times.add(m.getMeetingTime());
        }
        return times;
    }

    public ArrayList<Time> getSecLecTimes(){
        if (this.meetingType != MeetingType.SEC_LECTURE){
            return null;
        }
        ArrayList<Time> times = new ArrayList<>();
        for (MeetingTime m : meetings){
            times.add(m.getSecLecTime());
        }
        return times;
    }

    void addMeeting(int day, int period) {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetingTime.setMeetingTime(day, period);
        meetings.add(meetingTime);
        this.addMeetingToGUI(meetingTime);
    }

    void addMeeting(int day, int period, int secLecDay, int secLecPeriod) {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetingTime.setMeetingTime(day, period);
        meetingTime.setSecLecTime(secLecDay, secLecPeriod);
        this.meetings.add(meetingTime);
        this.addMeetingToGUI(meetingTime);
    }

    void deleteTime(MeetingTime time) {
        int i = 0;
        for (int j = 0; j < meetings.size(); j++) {
            if (time.equals(meetings.get(j))) {
                i = j;
                break;
            }
        }
        this.meetings.remove(i);
        this.resetMeetingsGUI();
    }

    private void addMeetingToGUI(MeetingTime meetingTime){
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

    private JButton newTimeButton;
    private JPanel timesPanel;
    private JPanel mainPanel;
}
