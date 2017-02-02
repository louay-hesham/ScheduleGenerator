package Core.FileIO.Saver;

import GUI.SubjectPanel;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by louay on 2/2/2017.
 */
public class FileSaver {

    protected final StringBuilder sb;

    public FileSaver(ArrayList<SubjectPanel> subjects){
        this.sb = new StringBuilder();
        this.sb.append("Normal\r\n");
        this.sb.append(subjects.size() + "\r\n");
        for (SubjectPanel s : subjects){
            this.sb.append(s.toString());
        }
    }

    public void saveFile(){
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.write(this.sb.toString());
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
