package co.edu.ing.escuela.backamongus.classes;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class MatchAmongUs {
    @Setter
    @Getter
    private String id;
    @Setter
    @Getter
    private HashMap <String, Player> players = new HashMap<>();
    @Setter
    @Getter
    private Boolean stateImpostor;
    public void addPlayers(String idPlayer , Player player){
        this.players.put(idPlayer, player);
    }
    public boolean existThisPlayer(String idPlayer){
        return this.players.containsKey(idPlayer);
    }


}
