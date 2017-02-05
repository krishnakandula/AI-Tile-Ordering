package controllers;

import models.State;

/**
 * Created by Krishna Chaitanya Kandula on 2/3/17.
 */
public interface StateController {
    void addState(State s);
    State getState();
    boolean isEmpty();
}
