package GUI.ChaosMode;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by louay on 10/28/2016.
 */
public class Meeting {

    public enum MeetingType {
        LECTURE, LECTURE_WITH_SEC, TUTORIAL, LAB
    }

    public enum Day {
        SATURDAY, SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY
    }

    private final MeetingType meetingType;
    private final ArrayList<MeetingTime> meetings;

    private void newTimeButtonActionPerformed() {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetings.add(meetingTime);
        this.resetMeetingsGUI();
    }

    public Meeting(MeetingType meetingType) {
        this.meetingType = meetingType;
        this.meetings = new ArrayList<>();
        newTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newTimeButtonActionPerformed();
            }
        });
        timesPanel.setLayout(new GroupLayout(timesPanel));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void addMeeting(int day, int period) {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetingTime.setMeetingTime(day, period);
        meetings.add(meetingTime);
        this.resetMeetingsGUI();
    }

    public void addMeeting(int day, int period, int secLecDay, int secLecPeriod) {
        MeetingTime meetingTime = new MeetingTime(this.meetingType, this);
        meetingTime.setMeetingTime(day, period);
        meetingTime.setSecLecTime(secLecDay, secLecPeriod);
        meetings.add(meetingTime);
        this.resetMeetingsGUI();
    }

    public void deleteTime(MeetingTime time) {
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

    private void resetMeetingsGUI() {

        timesPanel.removeAll();

        GroupLayout timesPanelLayout = new GroupLayout(timesPanel);

        GroupLayout.ParallelGroup h = timesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        GroupLayout.SequentialGroup v = timesPanelLayout.createSequentialGroup();

        for (MeetingTime meeting : meetings) {
            h.addComponent(meeting.getMainPanel());
            v.addComponent(meeting.getMainPanel());
        }
        timesPanelLayout.setHorizontalGroup(h);
        timesPanelLayout.setVerticalGroup(v);

        timesPanel.setLayout(timesPanelLayout);
        timesPanel.repaint();
    }

    private JButton newTimeButton;
    private JPanel timesPanel;
    private JPanel mainPanel;
}
