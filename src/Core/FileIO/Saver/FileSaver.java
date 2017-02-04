package Core.FileIO.Saver;

import GUI.SubjectsPanel.SubjectPanel;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by louay on 2/2/2017.
 */
public class FileSaver {

    private final StringBuilder sb;
    private final String type;

    public FileSaver(ArrayList<SubjectPanel> subjects, String type) {
        this.type = type;
        this.sb = new StringBuilder();
        this.sb.append(type + "\r\n");
        this.sb.append(subjects.size() + "\r\n\r\n");
        for (SubjectPanel s : subjects) {
            this.sb.append(s.toString());
        }
    }

    public void saveFile() {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                PrintWriter writer = new PrintWriter(file.getPath() + "_" + this.type + ".SGEN");
                writer.write(this.sb.toString());
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
