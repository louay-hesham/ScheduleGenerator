/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Core.NormalGenerator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
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
            Runtime rt = Runtime.getRuntime();
            URL url = Generator.class.getResource("Schedule_Creator.exe");
            Process p = rt.exec(url.getPath());
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
        }
        return p.exitValue();
    }
}
