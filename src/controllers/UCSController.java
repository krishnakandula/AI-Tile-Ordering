package controllers;

import models.State;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by Krishna Chaitanya Kandula on 2/5/2017.
 */
public class UCSController implements StateController{
    PriorityQueue<State> dataStruc;

    public UCSController(){
        dataStruc = new PriorityQueue<>(new StateComparator());
    }

    @Override
    public void addState(State s) {
        dataStruc.offer(s);
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
            return o1.gCost - o2.gCost;
        }
    }
}


