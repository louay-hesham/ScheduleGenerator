package GUI.SubjectsPanel.NormalMode;

import Core.Subject.ChaosMode.SubjectChaos;
import Core.Subject.NormalMode.GroupNormal;
import Core.Subject.NormalMode.SubjectNormal;
import Core.Subject.Subject;
import GUI.MainGUI;
import GUI.SubjectsPanel.ChaosMode.SubjectPanelChaos;
import GUI.SubjectsPanel.SubjectPanel;

import javax.swing.*;
import java.util.ArrayList;


/*
 * Created by louay on 10/28/2016.
 */
public class SubjectPanelNormal extends SubjectPanel {

    private final ArrayList<GroupPanelNormal> groups;
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
        super(gui, subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        groups = new ArrayList<>();
        this.initComponents();
        this.newGroupButtonActionPerformed();
    }

    public SubjectPanelNormal(MainGUI gui, SubjectNormal sub) {
        super(gui, sub.getSubjectName(), sub.getInfo());
        groups = new ArrayList<>();
        for (int i = 0; i < sub.getGroups().size(); i++) {
            this.newGroupButtonActionPerformed();
            this.groups.get(i).setGroup(sub.getGroups().get(i));
        }
        this.initComponents();
    }

    private void newGroupButtonActionPerformed() {
        GroupPanelNormal newGroup = new GroupPanelNormal(this, subjectInfo);
        this.groups.add(newGroup);
        this.groupsTabbedPane.addTab("Group " + (groupsTabbedPane.getTabCount() + 1), newGroup.getMainPanel());
    }

    private void deleteSubjectButtonActionPerformed() {
        this.gui.deleteSubject(this);
    }

    protected void initComponents() {
        this.newGroupButton.addActionListener(e -> newGroupButtonActionPerformed());
        this.deleteSubjectButton.addActionListener(e -> deleteSubjectButtonActionPerformed());
    }

    void deleteGroup(GroupPanelNormal group) {
        if (this.groups.size() == 1) {

            JOptionPane.showMessageDialog(null,
                    "Must at least have one group.",
                    "Can't Delete",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete this group?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (choice == 0) {
                this.groups.remove(group);
                this.groupsTabbedPane.remove(group.getMainPanel());
            }
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
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
        return new SubjectPanelChaos(this.gui, this.subjectName, this.subjectInfo, chaos);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.subjectName + "\r\n");
        sb.append(this.groups.size() + "!");
        sb.append(this.subjectInfo.toString());
        sb.append(this.getSubject().toString());
        return sb.toString();
    }
}
