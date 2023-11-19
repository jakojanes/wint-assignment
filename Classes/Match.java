package Classes;

import java.util.HashSet;
import java.util.Set;

public class Match {
    private final String matchId;
    private final double returnA;
    private final double returnB;
    private final String result;
    private final Set<String> playersWhoBet;


    public Match(String matchId, double returnA, double returnB, String result) {
        this.matchId = matchId;
        this.returnA = returnA;
        this.returnB = returnB;
        this.result = result;
        this.playersWhoBet = new HashSet<>();
    }

    public String getMatchId() {
        return matchId;
    }

    public double getReturn(String pick) {
        return pick.equals("A") ? this.returnA : this.returnB;
    }

    public String getResult() {
        return result;
    }
    public boolean didPlayerBet(String uid){
        return this.playersWhoBet.contains(uid);
    }

    public void addPlayerWhoBet(String uid){
        this.playersWhoBet.add(uid);
    }
}
