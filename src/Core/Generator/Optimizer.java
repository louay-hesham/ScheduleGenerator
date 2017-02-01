package Core.Generator;

import java.util.ArrayList;

/**
 * Created by louay on 2/1/2017.
 */
public class Optimizer {
    private final ArrayList<String[][]> schedules;
    private int maxOffDays;
    private int minOffDays;

    public Optimizer(){
        this.schedules = new ArrayList<>();
        this.maxOffDays = 0;
        this.minOffDays = Integer.MAX_VALUE;
    }

    public void insertSchedule(String[][] schedule){
        int days = this.getDaysOff(schedule);
        if (days > this.maxOffDays){
            this.schedules.clear();
            this.schedules.add(schedule);
            this.maxOffDays = days;
        } else if (days == this.maxOffDays){
            this.schedules.add(schedule);
        }
    }

    public ArrayList<String[][]> getSchedules(){

        return this.schedules;
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
}
