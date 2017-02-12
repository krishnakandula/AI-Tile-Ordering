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

    //Returns last state when finished
    public State runAlgorithm(String input){
        //Get start state
        State start = new State(input);
        start.hCost = goalTest(start);
        start.parent = null;

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

    private List<State> generateSuccessors(State state){
        List<Tile> tiles = state.tileList;
        List<State> successors = new ArrayList<>();
        int spaceIndex = getSpaceIndex(tiles);
        for(int i = 0; i < tiles.size(); i++){
            if(i != spaceIndex) {
                //Create clone
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

    private void getGCost(State current, State successor, int currentIndex, int spaceIndex){
        if(costFlag) {
            successor.gCost = Math.abs(currentIndex - spaceIndex);
            successor.totalCost = current.totalCost + Math.abs(currentIndex - spaceIndex);
        } else {
            successor.gCost = current.gCost + 1;
            successor.totalCost = successor.gCost;
        }
    }

    //Returns number of tiles out of place
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

//        Check center tile
        if(tiles.get(center).color != Tile.Color.space)
            tilesOutOfPlace++;

        return tilesOutOfPlace;
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
