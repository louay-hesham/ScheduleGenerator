package Core.FileIO.Loader;

import Core.Subject.Subject;
import GUI.MainGUI;
import GUI.SubjectsPanel.SubjectPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by louay on 2/3/2017.
 */
public abstract class FileParser {
    protected final List<String> lines;
    protected final ArrayList<SubjectPanel> panels;
    protected final MainGUI gui;
    protected int i = 1;
    protected int n;

    public FileParser(List<String> lines, MainGUI gui) throws Exception {
        this.lines = lines;
        this.panels = new ArrayList<>();
        this.gui = gui;
        this.startDecoding();
    }

    public ArrayList<SubjectPanel> getPanels() {
        return this.panels;
    }

    protected abstract void startDecoding() throws Exception;

    protected abstract Subject decodeSubject() throws Exception;

    protected String getNextLine() {
        String line = this.lines.get(this.i);
        this.i++;
        return line;
    }
}
