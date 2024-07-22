package co.edu.ing.escuela.backamongus.classes;

import lombok.*;

import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
public class Player {
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private String idPlayer;
    @Setter
    @Getter
    private String isImpostor;
    @Setter
    @Getter
    private HashMap<Integer, Boolean> tasks = new HashMap<>();
    @Setter
    @Getter
    private String idSession;

    public Player(String name, String idPlayer, String idSession) {
        this.name = name;
        this.idPlayer = idPlayer;
        this.idSession = idSession;
        this.generateTask();
    }

    private void generateTask(){
        for(int i = 0; i<11;i++){
            tasks.put(i, false);
        }
    }
}
