package Classes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class Player {

    private long balance;
    private boolean hasMadeIllegalMove;
    private String illegalMove;
    private int betsMade;
    private int betsWon;
    private long profitLoss;

    private final String playerId;

    public Player(String playerId) {
        this.balance = 0;
        this.playerId = playerId;
        this.hasMadeIllegalMove = false;
        this.illegalMove = null;
        this.betsMade = 0;
        this.betsWon = 0;
        this.profitLoss = 0;
    }

    public long getBalance() {
        return balance;
    }

    public void makeBet(String matchId, int amount, String pick, Casino casino, List<String> playerRound) {
        if (this.balance >= amount) {


            Match match = casino.getMatchFromId(matchId);
            match.addPlayerWhoBet(this.playerId);

            if (match.getResult().equals("DRAW")) {
                this.betsMade += 1;
                return;
            }

            if (match.getResult().equals(pick)) {
                long betWonAmount = (long) (amount * match.getReturn(pick));
                this.balance += betWonAmount;
                this.profitLoss += betWonAmount;
                casino.removeBalance(betWonAmount);
                this.betsMade += 1;
                this.betsWon += 1;
            } else {
                this.balance -= amount;
                this.profitLoss -= amount;
                casino.addBalance(amount);
                this.betsMade += 1;
            }

        } else {
            makePlayerIllegal(casino, playerRound);

        }
    }

    public String getIllegalMove() {
        return illegalMove;
    }

    public BigDecimal winRate() {
        if (betsMade == 0 || betsWon == 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal rate = BigDecimal.valueOf((double) betsWon / betsMade);
        return rate.setScale(2, RoundingMode.HALF_UP);
    }

    private void makePlayerIllegal(Casino casino, List<String> playerRound) {
        this.hasMadeIllegalMove = true;
        StringBuilder illegalMoveBuilder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < playerRound.size() && !playerRound.get(i).isEmpty()) {
                illegalMoveBuilder.append(playerRound.get(i)).append(" ");
            } else {
                illegalMoveBuilder.append("null ");
            }
        }
        this.illegalMove = illegalMoveBuilder.toString().trim();
        casino.addBalance(this.profitLoss);
        casino.addPlayerToIllegal(this);
    }

    public void addBalance(int amount) {
        this.balance += amount;
    }

    public void removeBalance(int amount, List<String> playerRound, Casino casino) {
        if (this.balance >= amount) {
            this.balance -= amount;
        } else {
            makePlayerIllegal(casino, playerRound);
        }
    }

    public String getPlayerId() {
        return playerId;
    }

}
