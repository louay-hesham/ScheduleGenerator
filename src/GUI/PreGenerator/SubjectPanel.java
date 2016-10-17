/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.PreGenerator;

import Core.InfoHelpers.GroupInfo;
import Core.InfoHelpers.SubjectInfo;
import GUI.PreGenerator.GroupPanel.SecondLecturePossibility;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author louay
 */
public class SubjectPanel extends javax.swing.JPanel {

    private final SubjectInfo subjectInfo;
    private final ArrayList<GroupPanel> groups;
    private final String subjectName;
    private final MainGUI gui;

    public String getSubjectName() {
        return subjectName;
    }

    /**
     * Creates new form SubjectPanel
     *
     * @param subjectName
     * @param secLecExists
     * @param tutExists
     * @param tutBiWeek
     * @param labExists
     * @param labBiweek
     */
    public SubjectPanel(MainGUI gui, String subjectName, boolean secLecExists, boolean tutExists, boolean tutBiWeek, boolean labExists, boolean labBiweek) {
        this.gui = gui;
        subjectInfo = new SubjectInfo(secLecExists, tutExists, tutBiWeek, labExists, labBiweek);
        groups = new ArrayList<>();
        this.subjectName = subjectName;
        initComponents();
    }

    public void setGroups(ArrayList<GroupInfo> groupsInfo) {
        for (GroupInfo groupInfo : groupsInfo) {
            GroupPanel newGroup = new GroupPanel(this, subjectInfo);
            groups.add(newGroup);
            groupsTabbedPane.addTab("Group " + (groupsTabbedPane.getTabCount() + 1), newGroup);
            newGroup.setGroupInfo(groupInfo);
        }
    }
    
    public String getMeetingType(int day, int period) throws Exception{
        for (GroupPanel group : groups){
            try {
                return group.getMeetingType(day, period);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        throw new Exception ("Not found in this subject: " + this.subjectName);
    }

    public String generateSubjectString() throws Exception {
        if (this.groups == null || this.groups.isEmpty()){
            throw new Exception(this.subjectName + " has no groups!");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.removeSpaces(this.subjectName)).append("\r\n");
        sb.append(this.groupsTabbedPane.getTabCount()).append(" ");

        SecondLecturePossibility p = SecondLecturePossibility.NO_SEC_LEC;
        if (this.subjectInfo.secLecExists) {
            for (GroupPanel g : this.groups) {
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
                for (GroupPanel g : this.groups) {
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

        for (GroupPanel g : this.groups) {
            sb.append(g.generateString()).append("\r\n");
        }

        return sb.toString();
    }
    
    protected void deleteGroup(GroupPanel group){
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure wou want to delete this group?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (choice == 0){
            this.groups.remove(group);
            this.groupsTabbedPane.remove(group);
        }
    }
    
    private String removeSpaces(String str){
        return str.replaceAll(" ", "_");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        groupsTabbedPane = new javax.swing.JTabbedPane();
        newGroupButton = new javax.swing.JButton();
        deleteSubjectButton = new javax.swing.JButton();

        newGroupButton.setText("New group");
        newGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGroupButtonActionPerformed(evt);
            }
        });

        deleteSubjectButton.setBackground(new java.awt.Color(255, 0, 0));
        deleteSubjectButton.setText("Delete Subject");
        deleteSubjectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteSubjectButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(groupsTabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(newGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(deleteSubjectButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(newGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteSubjectButton, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(groupsTabbedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void newGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGroupButtonActionPerformed
        GroupPanel newGroup = new GroupPanel(this, subjectInfo);
        groups.add(newGroup);
        groupsTabbedPane.addTab("Group " + (groupsTabbedPane.getTabCount() + 1), newGroup);
    }//GEN-LAST:event_newGroupButtonActionPerformed

    private void deleteSubjectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteSubjectButtonActionPerformed
        this.gui.deleteSubject(this);
    }//GEN-LAST:event_deleteSubjectButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteSubjectButton;
    private javax.swing.JTabbedPane groupsTabbedPane;
    private javax.swing.JButton newGroupButton;
    // End of variables declaration//GEN-END:variables
}
