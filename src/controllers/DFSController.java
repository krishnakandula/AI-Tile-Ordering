package controllers;

import models.State;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by Krishna Chaitanya Kandula on 2/5/17.
 */
public class DFSController implements StateController{
    Stack<State> dataStruc;

    public DFSController(){
        dataStruc = new Stack<>();
    }

    @Override
    public void addState(State s) {
        dataStruc.push(s);
    }

    @Override
    public void addStates(List<State> states) {
        Collections.reverse(states);
        for(State s : states)
            addState(s);
    }

    @Override
    public State getState() {
        return dataStruc.pop();
    }

    @Override
    public boolean isEmpty() {
        return dataStruc.isEmpty();
    }
}
