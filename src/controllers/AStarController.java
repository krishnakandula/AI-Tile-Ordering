package controllers;

import models.State;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Krishna Chaitanya Kandula on 2/5/2017.
 */
public class AStarController {
    private PriorityQueue<State> dataStruc;

    public AStarController(){
        dataStruc = new PriorityQueue<>();
    }

    class StateComparator implements Comparator<State>{
        @Override
        public int compare(State o1, State o2) {
            return 0;
        }
    }
}
