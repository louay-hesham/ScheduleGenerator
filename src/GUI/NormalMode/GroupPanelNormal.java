package GUI.NormalMode;

import Core.InfoHelpers.GroupInfo;
import Core.InfoHelpers.SubjectInfo;

import javax.swing.*;

/*
 * Created by louay on 10/28/2016.
 */
public class GroupPanelNormal {

    private final SubjectInfo subjectInfo;
    private SecondLecturePossibility secondLecturePlace;
    private GroupInfo groupInfo = null;
    private final SubjectPanelNormal subject;
    private boolean useActionListener = true;

    enum SecondLecturePossibility {
        NO_SEC_LEC, MAIN_LECTURE, TUTORIAL, LAB, NOT_POSSIBLE
    }

    GroupInfo getGroupInfo() {
        return groupInfo;
    }

    private void deleteGroupButtonActionPerformed() {
        this.subject.deleteGroup(this);
    }

    /**
     * Creates new form GroupPanel
     *
     * @param subject     /
     * @param subjectInfo /
     */
    GroupPanelNormal(SubjectPanelNormal subject, SubjectInfo subjectInfo, boolean addToChaos) {
        this.initComponents();
        this.subject = subject;
        this.secondLecturePlace = SecondLecturePossibility.NO_SEC_LEC;
        this.subjectInfo = subjectInfo;

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
        this.initGroupInfo();
        if (addToChaos) {
            this.subject.getChaosVersion().addGroup(this.groupInfo);
        }
    }

    private void changeGroupPropertiesUserInteracted() {
        if (this.useActionListener) {
            this.initGroupInfo();
            this.subject.resetChaosPanel();
        }
    }

    private void initComponents() {
        deleteGroupButton.addActionListener(e -> deleteGroupButtonActionPerformed());
        lectureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lectureDayComboBox.addActionListener(e -> changeGroupPropertiesUserInteracted());
        secLectureDayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        secLectureDayComboBox.addActionListener(e -> changeGroupPropertiesUserInteracted());
        tut1DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        tut1DayComboBox.addActionListener(e -> changeGroupPropertiesUserInteracted());
        tut2DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        tut2DayComboBox.addActionListener(e -> changeGroupPropertiesUserInteracted());
        lab1DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lab1DayComboBox.addActionListener(e -> changeGroupPropertiesUserInteracted());
        lab2DayComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday"}));
        lab2DayComboBox.addActionListener(e -> changeGroupPropertiesUserInteracted());
        lecturePeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        lecturePeriodSpinner.addChangeListener(evt -> changeGroupPropertiesUserInteracted());
        secLecturePeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        secLecturePeriodSpinner.addChangeListener(evt -> changeGroupPropertiesUserInteracted());
        tut1PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        tut1PeriodSpinner.addChangeListener(evt -> changeGroupPropertiesUserInteracted());
        tut2PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        tut2PeriodSpinner.addChangeListener(evt -> changeGroupPropertiesUserInteracted());
        lab1PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        lab1PeriodSpinner.addChangeListener(evt -> changeGroupPropertiesUserInteracted());
        lab2PeriodSpinner.setModel(new javax.swing.SpinnerNumberModel(1, 1, 7, 1));
        lab2PeriodSpinner.addChangeListener(evt -> changeGroupPropertiesUserInteracted());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    void setGroupInfo(GroupInfo groupInfo) {
        this.useActionListener = false;
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
        this.useActionListener = true;
    }

    void setSecondLecturePlace(SecondLecturePossibility secondLecturePlace) {
        this.secondLecturePlace = secondLecturePlace;
    }

    SecondLecturePossibility checkSecLecturePossibility() {
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

    String getMeetingType(int day, int period) throws Exception {
        this.initGroupInfo();
        return this.groupInfo.getMeetingType(day, period);
    }

    String generateString() throws Exception {
        this.initGroupInfo();
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

    private JComboBox<String> lectureDayComboBox;
    private JSpinner lecturePeriodSpinner;
    private JButton deleteGroupButton;
    private JLabel lectureLabel;
    private JLabel secLecLabel;
    private JLabel tut1Label;
    private JLabel tut2Label;
    private JLabel lab1Label;
    private JLabel lab2Label;
    private JComboBox<String> secLectureDayComboBox;
    private JComboBox<String> tut1DayComboBox;
    private JComboBox<String> tut2DayComboBox;
    private JComboBox<String> lab1DayComboBox;
    private JComboBox<String> lab2DayComboBox;
    private JLabel lecturePeriodLabel;
    private JLabel secLecturePeriodLabel;
    private JLabel tut1PeriodLabel;
    private JLabel tut2PeriodLabel;
    private JLabel lab1PeriodLabel;
    private JLabel lab2PeriodLabel;
    private JSpinner secLecturePeriodSpinner;
    private JSpinner tut1PeriodSpinner;
    private JSpinner tut2PeriodSpinner;
    private JSpinner lab1PeriodSpinner;
    private JSpinner lab2PeriodSpinner;
    private JPanel mainPanel;
}
