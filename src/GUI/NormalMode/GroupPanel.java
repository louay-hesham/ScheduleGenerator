/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI.NormalMode;

import Core.InfoHelpers.GroupInfo;
import Core.InfoHelpers.SubjectInfo;
import Core.InfoHelpers.Time;
import java.util.ArrayList;

/**
 *
 * @author louay
 */
public class GroupPanel extends javax.swing.JPanel {

    private final SubjectInfo subjectInfo;
    private SecondLecturePossibility secondLecturePlace;
    private GroupInfo groupInfo = null;
    private final SubjectPanel subject;

    public enum SecondLecturePossibility {
        NO_SEC_LEC, MAIN_LECTURE, TUTORIAL, LAB, NOT_POSSIBLE
    }

    /**
     * Creates new form GroupPanel
     *
     * @param subject
     * @param subjectInfo
     */
    public GroupPanel(SubjectPanel subject, SubjectInfo subjectInfo) {
        this.subject = subject;
        this.secondLecturePlace = SecondLecturePossibility.NO_SEC_LEC;
        this.subjectInfo = subjectInfo;
        initComponents();

        secLectureDayComboBox.setVisible(subjectInfo.secLecExists);
        secLecturePeriodLabel.setVisible(subjectInfo.secLecExists);
        secLecturePeriodSpinner.setVisible(subjectInfo.secLecExists);
        secLecLabel.setVisible(subjectInfo.secLecExists);

        tut2Label.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2DayComboBox.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2PeriodSpinner.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        tut2PeriodLabel.setVisible(subjectInfo.tutExists && !subjectInfo.tutBiWeek);
        if (subjectInfo.tutBiWeek) {
            tut1Label.setText("Tutorial");
        } else {
            tut1Label.setText("Tutorial 1");
        }
        tut1Label.setVisible(subjectInfo.tutExists);
        tut1DayComboBox.setVisible(subjectInfo.tutExists);
        tut1PeriodSpinner.setVisible(subjectInfo.tutExists);
        tut1PeriodLabel.setVisible(subjectInfo.tutExists);

        lab2Label.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2DayComboBox.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2PeriodSpinner.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        lab2PeriodLabel.setVisible(subjectInfo.labExists && !subjectInfo.labBiWeek);
        if (subjectInfo.labBiWeek) {
            lab1Label.setText("Lab");
        } else {
            lab1Label.setText("Lab 1");
        }
        lab1Label.setVisible(subjectInfo.labExists);
        lab1DayComboBox.setVisible(subjectInfo.labExists);
        lab1PeriodSpinner.setVisible(subjectInfo.labExists);
        lab1PeriodLabel.setVisible(subjectInfo.labExists);
    }

    public void setGroupInfo(GroupInfo groupInfo) {
        this.groupInfo = groupInfo;
        this.lectureDayComboBox.setSelectedIndex(groupInfo.lecture.day);
        this.lecturePeriodSpinner.setValue(groupInfo.lecture.period);
        if (this.subjectInfo.secLecExists) {
            this.secLectureDayComboBox.setSelectedIndex(groupInfo.secLecture.day);
            this.secLecturePeriodSpinner.setValue(groupInfo.secLecture.period);
        }

        if (this.subjectInfo.tutExists) {
            this.tut1DayComboBox.setSelectedIndex(groupInfo.tutorial1.day);
            this.tut1PeriodSpinner.setValue(groupInfo.tutorial1.period);
            if (!this.subjectInfo.tutBiWeek) {
                this.tut2DayComboBox.setSelectedIndex(groupInfo.tutorial2.day);
                this.tut2PeriodSpinner.setValue(groupInfo.tutorial2.period);
            }
        }

        if (this.subjectInfo.labExists) {
            this.lab1DayComboBox.setSelectedIndex(groupInfo.lab1.day);
            this.lab1PeriodSpinner.setValue(groupInfo.lab1.period);
            if (!this.subjectInfo.labBiWeek) {
                this.lab2DayComboBox.setSelectedIndex(groupInfo.lab2.day);
                this.lab2PeriodSpinner.setValue(groupInfo.lab2.period);
            }
        }
    }

