import controllers.StateController;
import models.State;
import models.Tile;

import java.util.*;

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

    //Returns last state when finished
    public State runAlgorithm(String input){
        //Get start state
        State start = new State(input);

        controller.addState(start);
        State prev = start;

        while(!controller.isEmpty()){
            State current = controller.getState();

            //Set current state
            current.setPrevious(prev);
            prev = current;

            if(goalTest(current))
                return current;

            if(!visited.contains(current)) {
                visited.add(current);
                //Get successors and add them to data struc
                for (State succesor : generateSuccessors(current))
                    controller.addState(succesor);
            }
        }
        return null;
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

    private int isOneAway(State s1, State s2){
        int diff = 2;
        List<Tile> s1Tiles = s1.tileList;
        List<Tile> s2Tiles = s2.tileList;

        int swapIndex = 0;
        for(int i = 0; i < s1Tiles.size(); i++){
            if(!(s1Tiles.get(i).equals(s2Tiles.get(i)))){
                if(diff <= 0)
                    return -1;
                if(s1Tiles.get(i).color != Tile.Color.space)
                    swapIndex = i;
                diff--;
            }
        }

        return swapIndex;
    }

    public String getFinalPath(State state){
        StringBuilder path = new StringBuilder();
        //Create list of all the expanded states
        List<State> expandedStates = new ArrayList<>();
        while(state != null) {
            expandedStates.add(state);
            state = state.previous;
        }
        Collections.reverse(expandedStates);
        path.append(pathFormat(expandedStates.get(0), -1, 0));

        //Figure out which states are excessive
        int index = 0;
        int iteration = 1;
        int swapIndex = 0;

        while(index < expandedStates.size()){
            int checkIndex = expandedStates.size() - 1;
            if(index == checkIndex) {
                path.append(pathFormat(expandedStates.get(checkIndex), swapIndex, iteration));
                return path.toString();
            }
            while((swapIndex = isOneAway(expandedStates.get(index), expandedStates.get(checkIndex))) != -1 && checkIndex > index)
                checkIndex--;
            path.append(pathFormat(expandedStates.get(checkIndex), swapIndex, iteration));
            index = checkIndex;
            iteration++;
        }

        return null;
    }

    private String pathFormat(State current, int indexMoved, int iteration){
        StringBuilder builder = new StringBuilder(String.format("Step %d:", iteration));
        if(indexMoved >= 0)
            builder.append(String.format("move %d", indexMoved));
        builder.append(current).append("\n");
        return builder.toString();
    }
}

class StateComparator implements Comparator<State> {
    @Override
    public int compare(State o1, State o2) {
        return o1.compareTo(o2);
    }
}
