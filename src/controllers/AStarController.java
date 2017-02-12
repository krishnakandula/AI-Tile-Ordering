package controllers;

import models.State;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Krishna Chaitanya Kandula on 2/5/2017.
 */
public class AStarController implements StateController{
    private PriorityQueue<State> dataStruc;
    private int priority;
    public AStarController(){
        dataStruc = new PriorityQueue<>(new StateComparator());
        priority = 0;
    }

    @Override
    public void addState(State s) {
        dataStruc.offer(s);
    }

    @Override
    public void addStates(List<State> states) {
        for(State s : states) {
            s.priority = priority++;
            addState(s);
        }
    }

    @Override
    public State getState() {
        return dataStruc.poll();
    }

    @Override
    public boolean isEmpty() {
        return dataStruc.isEmpty();
    }

    class StateComparator implements Comparator<State>{
        @Override
        public int compare(State o1, State o2) {
            int o1Cost = o1.totalCost + o1.hCost;
            int o2Cost = o2.totalCost + o2.hCost;

            if(o1Cost != o2Cost)
                return o1Cost - o2Cost;
            return o1.priority - o2.priority;
        }
    }
}
