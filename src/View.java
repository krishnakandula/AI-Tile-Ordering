import controllers.BFSController;
import controllers.StateController;
import models.State;
import models.Tile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Krishna Chaitanya Kandula on 2/3/17.
 */
public class View {
    private static final String INPUT_FILE_PATH = new File("").getAbsolutePath().concat("/input.txt");
    public static void main(String... args){
        //TODO: Get commandline arguments
        String input = getInput();
        StateController controller = new BFSController();
        Driver driver = new Driver(controller);
        State finalState = driver.runAlgorithm(input);
        System.out.println(driver.getFinalPath(finalState));
    }

    private static String getInput(){
        //Initalize readers
        String initialState = null;
        FileReader fileReader = null;
        BufferedReader reader = null;
        try {
            fileReader = new FileReader(INPUT_FILE_PATH);
            reader = new BufferedReader(fileReader);
            String currentLine = reader.readLine();
            if(currentLine != null)
                return currentLine;
            else
                throw new Exception("Couldn't read file");
        } catch (Exception e){
            System.out.println("Error reading file: " + e.getMessage());
            System.exit(0);
        } finally {
            try {
                if(fileReader != null)
                    fileReader.close();
                if(reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return initialState;
    }
}
