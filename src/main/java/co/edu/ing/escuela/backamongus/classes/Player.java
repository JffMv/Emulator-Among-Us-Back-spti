package co.edu.ing.escuela.backamongus.classes;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashMap;


@AllArgsConstructor
@Document("PlayersAmongUs")
public class Player {
    @Setter
    @Getter
    private String name;

    @Id
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
    @Setter
    @Getter
    private Boolean isAlive;
    public  Player(){
        this.generateTask();
    }

    public Player(String name, String idPlayer, String idSession) {
        this.name = name;
        this.idPlayer = idPlayer;
        this.idSession = idSession;
        this.generateTask();
    }

    private void generateTask(){
        for(int i = 1; i<12;i++){
            tasks.put(i, false);
        }
    }
    public boolean stateOfTask(Integer taskId) {
        Boolean taskStatus = tasks.get(taskId);
        if (taskStatus == null) {
            throw new IllegalArgumentException("Task ID not found");
        }
        return taskStatus;
    }
    public void updateTaks(Integer key){
        this.tasks.put(key, true);
    }
}
