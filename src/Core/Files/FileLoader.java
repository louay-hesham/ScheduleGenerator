/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.Files;

import Core.InfoHelpers.GroupInfo;
import GUI.MainGUI;
import GUI.NormalMode.SubjectPanel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author louay
 */
public class FileLoader {

    private File file;
    private Scanner scanner;

    public FileLoader(File file) {
        this.file = file;
        try {
            this.scanner = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadFile(MainGUI gui) {
        int numberOfSubjects = scanner.nextInt();

        for (int i = 0; i < numberOfSubjects; i++) {
            String subjectName = null;
            do {
                subjectName = scanner.nextLine().replaceAll("_", " ");
            }while(subjectName == null || subjectName.equals(""));
            int numberOfGroups = scanner.nextInt();
            int tut = scanner.nextInt();
            int lab = scanner.nextInt();

            boolean secLecExists = (tut == 1) || (lab == 1);
            boolean tutExists = (tut == 2);
            boolean labExists = (lab == 2);
            boolean tutBiWeek = false, labBiWeek = false;
            ArrayList<GroupInfo> groups = new ArrayList<>();

            for (int k = 0; k < numberOfGroups; k++) {
                GroupInfo group = new GroupInfo();
                int day, from, to;
                day = scanner.nextInt();
                from = scanner.nextInt();
                to = scanner.nextInt();
                group.setLecture(day, to / 2);

                if (to - from > 1) {
                    secLecExists = true;
                    if (from % 2 == 0) {
                        group.setSecLecture(day, from / 2);
                    } else if (to % 2 == 1) {
                        group.setSecLecture(day, (to / 2) + 1);
                    }
                }

                if (tut == 1) {
                    tutBiWeek = false;
                    day = scanner.nextInt();
                    from = scanner.nextInt();
                    to = scanner.nextInt();
                    if (from % 2 == 0) {
                        group.setSecLecture(day, from / 2);
                    } else if (from % 2 == 1) {
                        group.setSecLecture(day, (from / 2) + 1);
                    }
                } else if (tut == 2) {
                    day = scanner.nextInt();
                    from = scanner.nextInt();
                    to = scanner.nextInt();
                    if (from == to) {
                        if (from % 2 == 0) {
                            group.setTutorial1(day, from / 2);
                        } else if (from % 2 == 1) {
                            group.setTutorial1(day, (from / 2) + 1);
                        }
                        tutBiWeek = true;
                        day = scanner.nextInt();
                        from = scanner.nextInt();
                        to = scanner.nextInt();
                    } else {
                        group.setTutorial1(day, to / 2);
                        day = scanner.nextInt();
                        from = scanner.nextInt();
                        to = scanner.nextInt();
                        group.setTutorial2(day, to / 2);
                        tutBiWeek = false;
                    }

                }

                if (lab == 1) {
                    labBiWeek = false;
                    day = scanner.nextInt();
                    from = scanner.nextInt();
                    to = scanner.nextInt();
                    if (from % 2 == 0) {
                        group.setSecLecture(day, from / 2);
                    } else if (from % 2 == 1) {
                        group.setSecLecture(day, (from / 2) + 1);
                    }
                } else if (lab == 2) {
                    day = scanner.nextInt();
                    from = scanner.nextInt();
                    to = scanner.nextInt();
                    if (from == to) {
                        if (from % 2 == 0) {
                            group.setLab1(day, from / 2);
                        } else if (from % 2 == 1) {
                            group.setLab1(day, (from / 2) + 1);
                        }
                        labBiWeek = true;
                        day = scanner.nextInt();
                        from = scanner.nextInt();
                        to = scanner.nextInt();
                    } else {
                        group.setLab1(day, to / 2);
                        day = scanner.nextInt();
                        from = scanner.nextInt();
                        to = scanner.nextInt();
                        group.setLab2(day, to / 2);
                        labBiWeek = false;
                    }
                }
                groups.add(group);

            }
            SubjectPanel subject = gui.addSubject(subjectName, secLecExists, tutExists, tutBiWeek, labExists, labBiWeek);
            subject.setGroups(groups);
        }
    }
}
