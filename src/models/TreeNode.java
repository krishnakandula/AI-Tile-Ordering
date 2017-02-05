package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 2/4/2017.
 */
public class TreeNode {
    public TreeNode left;
    public TreeNode right;
    public State value;

    public TreeNode(State value){
        this.value = value;
    }

    public boolean contains(State s){
        if(this.value.equals(s))
            return true;

        boolean leftContains = false;
        boolean rightContains = false;
        if(left != null)
            leftContains = left.contains(s);
        if(right != null)
            rightContains = right.contains(s);

        return  leftContains || rightContains;
    }

    public static TreeNode heapify(List<State> states){
        List<TreeNode> heap = new ArrayList<>();
        heap.add(null);
        for(int i = 1; i < states.size(); i++) {
            if(states.get(i) == null)
                heap.add(null);
            else
                heap.add(new TreeNode(states.get(i)));
        }

        for(int i = 1; i < heap.size(); i++){
            TreeNode n = heap.get(i);
            if(n != null) {
                int leftIndex = i * 2;
                int rightIndex = leftIndex + 1;

                if (leftIndex < heap.size()) {
                    n.left = heap.get(leftIndex);
                }
                if (rightIndex < heap.size())
                    n.right = heap.get(rightIndex);
            }
        }
        return heap.get(1);
    }
}
