package controllers;

import models.State;

import java.util.LinkedList;
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
    public State getState() {
        return dataStruc.poll();
    }
}
