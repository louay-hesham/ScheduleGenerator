package GUI.SubjectsPanel.NormalMode;

import Core.Subject.ChaosMode.SubjectChaos;
import Core.Subject.NormalMode.GroupNormal;
import Core.Subject.NormalMode.SubjectNormal;
import Core.Subject.Subject;
import GUI.SubjectsPanel.ChaosMode.SubjectPanelChaos;
import GUI.MainGUI;
import GUI.SubjectsPanel.SubjectInfo;
import GUI.SubjectsPanel.SubjectPanel;

import javax.swing.*;
import java.util.ArrayList;


/*
 * Created by louay on 10/28/2016.
 */
public class SubjectPanelNormal extends SubjectPanel {

    private final ArrayList<GroupPanelNormal> groups;
    private final MainGUI gui;
    private JPanel mainPanel;
    private JButton newGroupButton;
    private JButton deleteSubjectButton;
    private JTabbedPane groupsTabbedPane;

    /**
     * Creates new form SubjectPanel
     *
     * @param subjectName  /
     * @param secLecExists /
     * @param tutExists    /
     * @param tutBiWeek    /
     * @param labExists    /
     * @param labBiweek    /
     */
    public SubjectPanelNormal(MainGUI gui, String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        super(subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        this.gui = gui;
        groups = new ArrayList<>();
        this.initComponents();
    }

    //WIP for file loader
    public SubjectPanelNormal(MainGUI gui, String subjectName, SubjectInfo info, Subject sub) {
        super(subjectName, info, sub);
        this.gui = gui;
        groups = new ArrayList<>();
    }

    private void newGroupButtonActionPerformed() {
        GroupPanelNormal newGroup = new GroupPanelNormal(this, subjectInfo);
        groups.add(newGroup);
        groupsTabbedPane.addTab("Group " + (groupsTabbedPane.getTabCount() + 1), newGroup.getMainPanel());
    }

    private void deleteSubjectButtonActionPerformed() {
        this.gui.deleteSubject(this);
    }

    protected void initComponents() {
        newGroupButton.addActionListener(e -> newGroupButtonActionPerformed());
        deleteSubjectButton.addActionListener(e -> deleteSubjectButtonActionPerformed());
    }

    void deleteGroup(GroupPanelNormal group) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete this group?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == 0) {
            this.groups.remove(group);
            this.groupsTabbedPane.remove(group.getMainPanel());
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public Subject getSubject() {
        ArrayList<GroupNormal> grps = new ArrayList<>();
        for (GroupPanelNormal g : this.groups) {
            grps.add(g.getGroup());
        }
        return new SubjectNormal(this.subjectName, grps, this.subjectInfo);
    }

    public SubjectPanelChaos getChaos() {
        SubjectChaos chaos = ((SubjectNormal) this.getSubject()).getChaos();
        return new SubjectPanelChaos(this.subjectName, this.subjectInfo, chaos);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.subjectName + "\r\n");
        sb.append(this.groups.size() + " ");
        sb.append(this.subjectInfo.toString());
        sb.append(this.getSubject().toString());
        return sb.toString();
    }
}
