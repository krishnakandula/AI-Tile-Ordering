package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 2/3/17.
 */
public class State {

    public List<Tile> tileList;
    public boolean isRoot;
    public State parent;
    public int gCost;
    public int hCost;
    public int totalCost;       //totalCost is the total gCost in the path expanded so far
    public int priority;

    //For creating the start state
    public State(String input){
        tileList = new ArrayList<>();
        for(int i = 0; i < input.length(); i++){
            tileList.add(new Tile(input.charAt(i)));
        }
        isRoot = true;
        gCost = 0;
        totalCost = 0;
    }

    //Copy constructor
    public State(State state){
        this.tileList = new ArrayList<>();
        for(Tile t : state.tileList)
            this.tileList.add(t);
        isRoot = false;
        this.gCost = state.gCost;
        this.hCost = state.hCost;
    }

    @Override
    public boolean equals(Object obj) {
        State o = (State) obj;
        for(int i = 0; i < tileList.size(); i++){
            if(!tileList.get(i).equals(o.tileList.get(i)))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return tileList.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Tile t : tileList)
            builder.append(t.toString());

        return builder.toString();
    }
}
