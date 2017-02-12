package controllers;

import models.State;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Krishna Chaitanya Kandula on 2/5/2017.
 */
public class UCSController implements StateController{
    PriorityQueue<State> dataStruc;
    private int priority;

    public UCSController(){
        dataStruc = new PriorityQueue<>(new StateComparator());
        priority = 0;
    }

    @Override
    public void addState(State s) {
        s.priority = priority++;
        dataStruc.offer(s);
    }

    @Override
    public void addStates(List<State> states) {
        for(State s : states) {
            addState(s);
        }
    }

    @Override
    public State getState() {
        State current = dataStruc.poll();
        return current;
    }

    @Override
    public boolean isEmpty() {
        return dataStruc.isEmpty();
    }

    class StateComparator implements Comparator<State>{
        @Override
        public int compare(State o1, State o2) {
            if(o1.totalCost != o2.totalCost)
                return o1.totalCost - o2.totalCost;
            //If total gCost is the same, use priority to determine position in queue
            return o1.priority - o2.priority;
        }
    }
}


