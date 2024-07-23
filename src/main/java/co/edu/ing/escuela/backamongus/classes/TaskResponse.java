package co.edu.ing.escuela.backamongus.classes;

import lombok.Getter;
import lombok.Setter;

public class TaskResponse {
    @Getter
    @Setter
    private String type;
    @Getter @Setter
    private boolean available;
    @Getter @Setter
    private String playerId;
    @Getter @Setter
    private String numberGroup;

    public TaskResponse(String type, boolean available, String playerId, String numberGroup) {
        this.type = type;
        this.available = available;
        this.playerId = playerId;
        this.numberGroup=numberGroup;
    }
}
