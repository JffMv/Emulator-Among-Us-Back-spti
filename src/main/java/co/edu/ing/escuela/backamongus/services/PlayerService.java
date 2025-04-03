package co.edu.ing.escuela.backamongus.services;

import co.edu.ing.escuela.backamongus.classes.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PlayerService {

    private final MongoTemplate mongoTemplate;

    public PlayerService(@Qualifier("playersMongoTemplate") MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public static final String ID_PLAYER = "idPlayer";
    public static final String ID_SESSION = "idSession";

    public Player createPlayer(Player player) {
        return mongoTemplate.save(player);
    }

    public Player getPlayerById(String idPlayer) {
        return mongoTemplate.findById(idPlayer, Player.class);
    }

    public List<Player> getAllPlayers() {   
        return mongoTemplate.findAll(Player.class);
    }

    public Player updatePlayer(Player player) {
        Query query = new Query(Criteria.where(this.ID_PLAYER).is(player.getIdPlayer()));
        Update update = new Update()
                .set("name", player.getName())
                .set("isImpostor", player.getIsImpostor())
                .set("tasks", player.getTasks())
                .set(this.ID_SESSION, player.getIdSession())
                .set("isAlive", player.getIsAlive());
        mongoTemplate.updateFirst(query, update, Player.class);
        return getPlayerById(player.getIdPlayer());
    }

    public void deletePlayer(String idPlayer) {
        Query query = new Query(Criteria.where(this.ID_PLAYER).is(idPlayer));
        mongoTemplate.remove(query, Player.class);
    }
    public void deletePlayerSession(String idSession) {
        Query query = new Query(Criteria.where(this.ID_SESSION).is(idSession));
        mongoTemplate.remove(query, Player.class);
    }

    public List<Player> getPlayersBySession(String idSession) {
        Query query = new Query(Criteria.where(this.ID_SESSION).is(idSession));
        return mongoTemplate.find(query, Player.class);
    }
    public boolean playerExists(String idPlayer) {
        Query query = new Query(Criteria.where(this.ID_PLAYER).is(idPlayer));
        return mongoTemplate.exists(query, Player.class);
    }



    public Boolean getTaskStatus(String idPlayer, Integer taskKey) {
        Query query = new Query(Criteria.where("ID_PLAYER").is(idPlayer));
        Player player = mongoTemplate.findOne(query, Player.class);
        
        if (player == null) {
            throw new RuntimeException("Jugador con id " + idPlayer + " no encontrado.");
        }
        
        if (player.getTasks() == null || !player.getTasks().containsKey(taskKey)) {
            throw new RuntimeException("Tarea con clave " + taskKey + " no encontrada para el jugador " + idPlayer + ".");
        }
        
        return player.getTasks().get(taskKey);
    }



    public Map<Integer, Boolean> getAllTasks(String idPlayer) {
        Query query = new Query(Criteria.where(this.ID_PLAYER).is(idPlayer));
        Player player = mongoTemplate.findOne(query, Player.class);

        if (player != null) {
            return player.getTasks();
        }
        return new Map<Integer, Boolean>();
    }

    // Actualizar una tarea específica por jugador y clave
    public Player updateTask(String idPlayer, Integer taskKey, Boolean newValue) {
        Query query = new Query(Criteria.where(this.ID_PLAYER).is(idPlayer));
        Update update = new Update().set("tasks." + taskKey, newValue);
        mongoTemplate.updateFirst(query, update, Player.class);
        return getPlayerById(idPlayer);
    }
}