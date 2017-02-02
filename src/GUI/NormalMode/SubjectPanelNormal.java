package GUI.NormalMode;

import Core.Generator.NormalMode.GroupNormal;
import Core.Generator.NormalMode.SubjectNormal;
import Core.Generator.Subject;
import GUI.ChaosMode.SubjectPanelChaos;
import GUI.MainGUI;
import GUI.SubjectPanel;

import javax.swing.*;
import java.util.ArrayList;


/*
 * Created by louay on 10/28/2016.
 */
public class SubjectPanelNormal extends SubjectPanel{

    private final ArrayList<GroupPanelNormal> groups;
    private final MainGUI gui;
    private final SubjectPanelChaos chaosVersion;

    public String getSubjectName() {
        return subjectName;
    }

    public SubjectPanelChaos getChaosVersion() {
        return chaosVersion;
    }

    private void newGroupButtonActionPerformed() {
        GroupPanelNormal newGroup = new GroupPanelNormal(this, subjectInfo);
        groups.add(newGroup);
        groupsTabbedPane.addTab("Group " + (groupsTabbedPane.getTabCount() + 1), newGroup.getMainPanel());
    }

    private void deleteSubjectButtonActionPerformed() {
        this.gui.deleteSubject(this);
    }

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
        chaosVersion = new SubjectPanelChaos(subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        this.initComponents();
    }

    protected void initComponents() {
        newGroupButton.addActionListener(e -> newGroupButtonActionPerformed());
        deleteSubjectButton.addActionListener(e -> deleteSubjectButtonActionPerformed());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    void deleteGroup(GroupPanelNormal group) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete this group?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == 0) {
            this.groups.remove(group);
            this.groupsTabbedPane.remove(group.getMainPanel());
        }
    }

    public Subject getSubject(){
        ArrayList<GroupNormal> grps = new ArrayList<>();
        for (GroupPanelNormal g : this.groups){
            grps.add(g.getGroup());
        }
        return new SubjectNormal(this.subjectName, grps);
    }

    private JPanel mainPanel;
    private JButton newGroupButton;
    private JButton deleteSubjectButton;
    private JTabbedPane groupsTabbedPane;
}
