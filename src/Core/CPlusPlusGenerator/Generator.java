/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.CPlusPlusGenerator;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author louay
 */
public class Generator {

    public static ArrayList<String[][]> getGeneratedSchedules(String input) {
        String output = runExe(input);
        ArrayList<String[][]> schedules = new ArrayList<>();
        String[] timetables = output.split("#");
        for (String timetable : timetables) {
            String[] periods = timetable.split("@");
            String[][] t = new String[14][];
            int i = 0;
            for (String period : periods) {
                String[] days = period.split("!");
                int j = 0;
                t[i] = new String[6];
                for (String day : days) {
                    t[i][j] = day;
                    j++;
                }
                i++;
            }
            schedules.add(t);
        }
        return schedules;
    }

    private static String runExe(String input) {
        try {
            //copying exe file from within the JAR to outside.
            InputStream is = Generator.class.getResource("Schedule_Creator.exe").openStream();
            FileOutputStream os = new FileOutputStream("Schedule_Creator.exe");
            byte[] b = new byte[2048];
            int length;
            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }
            is.close();
            os.close();
            //running exe
            Runtime rt = Runtime.getRuntime();
            String exeLocation = new File(Core.Generator.Generator.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent() + File.separator + "Schedule_Creator.exe";
            JOptionPane.showMessageDialog(null, exeLocation, "Error", JOptionPane.ERROR_MESSAGE);
            Process p = rt.exec(exeLocation);
            InputStream in = p.getInputStream();
            OutputStream out = p.getOutputStream();
            //InputStream err = p.getErrorStream();

            System.out.println(getStreamOutput(in));
            writeToStream(out, input);
            while (true) {
                if (getStreamOutput(in).endsWith("*")) {
                    writeToStream(out, "2\n");
//                    int exit = waitForExit(p);
//                    System.out.println("Exit code is: " + exit);
                    String str = getStreamOutput(in);
                    System.out.println(str);
                    return str;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        }
    }

    private static String getStreamOutput(InputStream in) {
        try {
            while (in.available() == 0) {
                //
            }
            byte[] b;
            b = new byte[in.available()];
            in.read(b);
            StringBuilder sb = new StringBuilder();
            for (byte c : b) {
                sb.append(Character.toChars(c));
            }
            return sb.toString();

        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
            return "Error";
        }
    }

    private static void writeToStream(OutputStream out, String str) {
        try {
            out.write(str.getBytes());
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int waitForExit(Process p) {
        while (p.isAlive()) {
            //
        }
        return p.exitValue();
    }
}
