package Core.Generator;

import java.util.ArrayList;

/**
 * Created by louay on 2/1/2017.
 */
public class Optimizer {
    private final ArrayList<String[][]> optimizedSchedules;
    private int maxOffDays;
    private int minGaps;

    public Optimizer() {
        this.optimizedSchedules = new ArrayList<>();
        this.maxOffDays = 0;
        this.minGaps = Integer.MAX_VALUE;
    }

    public void insertSchedule(String[][] schedule) {
        int days = this.getDaysOff(schedule);
        if (days > this.maxOffDays) {
            this.optimizedSchedules.clear();
            this.optimizedSchedules.add(schedule);
            this.maxOffDays = days;
            this.minGaps = this.getGaps(schedule);
        } else if (days == this.maxOffDays) {
            int gaps = this.getGaps(schedule);
            if (gaps < minGaps) {
                this.optimizedSchedules.clear();
                this.optimizedSchedules.add(schedule);
                this.minGaps = gaps;
            } else if (gaps == minGaps) {
                this.optimizedSchedules.add(schedule);
            }
        }
    }

    public ArrayList<String[][]> getOptimizedSchedules() {
        return this.optimizedSchedules;
    }

    private int getDaysOff(String[][] schedule) {
        int days = 0;
        for (int i = 0; i < 7; i++) {
            boolean off = true;
            for (int j = 0; j < 14; j++) {
                if (!schedule[j][i].equals("___")) {
                    off = false;
                    break;
                }
            }
            if (off) {
                days++;
            }
        }
        return days;
    }

    private int getGaps(String[][] schedule) {
        int gaps = 0;
        for (int i = 0; i < 7; i++) {
            int start = 0, end = 13;
            boolean found = false, foundStart = false, foundEnd = false;
            while (!found && (start != (end + 1))) {
                if (schedule[start][i].equals("___")) {
                    start++;
                } else {
                    foundStart = true;
                }
                if (schedule[end][i].equals("___")) {
                    end--;
                } else {
                    foundEnd = true;
                }
                found = foundStart && foundEnd;
            }
            if (found) {
                for (int j = start; j <= end; j++) {
                    if (schedule[j][i].equals("___")) {
                        gaps++;
                    }
                }
            }
        }
        return gaps;
    }
}
