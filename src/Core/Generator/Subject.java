package Core.Generator;

import java.util.ArrayList;

/**
 * Created by louay on 1/30/2017.
 */
public abstract class Subject {
    protected final ArrayList<Time> timesInPermutation = new ArrayList<>();

    public abstract ArrayList<Time> getTimesInPermutation();

    public abstract void nextPermutation() throws Exception;




}