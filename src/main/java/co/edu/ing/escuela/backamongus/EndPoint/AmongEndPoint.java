package co.edu.ing.escuela.backamongus.EndPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Logger;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ServerEndpoint("/play")
public class AmongEndPoint {
    private static final Logger logger = Logger.getLogger(AmongEndPoint.class.getName());
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static Queue<Session> queue = new ConcurrentLinkedQueue<>();
    static Map<String, PlayerState> playerStates = new HashMap<>();
    Session ownSession = null;

    public void send(String msg) {
        try {
            for (Session session : queue) {
                if (!session.equals(this.ownSession)) {
                    session.getBasicRemote().sendText(msg);
                }
                logger.log(Level.INFO, "Sent: {0}", msg);
            }
            System.out.println("Sent to all players: " + msg);
        } catch (IOException e) {
            logger.log(Level.INFO, e.toString());
        }
    }

    @OnMessage
    public void processPoint(String message, Session session) {
        try {
            PlayerAction action = objectMapper.readValue(message, PlayerAction.class);
            String sessionId = action.getId();

            PlayerState state = playerStates.getOrDefault(sessionId, new PlayerState(sessionId));

            // Actualizar solo si se reciben nuevas coordenadas
            if (action.getX() != 0 || action.getY() != 0) {
                state.setX(action.getX());
                state.setY(action.getY());
            }

            state.setPlayerId(action.getId());
            state.setKey(action.getKey());
            state.setName(action.getName());

            playerStates.put(sessionId, state);

            String updatedStateMessage = objectMapper.writeValueAsString(playerStates);
            this.send(updatedStateMessage);

            logger.log(Level.INFO, "Point received: {0}. From session: {1}", new Object[]{message, session});
            System.out.println("Received from player: " + message);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to process point", e);
        }
    }

    @OnOpen
    public void openConnection(Session session) {
        queue.add(session);
        ownSession = session;
        logger.log(Level.INFO, "Connection opened.");
        try {
            session.getBasicRemote().sendText("Connection established.");
            System.out.println("New connection established with session ID: " + session.getId());

            // Inicializar el estado del nuevo jugador
            PlayerState newPlayerState = new PlayerState(session.getId());
            playerStates.put(session.getId(), newPlayerState);

            // Enviar el estado actual a todos los jugadores
            String updatedStateMessage = objectMapper.writeValueAsString(playerStates);
            this.send(updatedStateMessage);
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        }
    }

    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
        playerStates.remove(session.getId());
        logger.log(Level.INFO, "Connection closed.");
        System.out.println("Connection closed with session ID: " + session.getId());

        try {
            // Notificar a los demás jugadores que este jugador se ha desconectado
            String updatedStateMessage = objectMapper.writeValueAsString(playerStates);
            this.send(updatedStateMessage);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to send updated state after player disconnection", e);
        }
    }

    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
        playerStates.remove(session.getId());
        logger.log(Level.INFO, t.toString());
        logger.log(Level.INFO, "Connection error.");
        System.out.println("Error in connection with session ID: " + session.getId() + ". Error: " + t.getMessage());
    }

    private static class PlayerState {
        @Setter
        @Getter
        private String sessionId;
        @Setter
        @Getter
        private String playerId;
        @Setter
        @Getter
        private int x;
        @Setter
        @Getter
        private int y;
        @Setter
        @Getter
        private String key;
        @Setter
        @Getter
        private String name;

        public PlayerState(String sessionId) {
            this.sessionId = sessionId;
        }

    }

    public static class PlayerAction {
        @Setter
        @Getter
        private String id;
        @Setter
        @Getter
        private String name;
        @Setter
        @Getter
        private int x;
        @Setter
        @Getter
        private int y;
        @Setter
        @Getter
        private String key;
        @Setter
        @Getter
        private Boolean moveEnd;

        // Constructor por defecto necesario para Jackson
        public PlayerAction() {}


    }
}