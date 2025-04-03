package co.edu.ing.escuela.backamongus.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class MatchAmongUsTest {

    private MatchAmongUs match;

    @BeforeEach
    void setUp() {
        match = new MatchAmongUs();
    }

    @Test
    void testExistThisPlayer() {
        Player player = new Player();
        match.addPlayers("player1", player);

        assertTrue(match.existThisPlayer("player1"));
        assertFalse(match.existThisPlayer("player2"));
    }

    @Test
    void testAvailableTaskPlayer() {
        Player player = new Player();

        player.updateTaks(1); // Asumiendo que esto marca la tarea 1 como completada
        match.addPlayers("player1", player);

        assertTrue(match.availableTaskPlayer("player1", "1"));
        assertFalse(match.availableTaskPlayer("player1", "2")); // Asumiendo que la tarea 2 aún no se ha completado
    }

    @Test
    void testGetId() {
        String id = "match123";
        match.setId(id);
        assertEquals(id, match.getId());
    }

    @Test
    void testGetPlayers() {
        Player player1 = new Player();
        Player player2 = new Player();
        match.addPlayers("player1", player1);
        match.addPlayers("player2", player2);

        HashMap<String, Player> players = match.getPlayers();
        assertEquals(2, players.size());
        assertTrue(players.containsKey("player1"));
        assertTrue(players.containsKey("player2"));
    }

    @Test
    void testGetStateImpostor() {
        Boolean state = true;
        match.setStateImpostor(state);
        assertEquals(state, match.getStateImpostor());
    }
}