package co.edu.ing.escuela.backamongus.services;

import co.edu.ing.escuela.backamongus.classes.MatchAmongUs;
import co.edu.ing.escuela.backamongus.classes.Player;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {

    @Autowired
    @Qualifier("matchesMongoTemplate")
    private MongoTemplate mongoTemplate;

    // Crear una nueva partida
    public MatchAmongUs createMatch(MatchAmongUs match) {
        return mongoTemplate.save(match);
    }

    // Leer una partida por su ID
    public MatchAmongUs getMatchById(String id) {
        return mongoTemplate.findById(id, MatchAmongUs.class);
    }

    // Leer todas las partidas
    public List<MatchAmongUs> getAllMatches() {
        return mongoTemplate.findAll(MatchAmongUs.class);
    }

    // Actualizar una partida
    public MatchAmongUs updateMatch(MatchAmongUs match) {
        Query query = new Query(Criteria.where("id").is(match.getId()));
        Update update = new Update()
                .set("players", match.getPlayers())
                .set("stateImpostor", match.getStateImpostor());
        mongoTemplate.updateFirst(query, update, MatchAmongUs.class);
        return getMatchById(match.getId());
    }

    // Borrar una partida por su ID
    public void deleteMatch(String id) {
        Query query = new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query, MatchAmongUs.class);
    }
    /**

    // Añadir un jugador a una partida
    public MatchAmongUs addPlayerToMatch(String matchId, Player player) {
        MatchAmongUs match = getMatchById(matchId);
        if (match != null) {
            match.addPlayers(player.getIdPlayer(), player);
            return updateMatch(match);
        }
        return null;
    }

    // Verificar si un jugador existe en una partida
    public boolean playerExistsInMatch(String matchId, String playerId) {
        MatchAmongUs match = getMatchById(matchId);
        return match != null && match.existThisPlayer(playerId);
    }

    // Verificar si una tarea está disponible para un jugador
    public boolean isTaskAvailableForPlayer(String matchId, String playerId, String taskId) {
        MatchAmongUs match = getMatchById(matchId);
        return match != null && match.availableTaskPlayer(playerId, taskId);
    }

    // Modificar el estado de una tarea para un jugador
    public MatchAmongUs modifyTaskForPlayer(String matchId, String playerId, String taskId) {
        MatchAmongUs match = getMatchById(matchId);
        if (match != null) {
            match.modifiedTask(playerId, taskId);
            return updateMatch(match);
        }
        return null;
    }

    // Cambiar el estado del impostor
    public MatchAmongUs setImpostorState(String matchId, Boolean state) {
        MatchAmongUs match = getMatchById(matchId);
        if (match != null) {
            match.setStateImpostor(state);
            return updateMatch(match);
        }
        return null;
    }*/
}