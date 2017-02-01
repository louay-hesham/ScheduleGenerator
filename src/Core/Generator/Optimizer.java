package Core.Generator;

import java.util.ArrayList;

/**
 * Created by louay on 2/1/2017.
 */
public class Optimizer {
    private final ArrayList<String[][]> partiallyOptimizedSchedules;
    private final ArrayList<String[][]> fullyOptimizedSchedules;
    private int maxOffDays;
    private int minGaps;

    public Optimizer(){
        this.partiallyOptimizedSchedules = new ArrayList<>();
        this.maxOffDays = 0;
        this.minGaps = Integer.MAX_VALUE;
        this.fullyOptimizedSchedules = new ArrayList<>();
    }

    public void insertSchedule(String[][] schedule){
        int days = this.getDaysOff(schedule);
        if (days > this.maxOffDays){
            this.partiallyOptimizedSchedules.clear();
            this.partiallyOptimizedSchedules.add(schedule);
            this.maxOffDays = days;
        } else if (days == this.maxOffDays){
            this.partiallyOptimizedSchedules.add(schedule);
        }
    }

    public ArrayList<String[][]> getOptimizedSchedules(){
        for (String[][] schedule : this.partiallyOptimizedSchedules){
            int gaps = this.getGaps(schedule);
            if (gaps < minGaps){
                this.fullyOptimizedSchedules.clear();
                this.fullyOptimizedSchedules.add(schedule);
                this.minGaps = gaps;
            } else if (gaps == minGaps){
                this.fullyOptimizedSchedules.add(schedule);
            }
        }
        return this.fullyOptimizedSchedules;
    }

    private int getDaysOff(String[][] schedule){
        int days = 0;
        for (int i = 0; i < 7; i++){
            boolean off = true;
            for (int j = 0; j < 14; j++){
                if (!schedule[j][i].equals("___")){
                    off = false;
                    break;
                }
            }
            if (off){
                days++;
            }
        }
        return days;
    }

    private int getGaps(String[][] schedule){
        int gaps = 0;
        for (int i = 0; i < 7; i++){
            int start = 0, end = 13;
            boolean found = false, foundStart = false, foundEnd = false;
            while (!found && (start != (end + 1))){
                if (schedule[start][i].equals("___")){
                    start++;
                } else {
                    foundStart = true;
                }
                if (schedule[end][i].equals("___")){
                    end--;
                } else {
                    foundEnd = true;
                }
                found = foundStart && foundEnd;
            }
            if (found){
                for (int j = start; j <= end; j++){
                    if (schedule[j][i].equals("___")){
                        gaps++;
                    }
                }
            }
        }
        return gaps;
    }
}
