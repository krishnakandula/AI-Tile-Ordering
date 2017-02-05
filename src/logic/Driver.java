package logic;

import controllers.StateController;
import models.State;
import models.Tile;
import models.TreeNode;

import java.util.*;

/**
 * Created by Krishna Chaitanya Kandula on 2/4/17.
 */
public class Driver {

    private StateController controller;
    private HashSet<State> visited;
    private List<State> stateHeap;
    public static int numberOfChildren;

    public Driver(StateController controller){
        this.controller = controller;
        this.visited = new HashSet<>();
    }

    //Returns last state when finished
    public State runAlgorithm(String input){
        numberOfChildren = input.length() - 1;
        //Get start state
        State start = new State(input);
        stateHeap = new ArrayList<>();
        stateHeap.add(null);

        controller.addState(start);

        while(!controller.isEmpty()){
            State current = controller.getState();
            stateHeap.add(current);

            if(current != null) {
                if (goalTest(current)) {
                    return current;
                }

                if (!visited.contains(current)) {
                    visited.add(current);
                    //Get successors and add them to data struc
                    for (State succesor : generateSuccessors(current))
                        controller.addState(succesor);
                } else {
                    //Add null children to heap
                    for(int i = 0; i < numberOfChildren; i++)
                        controller.addState(null);
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
                State succsesor = new State(state);
                List<Tile> successorList = succsesor.tileList;

                //Swap place of current tile and space
                Tile temp = successorList.get(i);
                successorList.set(i, successorList.get(spaceIndex));
                successorList.set(spaceIndex, temp);

                successors.add(succsesor);
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

        TreeNode root = TreeNode.heapify(stateHeap);
        path.append(pathFormat(stateHeap.get(1), -1, 0));
        getFinalPathHelper(root, stateHeap.get(stateHeap.size() - 1), 1, path);
        return path.toString();
    }

    private void getFinalPathHelper(TreeNode node, State goal, int iteration, StringBuilder path){
        if(node.value.equals(goal))
            return;

        TreeNode next = null;
        for(TreeNode n : node.children){
            if(n != null && n.contains(goal)){
                next = n;
                break;
            }
        }

        int indexMoved = isOneAway(node.value, next.value);
        path.append(pathFormat(next.value, indexMoved, iteration++));
        getFinalPathHelper(next, goal, iteration, path);
    }

    private String pathFormat(State current, int indexMoved, int iteration){
        StringBuilder builder = new StringBuilder(String.format("Step %d:   ", iteration));
        if(indexMoved >= 0)
            builder.append(String.format("move %d ", indexMoved));
        builder.append(current).append(" \n");
        return builder.toString();
    }
}
