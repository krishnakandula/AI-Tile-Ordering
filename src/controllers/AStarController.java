package controllers;

import models.State;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Krishna Chaitanya Kandula on 2/5/2017.
 */
public class AStarController implements StateController{
    private PriorityQueue<State> dataStruc;

    public AStarController(){
        dataStruc = new PriorityQueue<>(new StateComparator());
//        dataStruc = new PriorityQueue<>(Collections.reverseOrder(new StateComparator()));
    }

    @Override
    public void addState(State s) {
        dataStruc.offer(s);
    }

    @Override
    public void addStates(List<State> states) {
        for(State s : states)
            addState(s);
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
            int o1Cost = o1.gCost + o1.hCost;
            int o2Cost = o2.gCost + o2.hCost;

            return o1Cost - o2Cost;
        }
    }
}
