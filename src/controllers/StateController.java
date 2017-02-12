package controllers;

import models.State;

import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 2/3/17.
 */
public interface StateController {
    void addState(State s);
    void addStates(List<State> states);
    State getState();
    boolean isEmpty();
}
