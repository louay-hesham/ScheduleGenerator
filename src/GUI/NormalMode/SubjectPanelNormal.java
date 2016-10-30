package GUI.NormalMode;

import Core.InfoHelpers.GroupInfo;
import Core.InfoHelpers.SubjectInfo;
import GUI.ChaosMode.SubjectPanelChaos;
import GUI.MainGUI;
import GUI.NormalMode.GroupPanelNormal.SecondLecturePossibility;

import javax.swing.*;
import java.util.ArrayList;


/*
 * Created by louay on 10/28/2016.
 */
public class SubjectPanelNormal {

    private final SubjectInfo subjectInfo;
    private final ArrayList<GroupPanelNormal> groups;
    private final String subjectName;
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
        this.gui = gui;
        subjectInfo = new SubjectInfo(secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        groups = new ArrayList<>();
        this.subjectName = subjectName;
        chaosVersion = new SubjectPanelChaos(subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        this.initComponents();
    }

    private void initComponents() {
        newGroupButton.addActionListener(e -> newGroupButtonActionPerformed());
        deleteSubjectButton.addActionListener(e -> deleteSubjectButtonActionPerformed());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    void resetChaosPanel() {
        this.chaosVersion.reset();
        for (GroupPanelNormal group : groups) {
            this.chaosVersion.addGroup(group.getGroupInfo());
        }
    }

    public void setGroups(ArrayList<GroupInfo> groupsInfo) {
        for (GroupInfo groupInfo : groupsInfo) {
            GroupPanelNormal newGroup = new GroupPanelNormal(this, subjectInfo);
            groups.add(newGroup);
            groupsTabbedPane.addTab("Group " + (groupsTabbedPane.getTabCount() + 1), newGroup.getMainPanel());
            newGroup.setGroupInfo(groupInfo);
            this.chaosVersion.addGroup(groupInfo);
        }
    }

    public String getMeetingType(int day, int period) throws Exception {
        for (GroupPanelNormal group : groups) {
            try {
                return group.getMeetingType(day, period);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        throw new Exception("Not found in this subject: " + this.subjectName);
    }

    public String generateSubjectString() throws Exception {
        if (this.groups == null || this.groups.isEmpty()) {
            throw new Exception(this.subjectName + " has no groups!");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.removeSpaces(this.subjectName)).append("\r\n");
        sb.append(this.groupsTabbedPane.getTabCount()).append(" ");

        SecondLecturePossibility p = SecondLecturePossibility.NO_SEC_LEC;
        if (this.subjectInfo.secLecExists) {
            for (GroupPanelNormal g : this.groups) {
                SecondLecturePossibility current = g.checkSecLecturePossibility();
                if (current.ordinal() == 0) {
                    break;
                } else if (current.ordinal() > p.ordinal()) {
                    p = current;
                }
            }
            if (p.equals(SecondLecturePossibility.NOT_POSSIBLE)) {
                throw new Exception("Unhandled case: secondary lecture is far from main lecture AND lab and tutorial exist.");
            } else {
                for (GroupPanelNormal g : this.groups) {
                    g.setSecondLecturePlace(p);
                }
            }
        }

        switch (p) {
            case NO_SEC_LEC:
            case MAIN_LECTURE: {
                if (!this.subjectInfo.tutExists) {
                    sb.append("0 ");
                } else {
                    sb.append("2 ");
                }

                if (!this.subjectInfo.labExists) {
                    sb.append("0\r\n\r\n");
                } else {
                    sb.append("2\r\n\r\n");
                }
                break;
            }

            case TUTORIAL: {
                sb.append("1 ");
                if (!this.subjectInfo.labExists) {
                    sb.append("0\r\n\r\n");
                } else {
                    sb.append("2\r\n\r\n");
                }
                break;
            }

            case LAB: {
                if (!this.subjectInfo.tutExists) {
                    sb.append("0 ");
                } else {
                    sb.append("2 ");
                }
                sb.append("1\r\n\r\n");
                break;
            }
        }

        for (GroupPanelNormal g : this.groups) {
            sb.append(g.generateString()).append("\r\n");
        }

        return sb.toString();
    }

    void deleteGroup(GroupPanelNormal group) {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete this group?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == 0) {
            this.groups.remove(group);
            this.groupsTabbedPane.remove(group.getMainPanel());
        }
    }

    private String removeSpaces(String str) {
        return str.replaceAll(" ", "_");
    }

    private JPanel mainPanel;
    private JButton newGroupButton;
    private JButton deleteSubjectButton;
    private JTabbedPane groupsTabbedPane;
}
