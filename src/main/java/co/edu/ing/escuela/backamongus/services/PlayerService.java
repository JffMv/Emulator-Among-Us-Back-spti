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

    @Autowired
    @Qualifier("playersMongoTemplate")
    private MongoTemplate mongoTemplate;

    // Crear un nuevo jugador
    public Player createPlayer(Player player) {
        return mongoTemplate.save(player);
    }

    // Leer un jugador por su ID
    public Player getPlayerById(String idPlayer) {
        return mongoTemplate.findById(idPlayer, Player.class);
    }

    // Leer todos los jugadores
    public List<Player> getAllPlayers() {
        return mongoTemplate.findAll(Player.class);
    }

    // Actualizar un jugador
    public Player updatePlayer(Player player) {
        Query query = new Query(Criteria.where("idPlayer").is(player.getIdPlayer()));
        Update update = new Update()
                .set("name", player.getName())
                .set("isImpostor", player.getIsImpostor())
                .set("tasks", player.getTasks())
                .set("idSession", player.getIdSession())
                .set("isAlive", player.getIsAlive());
        mongoTemplate.updateFirst(query, update, Player.class);
        return getPlayerById(player.getIdPlayer());
    }

    // Borrar un jugador por su ID
    public void deletePlayer(String idPlayer) {
        Query query = new Query(Criteria.where("idPlayer").is(idPlayer));
        mongoTemplate.remove(query, Player.class);
    }
    // Borrar un jugador por su ID session
    public void deletePlayerSession(String idSession) {
        Query query = new Query(Criteria.where("idSession").is(idSession));
        mongoTemplate.remove(query, Player.class);
    }

    // Método adicional: Buscar jugadores por sesión
    public List<Player> getPlayersBySession(String idSession) {
        Query query = new Query(Criteria.where("idSession").is(idSession));
        return mongoTemplate.find(query, Player.class);
    }
    public boolean playerExists(String idPlayer) {
        Query query = new Query(Criteria.where("idPlayer").is(idPlayer));
        return mongoTemplate.exists(query, Player.class);
    }



    // Consultar una tarea específica por jugador y clave
    public Boolean getTaskStatus(String idPlayer, Integer taskKey) {
        Query query = new Query(Criteria.where("idPlayer").is(idPlayer));
        Player player = mongoTemplate.findOne(query, Player.class);

        if (player != null && player.getTasks() != null) {
            return player.getTasks().get(taskKey);
        }
        return null; // o lanza una excepción si prefieres
    }

    // Consultar todas las tareas por jugador
    public Map<Integer, Boolean> getAllTasks(String idPlayer) {
        Query query = new Query(Criteria.where("idPlayer").is(idPlayer));
        Player player = mongoTemplate.findOne(query, Player.class);

        if (player != null) {
            return player.getTasks();
        }
        return null; // o lanza una excepción si prefieres
    }

    // Actualizar una tarea específica por jugador y clave
    public Player updateTask(String idPlayer, Integer taskKey, Boolean newValue) {
        Query query = new Query(Criteria.where("idPlayer").is(idPlayer));
        Update update = new Update().set("tasks." + taskKey, newValue);
        mongoTemplate.updateFirst(query, update, Player.class);
        return getPlayerById(idPlayer);
    }
}