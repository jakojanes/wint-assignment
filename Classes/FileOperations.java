package Classes;

import java.io.*;
import java.util.*;

public class FileOperations {


    public static void writeResultToFile(HashMap<String, Player> players, HashMap<String, Player> illegalPlayers, long balance) throws IOException {
        String filePath = "result.txt";
        List<String> legalPlayersList = new ArrayList<>();
        List<String> illegalPlayersList = new ArrayList<>();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Player player : players.values()) {
                String line = player.getPlayerId() + " " + player.getBalance() + " " + player.winRate();
                legalPlayersList.add(line);
            }
            legalPlayersList.sort(Comparator.comparing(String::toString));

            for (Player illegalPlayer : illegalPlayers.values()) {
                String line = illegalPlayer.getIllegalMove();
                illegalPlayersList.add(line);
            }
            illegalPlayersList.sort(Comparator.comparing(String::toString));


            writeLinesToFile(writer, legalPlayersList);
            writer.write(System.lineSeparator());
            writeLinesToFile(writer, illegalPlayersList);


            writer.write(System.lineSeparator() + balance);
        }
    }

    public static void writeLinesToFile(BufferedWriter writer, List<String> lines) throws IOException {
        if (lines.isEmpty()) {
            writer.write(System.lineSeparator());
        }
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
    }

    public static List<String> readFromFile(String filePath) throws IOException {
        FileReader fileReader = new FileReader(filePath);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        List<String> lines = new ArrayList<>();

        try {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } finally {
            bufferedReader.close();
        }

        return lines;
    }


    public static void addMatchesToCasino(Casino casino, String filePath) throws IOException {
        List<String> matchData = readFromFile(filePath);
        for (String data : matchData) {
            List<String> values = List.of(data.split(","));

            Match match = new Match(values.get(0), Double.parseDouble(values.get(1)), Double.parseDouble(values.get(2)), values.get(3));
            casino.addMatch(match);
        }

    }

}
