import controllers.StateController;
import models.State;
import models.Tile;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by Krishna Chaitanya Kandula on 2/4/17.
 */
public class Driver {

    private StateController controller;
    private TreeSet<State> visited;

    public Driver(StateController controller){
        this.controller = controller;
        this.visited = new TreeSet<>(new StateComparator());
    }

    public void runAlgorithm(String input){

    }

    private List<State> generateSuccessors(State state){
        List<Tile> tiles = state.tileList;
        List<State> successors = new ArrayList<>();
        int spaceIndex = getSpaceIndex(tiles);
        for(int i = 0; i < tiles.size(); i++){
            //Create clone
            State succesor = new State(state);
            List<Tile> successorList = succesor.tileList;

            //Swap place of current tile and space
            Tile temp = successorList.get(i);
            successorList.set(i, successorList.get(spaceIndex));
            successorList.set(spaceIndex, temp);

            successors.add(succesor);
        }
        return successors;
    }

    private int getSpaceIndex(List<Tile> tiles){
        int spaceIndex = 0;
        for(int i = 0; i < tiles.size(); i++){
            if(tiles.get(i).color == Tile.Color.space) {
                spaceIndex = i;
                break;
            }
        }
        return spaceIndex;
    }

    private boolean goalTest(State current){
        List<Tile> tiles = current.tileList;
        int spaceIndex = getSpaceIndex(tiles);
        //Check all tiles left of spaceIndex
        for(int i = 0; i < spaceIndex; i++){
            if(tiles.get(i).color != Tile.Color.black)
                return false;
        }
        //Check all tiles right of spaceIndex
        for(int i = spaceIndex + 1; i < tiles.size(); i++){
            if(tiles.get(i).color != Tile.Color.white)
                return false;
        }
        return true;
    }
}

class StateComparator implements Comparator<State> {
    @Override
    public int compare(State o1, State o2) {
        return o1.compareTo(o2);
    }
}