    public void setSecondLecturePlace(SecondLecturePossibility secondLecturePlace) {
        this.secondLecturePlace = secondLecturePlace;
    }

    public SecondLecturePossibility checkSecLecturePossibility() {
        if (!this.subjectInfo.secLecExists) {
            return SecondLecturePossibility.NO_SEC_LEC;
        } else {
            int mainLecPeriod = (int) this.lecturePeriodSpinner.getValue();
            if (this.lectureDayComboBox.getSelectedIndex() == this.secLectureDayComboBox.getSelectedIndex() && Math.abs(mainLecPeriod - (int) this.secLecturePeriodSpinner.getValue()) == 1) {
                return SecondLecturePossibility.MAIN_LECTURE;
            } else if (!this.subjectInfo.tutExists) {
                return SecondLecturePossibility.TUTORIAL;
            } else if (!this.subjectInfo.labExists) {
                return SecondLecturePossibility.LAB;
            } else {
                return SecondLecturePossibility.NOT_POSSIBLE;
            }
        }
    }

    public String getMeetingType(int day, int period) throws Exception {
        this.initGroupInfo();
        return this.groupInfo.getMeetingType(day, period);
    }

    public String generateString() throws Exception {
        this.initGroupInfo();
        //if (this.isConflicting()){
            //throw new Exception("There is a group that conflicts with itself in " + subject.getSubjectName());
        //}
        StringBuilder sb = new StringBuilder();
        String lecStrings = generateLectureString();
        switch (this.secondLecturePlace) {
            case NO_SEC_LEC:
            case MAIN_LECTURE:
                sb.append(lecStrings).append(generateTutString()).append(generateLabString());
                break;

            case TUTORIAL:
                sb.append(lecStrings).append(generateLabString());
                break;

            case LAB: {
                String[] lecAndLabStrings = lecStrings.split("<");
                sb.append(lecAndLabStrings[0]).append(generateTutString()).append(lecAndLabStrings[1]);
                break;
            }

            case NOT_POSSIBLE:
                throw new Exception("-1");
        }
        return sb.toString();
    }

    private String generateLectureString() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(this.lectureDayComboBox.getSelectedIndex()).append(" ");
        int mainLecPeriod = (int) this.lecturePeriodSpinner.getValue();

