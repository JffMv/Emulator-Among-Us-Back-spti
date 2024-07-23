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

    public TaskResponse(String type, boolean available, String playerId) {
        this.type = type;
        this.available = available;
        this.playerId = playerId;
    }
}
