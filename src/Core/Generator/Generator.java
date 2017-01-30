package Core.Generator;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by louay on 1/30/2017.
 */
public class Generator {
    private final Stack<Subject> stack = new Stack<>();
    private final ArrayList<Subject> subject;

    public Generator(ArrayList<Subject> subject) {
        this.subject = subject;
    }
}
