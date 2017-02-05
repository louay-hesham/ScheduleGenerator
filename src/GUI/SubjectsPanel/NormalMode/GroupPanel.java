package GUI.SubjectsPanel.NormalMode;

import Core.Subject.NormalMode.GroupNormal;
import GUI.SubjectsPanel.SubjectInfo;

import javax.swing.*;

/**
 * Created by louay on 2/5/2017.
 */
public abstract class GroupPanel {
    protected final SubjectInfo subjectInfo;
    protected final SubjectPanelNormal subject;

    GroupPanel(SubjectPanelNormal subject, SubjectInfo subjectInfo) {
        this.subject = subject;
        this.subjectInfo = subjectInfo;
    }

    protected void deleteGroupButtonActionPerformed() {
        this.subject.deleteGroup(this);
    }

    protected abstract void initComponents();

    protected abstract GroupNormal getGroup();

    protected abstract void setGroup(GroupNormal group);

    public abstract JPanel getMainPanel();
}
