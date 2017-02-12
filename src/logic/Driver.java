package logic;

import controllers.StateController;
import models.State;
import models.Tile;

import java.util.*;

/**
 * Created by Krishna Chaitanya Kandula on 2/4/17.
 */
public class Driver {

    private StateController controller;
    private HashSet<State> visited;
    private boolean costFlag;

    public Driver(StateController controller, boolean costFlag){
        this.controller = controller;
        this.visited = new HashSet<>();
        this.costFlag = costFlag;
    }

    /**
     * Runs the generic search algorithm
     * @param input the initial state provided by the input file
     * @return the goal state that contains its path
     */
    public State runAlgorithm(String input){
        //Get start state
        State start = new State(input);
        start.hCost = goalTest(start);
        start.parent = null;

        //Add the initial state to the data structure
        controller.addState(start);

        while(!controller.isEmpty()){
            State current = controller.getState();
            if(current != null) {
                //Goal test
                if (current.hCost == 0)
                    return current;

                if (!visited.contains(current)) {
                    visited.add(current);
                    //Get successors and add them to data structure
                    controller.addStates(generateSuccessors(current));
                }
            }
        }
        return null;
    }

    /**
     * Generates the successors of state using the successor function
     * @param state the state to generate successors for
     * @return the list of successor states
     */
    private List<State> generateSuccessors(State state){
        List<Tile> tiles = state.tileList;
        List<State> successors = new ArrayList<>();
        int spaceIndex = getSpaceIndex(tiles);
        for(int i = 0; i < tiles.size(); i++){
            if(i != spaceIndex) {
                //Create clone of current state
                State successor = new State(state);
                successor.parent = state;

                List<Tile> successorList = successor.tileList;

                //Swap place of current tile and space
                Tile temp = successorList.get(i);
                successorList.set(i, successorList.get(spaceIndex));
                successorList.set(spaceIndex, temp);

                successor.hCost = goalTest(successor);
                getGCost(state, successor, i, spaceIndex);

                successors.add(successor);
            }
        }
        return successors;
    }

    /**
     * Finds where the space tile is in a state
     * @param tiles the set of tiles of a state
     * @return the index of the space tile
     */
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

    /**
     * Gets the gCost of a state determined by the cost flag
     * @param parent the parent state
     * @param successor the state for which the gCost is being calculated
     * @param currentIndex the latest index where a tile was swapped
     * @param spaceIndex the index that contains the space tile
     */
    private void getGCost(State parent, State successor, int currentIndex, int spaceIndex){
        if(costFlag) {
            successor.gCost = Math.abs(currentIndex - spaceIndex);
            successor.totalCost = parent.totalCost + Math.abs(currentIndex - spaceIndex);
        } else {
            successor.gCost = parent.gCost + 1;
            successor.totalCost = successor.gCost;
        }
    }

    //Returns number of tiles out of place

    /**
     * Checks whether the current state is the goal state by calculating the number of tiles out of place
     * @param current the state that is being checked
     * @return the number of tiles out of place
     */
    private int goalTest(State current){
        List<Tile> tiles = current.tileList;
        int tilesOutOfPlace = 0;
        int center = tiles.size() / 2;

        //Check all tiles left of spaceIndex
        for(int i = 0; i < center; i++){
            if(tiles.get(i).color != Tile.Color.space && tiles.get(i).color != Tile.Color.black)
                tilesOutOfPlace++;
        }
        //Check all tiles right of spaceIndex
        for(int i = center + 1; i < tiles.size(); i++){
            if(tiles.get(i).color != Tile.Color.space && tiles.get(i).color != Tile.Color.white)
                tilesOutOfPlace++;
        }

        //Check center tile
        if(tiles.get(center).color != Tile.Color.space)
            tilesOutOfPlace++;

        return tilesOutOfPlace;
    }

    /**
     * Calculates the number of indices a tile has moved from its previous state
     * @param s1
     * @param s2
     * @return the number of indices a tile has moved
     */
    private int isOneAway(State s1, State s2){
        int diff = 2;
        List<Tile> s1Tiles = s1.tileList;
        List<Tile> s2Tiles = s2.tileList;

        int swapIndex = 0;
        for(int i = 0; i < s1Tiles.size(); i++){
            if(!(s1Tiles.get(i).equals(s2Tiles.get(i)))){
                if(diff <= 0)
                    return -1;
                boolean checkSwap = !(s1Tiles.get(i).color == Tile.Color.space ^ s2Tiles.get(i).color == Tile.Color.space);
                if(checkSwap)
                    return -1;
                if(s1Tiles.get(i).color != Tile.Color.space)
                    swapIndex = i;
                diff--;
            }
        }

        return swapIndex;
    }

    /**
     * Generates the path to the goal state
     * @param state the goal state
     * @return the String containing the path
     */
    public String getFinalPath(State state){
        StringBuilder path = new StringBuilder();
        Stack<State> statePath = new Stack<>();

        while(state != null){
            statePath.push(state);
            state = state.parent;
        }

        State prev = statePath.pop();
        path.append(pathFormat(prev, -1, 0));

        int iteration = 1;
        while(!statePath.isEmpty()) {
            State current = statePath.pop();
            int indexMoved = isOneAway(prev, current);
            String formattedPath = pathFormat(current, indexMoved, iteration++);
            path.append(formattedPath);
            prev = current;
        }

        return path.toString();
    }

    /**
     * Helps format the final path to meet requirements
     * @param current the current state
     * @param indexMoved the index a tile moved from its previous state
     * @param iteration the number of states so far
     * @return the formatted String for the state
     */
    private String pathFormat(State current, int indexMoved, int iteration){
        StringBuilder builder = new StringBuilder(String.format("Step %d:   ", iteration));
        if(indexMoved >= 0)
            builder.append(String.format("move %d ", indexMoved));
        builder.append(current);
        if(costFlag && indexMoved >= 0)
            builder.append(String.format("  (c=%d)", current.gCost));
        return builder.append("\n").toString();
    }
}