        switch (this.secondLecturePlace) {
            case NO_SEC_LEC: {
                sb.append((mainLecPeriod * 2) - 1).append(" ");
                sb.append(mainLecPeriod * 2).append("\r\n");
                break;
            }

            case MAIN_LECTURE: {
                if (mainLecPeriod < (int) this.secLecturePeriodSpinner.getValue()) {
                    sb.append((mainLecPeriod * 2) - 1).append(" ");
                    sb.append((mainLecPeriod * 2) + 1).append("\r\n");
                } else {
                    sb.append((mainLecPeriod * 2) - 2).append(" ");
                    sb.append(mainLecPeriod * 2).append("\r\n");
                }
                break;
            }

            case TUTORIAL: {
                sb.append((mainLecPeriod * 2) - 1).append(" ");
                sb.append(mainLecPeriod * 2).append("\r\n");
                sb.append(this.secLectureDayComboBox.getSelectedIndex()).append(" ");
                int secLecPeriod = (int) this.secLecturePeriodSpinner.getValue();
                if (this.lectureDayComboBox.getSelectedIndex() == this.secLectureDayComboBox.getSelectedIndex() && Math.abs(mainLecPeriod - (int) this.secLecturePeriodSpinner.getValue()) == 1) {
                    if (mainLecPeriod < secLecPeriod) {
                        sb.append((secLecPeriod * 2) - 1).append(" ");
                        sb.append((secLecPeriod * 2) - 1).append("\r\n");
                    } else {
                        sb.append(secLecPeriod * 2).append(" ");
                        sb.append(secLecPeriod * 2).append("\r\n");
                    }
                } else {
                    sb.append((secLecPeriod * 2) - 1).append(" ");
                    sb.append((secLecPeriod * 2) - 1).append("\r\n");
                }
                break;
            }

            case LAB: {
                sb.append((mainLecPeriod * 2) - 1).append(" ");
                sb.append(mainLecPeriod * 2).append("\r\n<");
                sb.append(this.secLectureDayComboBox.getSelectedIndex()).append(" ");
                int secLecPeriod = (int) this.secLecturePeriodSpinner.getValue();
                if (this.lectureDayComboBox.getSelectedIndex() == this.secLectureDayComboBox.getSelectedIndex() && Math.abs(mainLecPeriod - (int) this.secLecturePeriodSpinner.getValue()) == 1) {
                    if (mainLecPeriod < secLecPeriod) {
                        sb.append((secLecPeriod * 2) - 1).append(" ");
                        sb.append((secLecPeriod * 2) - 1).append("\r\n");
                    } else {
                        sb.append(secLecPeriod * 2).append(" ");
                        sb.append(secLecPeriod * 2).append("\r\n");
                    }
                } else {
                    sb.append((secLecPeriod * 2) - 1).append(" ");
                    sb.append((secLecPeriod * 2) - 1).append("\r\n");
                }
                break;
            }

            case NOT_POSSIBLE: {
                throw new Exception("-1");
            }
        }
        return sb.toString();
    }

    private String generateTutString() {
        StringBuilder sb = new StringBuilder();
        if (this.subjectInfo.tutExists) {
            if (this.subjectInfo.tutBiWeek) {
                sb.append(this.tut1DayComboBox.getSelectedIndex()).append(" ");
                int period = (int) this.tut1PeriodSpinner.getValue();
                sb.append((period * 2) - 1).append(" ");
                sb.append((period * 2) - 1).append("\r\n");
                sb.append(this.tut1DayComboBox.getSelectedIndex()).append(" ");
                sb.append(period * 2).append(" ");
                sb.append(period * 2).append("\r\n");
            } else {
                sb.append(this.tut1DayComboBox.getSelectedIndex()).append(" ");
                int period = (int) this.tut1PeriodSpinner.getValue();
                sb.append((period * 2) - 1).append(" ");
                sb.append(period * 2).append("\r\n");

                sb.append(this.tut2DayComboBox.getSelectedIndex()).append(" ");
                period = (int) this.tut2PeriodSpinner.getValue();
                sb.append((period * 2) - 1).append(" ");
                sb.append(period * 2).append("\r\n");
            }
        }
        return sb.toString();
    }

    private String generateLabString() {
        StringBuilder sb = new StringBuilder();
        if (this.subjectInfo.labExists) {
            if (this.subjectInfo.labBiWeek) {
                sb.append(this.lab1DayComboBox.getSelectedIndex()).append(" ");
                int period = (int) this.lab1PeriodSpinner.getValue();
                sb.append((period * 2) - 1).append(" ");
                sb.append((period * 2) - 1).append("\r\n");
                sb.append(this.lab1DayComboBox.getSelectedIndex()).append(" ");
                sb.append(period * 2).append(" ");
                sb.append(period * 2).append("\r\n");
            } else {
                sb.append(this.lab1DayComboBox.getSelectedIndex()).append(" ");
                int period = (int) this.lab1PeriodSpinner.getValue();
                sb.append((period * 2) - 1).append(" ");
                sb.append(period * 2).append("\r\n");

                sb.append(this.lab2DayComboBox.getSelectedIndex()).append(" ");
                period = (int) this.lab2PeriodSpinner.getValue();
                sb.append((period * 2) - 1).append(" ");
                sb.append(period * 2).append("\r\n");
            }
        }
        return sb.toString();
    }

    private void initGroupInfo() {
        this.groupInfo = new GroupInfo();
        groupInfo.lecture.day = this.lectureDayComboBox.getSelectedIndex();
        groupInfo.lecture.period = (int) this.lecturePeriodSpinner.getValue();
        if (this.subjectInfo.secLecExists) {
            groupInfo.secLecture.day = this.secLectureDayComboBox.getSelectedIndex();
            groupInfo.secLecture.period = (int) this.secLecturePeriodSpinner.getValue();
        }

        if (this.subjectInfo.tutExists) {
            groupInfo.tutorial1.day = this.tut1DayComboBox.getSelectedIndex();
            groupInfo.tutorial1.period = (int) this.tut1PeriodSpinner.getValue();
            if (!this.subjectInfo.tutBiWeek) {
                groupInfo.tutorial2.day = this.tut2DayComboBox.getSelectedIndex();
                groupInfo.tutorial2.period = (int) this.tut2PeriodSpinner.getValue();
            }
        }

        if (this.subjectInfo.labExists) {
            groupInfo.lab1.day = this.lab1DayComboBox.getSelectedIndex();
            groupInfo.lab1.period = (int) this.lab1PeriodSpinner.getValue();
            if (!this.subjectInfo.labBiWeek) {
                groupInfo.lab2.day = this.lab2DayComboBox.getSelectedIndex();
                groupInfo.lab2.period = (int) this.lab2PeriodSpinner.getValue();
            }
        }
    }

    private boolean isConflicting(){
        ArrayList<Time> times = new ArrayList<>();
        times.add(groupInfo.lecture);
        if (subjectInfo.secLecExists){
            times.add(groupInfo.secLecture);
        }
        if (subjectInfo.tutExists){
            times.add(groupInfo.tutorial1);
            if (!subjectInfo.tutBiWeek){
                times.add(groupInfo.tutorial2);
            }
        }
        if (subjectInfo.labExists){
            times.add(groupInfo.lab1);
            if (!subjectInfo.labBiWeek){
                times.add(groupInfo.lab2);
            }
        }
        return Time.checkConflict(times);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lectureLabel = new javax.swing.JLabel();
        lectureDayComboBox = new javax.swing.JComboBox<>();
        lecturePeriodSpinner = new javax.swing.JSpinner();
        lecturePeriodLabel = new javax.swing.JLabel();
        secLectureDayComboBox = new javax.swing.JComboBox<>();
        secLecturePeriodSpinner = new javax.swing.JSpinner();
        secLecturePeriodLabel = new javax.swing.JLabel();
        secLecLabel = new javax.swing.JLabel();
        tut1Label = new javax.swing.JLabel();
        tut1DayComboBox = new javax.swing.JComboBox<>();
        tut1PeriodLabel = new javax.swing.JLabel();
        tut1PeriodSpinner = new javax.swing.JSpinner();
        tut2DayComboBox = new javax.swing.JComboBox<>();
        tut2PeriodLabel = new javax.swing.JLabel();
        tut2PeriodSpinner = new javax.swing.JSpinner();
        tut2Label = new javax.swing.JLabel();
        lab1DayComboBox = new javax.swing.JComboBox<>();
        lab1PeriodLabel = new javax.swing.JLabel();
        lab1PeriodSpinner = new javax.swing.JSpinner();
        lab2DayComboBox = new javax.swing.JComboBox<>();
        lab2PeriodLabel = new javax.swing.JLabel();
        lab2PeriodSpinner = new javax.swing.JSpinner();
        lab2Label = new javax.swing.JLabel();
        lab1Label = new javax.swing.JLabel();
        deleteGroupButton = new javax.swing.JButton();

        lectureLabel.setText("Main Lecture:");

        lectureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday" }));

        lecturePeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));

        lecturePeriodLabel.setText("Period");

        secLectureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday" }));

        secLecturePeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));

        secLecturePeriodLabel.setText("Period");

        secLecLabel.setText("Secondary Lecture:");

        tut1Label.setText("jLabel1");

        tut1DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday" }));

        tut1PeriodLabel.setText("Period");

        tut1PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));

        tut2DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday" }));

        tut2PeriodLabel.setText("Period");

        tut2PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));

        tut2Label.setText("Tutorial 2");

        lab1DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday" }));

        lab1PeriodLabel.setText("Period");

        lab1PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));

        lab2DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday" }));

        lab2PeriodLabel.setText("Period");

        lab2PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));

        lab2Label.setText("Lab 2");

        lab1Label.setText("jLabel1");

        deleteGroupButton.setBackground(new java.awt.Color(255, 0, 0));
        deleteGroupButton.setText("Delete Group");
        deleteGroupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteGroupButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tut2Label)
                    .addComponent(tut1Label)
                    .addComponent(secLecLabel)
                    .addComponent(lectureLabel)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(lab2Label)
                            .addGap(146, 146, 146))
                        .addComponent(lab1Label, javax.swing.GroupLayout.Alignment.LEADING)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tut2DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(tut1DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(secLectureDayComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lectureDayComboBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lecturePeriodLabel)
                                .addGap(18, 18, 18)
                                .addComponent(lecturePeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(secLecturePeriodLabel)
                                .addGap(18, 18, 18)
                                .addComponent(secLecturePeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tut1PeriodLabel)
                                .addGap(18, 18, 18)
                                .addComponent(tut1PeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(tut2PeriodLabel)
                                .addGap(18, 18, 18)
                                .addComponent(tut2PeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lab2DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lab1DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lab1PeriodLabel)
                                .addGap(18, 18, 18)
                                .addComponent(lab1PeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lab2PeriodLabel)
                                .addGap(18, 18, 18)
                                .addComponent(lab2PeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(50, 50, 50))
            .addGroup(layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(deleteGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lectureLabel)
                    .addComponent(lectureDayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lecturePeriodLabel)
                    .addComponent(lecturePeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(secLectureDayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secLecturePeriodLabel)
                    .addComponent(secLecturePeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(secLecLabel))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tut1Label)
                    .addComponent(tut1DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tut1PeriodLabel)
                    .addComponent(tut1PeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tut2Label)
                    .addComponent(tut2DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tut2PeriodLabel)
                    .addComponent(tut2PeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab1Label)
                    .addComponent(lab1DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lab1PeriodLabel)
                    .addComponent(lab1PeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lab2Label)
                    .addComponent(lab2DayComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lab2PeriodLabel)
                    .addComponent(lab2PeriodSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(deleteGroupButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void deleteGroupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteGroupButtonActionPerformed
        this.subject.deleteGroup(this);
    }//GEN-LAST:event_deleteGroupButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteGroupButton;
    private javax.swing.JComboBox<String> lab1DayComboBox;
    private javax.swing.JLabel lab1Label;
    private javax.swing.JLabel lab1PeriodLabel;
    private javax.swing.JSpinner lab1PeriodSpinner;
    private javax.swing.JComboBox<String> lab2DayComboBox;
    private javax.swing.JLabel lab2Label;
    private javax.swing.JLabel lab2PeriodLabel;
    private javax.swing.JSpinner lab2PeriodSpinner;
    private javax.swing.JComboBox<String> lectureDayComboBox;
    private javax.swing.JLabel lectureLabel;
    private javax.swing.JLabel lecturePeriodLabel;
    private javax.swing.JSpinner lecturePeriodSpinner;
    private javax.swing.JLabel secLecLabel;
    private javax.swing.JComboBox<String> secLectureDayComboBox;
    private javax.swing.JLabel secLecturePeriodLabel;
    private javax.swing.JSpinner secLecturePeriodSpinner;
    private javax.swing.JComboBox<String> tut1DayComboBox;
    private javax.swing.JLabel tut1Label;
    private javax.swing.JLabel tut1PeriodLabel;
    private javax.swing.JSpinner tut1PeriodSpinner;
    private javax.swing.JComboBox<String> tut2DayComboBox;
    private javax.swing.JLabel tut2Label;
    private javax.swing.JLabel tut2PeriodLabel;
    private javax.swing.JSpinner tut2PeriodSpinner;
    // End of variables declaration//GEN-END:variables
}
