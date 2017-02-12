package controllers;

import models.State;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Krishna Chaitanya Kandula on 2/4/17.
 */
public class BFSController implements StateController {

    private Queue<State> dataStruc;

    public BFSController(){
        dataStruc = new LinkedList<>();
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
}
