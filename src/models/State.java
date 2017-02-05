package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 2/3/17.
 */
public class State implements Comparable<State> {

    public State previous;
    public List<Tile> tileList;
    public boolean isRoot;

    //For creating the start state
    public State(String input){
        this.previous = null;
        tileList = new ArrayList<>();
        for(int i = 0; i < input.length(); i++){
            tileList.add(new Tile(input.charAt(i)));
        }
        isRoot = true;
    }

    public void setPrevious(State previous) {
        if(!this.isRoot)
            this.previous = previous;
    }

    //Copy constructor
    public State(State state){
        this.tileList = new ArrayList<>();
        for(Tile t : state.tileList)
            this.tileList.add(t);
        this.previous = state.previous;
        isRoot = false;
    }

    @Override
    public int compareTo(State o) {
        for(int i = 0; i < tileList.size(); i++){
            if(!tileList.get(i).equals(o.tileList.get(i)))
                return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return tileList.toString();
    }
}
