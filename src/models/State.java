package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 2/3/17.
 */
public class State implements Comparable<State> {

    public State previous;
    public List<Tile> tileList;

    public State(List<Tile> tiles){
        this.tileList = tiles;
    }

    //Copy constructor
    public State(State state){
        this.tileList = new ArrayList<>();
        for(Tile t : state.tileList)
            this.tileList.add(t);
        this.previous = state.previous;
    }

    @Override
    public int compareTo(State o) {
        for(int i = 0; i < tileList.size(); i++){
            if(!tileList.get(i).equals(o.tileList.get(i)))
                return -1;
        }
        return 0;
    }
}
