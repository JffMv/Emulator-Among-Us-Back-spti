package co.edu.ing.escuela.backamongus.classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player("TestPlayer", "123", "456");
    }

    @Test
    void testStateOfTask_InitialState() {
        Player player = new Player();
        assertEquals(false, player.stateOfTask(1));
    }

    @Test
    void testStateOfTask_AfterUpdate() {
        int taskToUpdate = 5;
        player.updateTaks(taskToUpdate);
        assertTrue(player.stateOfTask(taskToUpdate), "Updated task should be true");
    }

    @Test
    void testStateOfTask_InvalidTaskId() {
        Player player = new Player();
        assertThrows(IllegalArgumentException.class, () -> {
            player.stateOfTask(-1);
        });
    }
}