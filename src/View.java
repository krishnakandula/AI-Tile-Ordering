import controllers.*;
import logic.Driver;
import models.State;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Krishna Chaitanya Kandula on 2/3/17.
 */
public class View {

    private static String INPUT_FILE_PATH = new File("")
            .getAbsolutePath()
            .concat("/");
    
    public static void main(String... args){
        if(args.length < 3 || args.length > 4) {
            System.out.println("Invalid arguments: " + Arrays.toString(args));
            System.exit(0);
        }

        if(!args[0].equals("search")) {
            System.out.println(args[0] + " command not found");
            System.exit(0);
        }

        boolean costFlag = false;
        String inputFileName;
        StateController controller;

        if(args[1].equals("-cost")){
            costFlag = true;
            controller = ControllerFactory.getController(args[2]);
            inputFileName = args[3];
        } else {
            controller = ControllerFactory.getController(args[1]);
            inputFileName = args[2];
        }
		
        INPUT_FILE_PATH = INPUT_FILE_PATH.concat(inputFileName);
        String input = getTileInput();
        Driver driver = new Driver(controller, costFlag);
        State finalState = driver.runAlgorithm(input);
        System.out.println(driver.getFinalPath(finalState));
    }

    private static String getTileInput(){
        //Initialize readers
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
