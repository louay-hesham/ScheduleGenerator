package Core.Generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by louay on 1/30/2017.
 */
public class Generator {
    private final Stack<Subject> stack;
    private final ArrayList<Subject> subjects;
    private int iSub, nSub;
    //private final Stack<String[][]> generationScheduleStack;
    private final String[][] currentSchedule;
    private final String emptyPeriod;
    private final Optimizer optimizer;

    public Generator(ArrayList<Subject> subject) {
        this.subjects = subject;
        this.stack = new Stack<>();
        //this.schedules = new ArrayList<>();
        this.nSub = this.subjects.size();
        this.iSub = 0;
        //this.generationScheduleStack = new Stack<>();
        this.currentSchedule = this.getNewSchedule();
        this.emptyPeriod = new String("___");
        this.optimizer = new Optimizer();
    }

    public ArrayList<String[][]> getSchedules(){
        this.generate();
        return this.optimizer.getSchedules();
    }

    private void generate(){
        while (true){
            Subject s = this.subjects.get(iSub);
            try {
                s.nextPermutation();
                boolean pushed = this.push(s);
                if (pushed){
                    if (iSub == nSub){
                        this.optimizer.insertSchedule(this.deepCopy(this.currentSchedule));
                        this.pop();
                        continue;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            } catch (Exception e) {
                try {
                    this.pop();
                    continue;
                } catch (Exception e1) {
                    return;
                }
            }
        }
    }

    private boolean push(Subject s){
        boolean success = true;
        boolean[][] visited = new boolean[14][7];
        for (Time t : s.getTimesInPermutation()){
            switch (t.getType()){
                case LECTURE:
                case SEC_LECTURE:
                case LAB_FULL:
                case TUT_FULL:
                {
                    for (int i = t.from; i <= t.to; i++){
                        success = success && (this.currentSchedule[i][t.day].equals(this.emptyPeriod)) && (!visited[i][t.day]);
                        visited[i][t.day] = true;
                    }
                    break;
                }
                case LAB_HALF:
                case TUT_HALF:
                {
                    success = success && ((this.currentSchedule[t.to][t.day].equals(this.emptyPeriod) && (!visited[t.to][t.day])) || (this.currentSchedule[t.to - 1][t.day].equals(this.emptyPeriod) && (!visited[t.to - 1][t.day])));
                    if (this.currentSchedule[t.to][t.day].equals(this.emptyPeriod)){
                        visited[t.to][t.day] = true;
                    } else if (this.currentSchedule[t.to - 1][t.day].equals(this.emptyPeriod)){
                        visited[t.to - 1][t.day] = true;
                    }
                    break;
                }
            }
        }
        if (success){
            //this.generationScheduleStack.push(this.deepCopy(this.currentSchedule));
            this.stack.push(this.subjects.get(iSub));
            for (Time t : s.getTimesInPermutation()){
                switch (t.getType()){
                    case LECTURE:
                    case SEC_LECTURE:
                    case LAB_FULL:
                    case TUT_FULL:
                    {
                        for (int i = t.from; i <= t.to; i++){
                            this.currentSchedule[i][t.day] = (s.getSubjectName() + "!" + t.getTypeString());
                        }
                        break;
                    }
                    case LAB_HALF:
                    case TUT_HALF:
                    {
                        if (this.currentSchedule[t.to][t.day].equals(this.emptyPeriod)){
                            this.currentSchedule[t.to][t.day] = (s.getSubjectName() + "!" + t.getTypeString());
                        } else {
                            this.currentSchedule[t.to - 1][t.day] = (s.getSubjectName() + "!" + t.getTypeString());
                        }
                        break;
                    }
                }
            }
            iSub++;
        }
        return success;
    }

    private void pop() throws Exception {
        if (this.stack.isEmpty()){
            throw new Exception("Done");
        }
        Subject s = this.stack.pop();
        //this.currentSchedule = this.generationScheduleStack.pop();
        for (Time t : s.getTimesInPermutation()){
            switch (t.getType()){
                case LECTURE:
                case SEC_LECTURE:
                case LAB_FULL:
                case TUT_FULL:
                {
                    for (int i = t.from; i <= t.to; i++){
                        this.currentSchedule[i][t.day] = "___";
                    }
                    break;
                }
                case LAB_HALF:
                case TUT_HALF:
                {
                    String toSubjectName = this.currentSchedule[t.to][t.day].split("!")[0];
                    if (s.getSubjectName().equals(toSubjectName)){
                        this.currentSchedule[t.to][t.day] = "___";
                    } else {
                        this.currentSchedule[t.to - 1][t.day] = "___";
                    }
                    break;
                }
            }
        }
        iSub--;
    }

    private String[][] getNewSchedule(){
        String[][] newSchedule = new String[14][];
        for (int i=0; i<14; i++){
            newSchedule[i] = new String[7];
            for (int j=0; j<7; j++){
                newSchedule[i][j] = "___";
            }
        }
        return newSchedule;
    }

    private String[][] deepCopy(String[][] original) {
        if (original == null) {
            return null;
        }

        final String[][] result = new String[original.length][];
        for (int i = 0; i < original.length; i++) {
            result[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return result;
    }

}
