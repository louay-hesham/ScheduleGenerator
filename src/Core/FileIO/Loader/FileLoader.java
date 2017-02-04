package Core.FileIO.Loader;

import GUI.MainGUI;
import GUI.SubjectsPanel.SubjectPanel;

import javax.swing.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by louay on 2/2/2017.
 */
public class FileLoader {
    private final File file;
    private final FileParser parser;

    public FileLoader(MainGUI gui) throws Exception {
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            this.file = fileChooser.getSelectedFile();
            List<String> lines = Files.readAllLines(Paths.get(this.file.getPath()));
            for (int i = 0; i < lines.size(); i++) {
                if (lines.get(i).equals("")) {
                    lines.remove(i);
                    i--;
                }
            }
            switch (lines.get(0)) {
                case "Normal":
                    gui.convertToNormalMode();
                    this.parser = new FileParserNormal(lines, gui);
                    break;
                case "7eby":
                    gui.convertTo7ebyMode();
                    this.parser = new FileParserChaos(lines, gui);
                    break;
                default:
                    throw new Exception("Invalid file format");
            }
        } else {
            throw new Exception("Invalid file");
        }
    }

    public ArrayList<SubjectPanel> getPanels() {
        return this.parser.getPanels();
    }
}
