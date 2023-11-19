import Classes.Casino;
import Classes.FileOperations;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            Casino casino = initializeCasino();
            String PATH_TO_RES_FOLDER = "res/";


            FileOperations.addMatchesToCasino(casino, PATH_TO_RES_FOLDER + "match_data.txt");
            casino.playMatches(PATH_TO_RES_FOLDER + "player_data.txt");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static Casino initializeCasino() {
        return new Casino();
    }
}
