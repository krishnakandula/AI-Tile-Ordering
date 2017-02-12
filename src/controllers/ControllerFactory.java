package controllers;

/**
 * Created by Krishna Chaitanya Kandula on 2/7/2017.
 */
public class ControllerFactory {
    public static StateController getController(String controllerType, boolean costFlag){
        switch (controllerType){
            case "DFS":
                return new DFSController();
            case "UCS":
                return new UCSController(costFlag);
            case "GS":
                return new GSController();
            case "A-star":
                return new AStarController();
            default:
                return new BFSController();
        }
    }
}
