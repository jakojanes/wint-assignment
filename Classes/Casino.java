package Classes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


public class Casino {

    private long balance;
    private final HashMap<String, Match> matches;
    private final HashMap<String, Player> players;
    private final HashMap<String, Player> illegalPlayers;

    public Casino() {
        this.balance = 0;
        this.matches = new HashMap<>();
        this.players = new HashMap<>();
        this.illegalPlayers = new HashMap<>();
    }


    public void playMatches(String filePath) throws IOException {
        List<String> playerData = FileOperations.readFromFile(filePath);
        for (String data : playerData) {
            List<String> playerRound = List.of(data.split(","));
            String playerId = playerRound.get(0);
            String operation = playerRound.get(1);
            Player player = this.getPlayerFromid(playerId);

            if (illegalPlayers.containsKey(playerId)) {
                continue;
            }

            if (player == null) {
                player = new Player(playerId);
                this.addPlayer(player);
            }


            switch (operation) {
                case "BET": {
                    String matchId = playerRound.get(2);

                    if (this.getMatchFromId(matchId).didPlayerBet(playerId)) {
                        continue;
                    }

                    int betAmount = Integer.parseInt(playerRound.get(3));
                    String betPick = playerRound.get(4);
                    player.makeBet(matchId, betAmount, betPick, this, playerRound);
                    break;
                }
                case "WITHDRAW": {
                    int withdrawAmount = Integer.parseInt(playerRound.get(3));
                    player.removeBalance(withdrawAmount, playerRound, this);
                    break;
                }
                case "DEPOSIT": {
                    int depositAmount = Integer.parseInt(playerRound.get(3));
                    player.addBalance(depositAmount);
                    break;
                }
                default: {
                    System.out.println("Unknown operation: " + operation);
                    break;
                }
            }
        }
        writeResult();
    }

    private void writeResult() throws IOException {
        FileOperations.writeResultToFile(players, illegalPlayers, this.balance);
    }

    public void addPlayerToIllegal(Player player) {
        illegalPlayers.put(player.getPlayerId(), player);
        players.remove(player.getPlayerId());
    }

    private void addPlayer(Player player) {
        players.put(player.getPlayerId(), player);
    }

    private Player getPlayerFromid(String id) {
        return players.get(id);
    }

    public void addMatch(Match match) {
        matches.put(match.getMatchId(), match);
    }

    public Match getMatchFromId(String id) {
        return matches.get(id);
    }

    public void removeBalance(long amount) {
        this.balance -= amount;
    }

    public void addBalance(long amount) {
        this.balance += amount;
    }
}
