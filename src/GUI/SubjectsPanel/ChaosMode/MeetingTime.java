package GUI.SubjectsPanel.ChaosMode;

import Core.Subject.MeetingType;
import Core.Subject.Time;

import javax.swing.*;

/**
 * Created by louay on 2/6/2017.
 */
public abstract class MeetingTime {

    private static int totalIDs = 0;

    protected final int ID;
    protected final boolean secLec;
    protected final MeetingsPanel meeting;
    protected final MeetingType type;

    public MeetingTime(MeetingType meetingType, MeetingsPanel meeting) {
        this.ID = MeetingTime.totalIDs;
        MeetingTime.totalIDs++;
        this.type = meetingType;
        this.meeting = meeting;
        this.secLec = meetingType == MeetingType.SEC_LECTURE;
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
        final MeetingTimeSimple other = (MeetingTimeSimple) obj;
        return this.ID == other.ID;
    }

    protected void deleteMeetingButtonActionPerformed() {
        meeting.deleteTime(this);
    }

    protected abstract void initComponents();

    protected abstract void setMeetingTime(Time t);

    protected abstract void setSecLecTime(Time t);

    public abstract Time getMeetingTime();

    public abstract Time getSecLecTime();

    public abstract JPanel getMainPanel();
}
