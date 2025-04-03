package co.edu.ing.escuela.backamongus.repositories;

import co.edu.ing.escuela.backamongus.classes.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {
    @Query("{name:'?0'}")
    Player findPlayerByName(String name);

    @Query("{idPlayer:'?0'}")
    Player findPlayerById(String id);

    public long count();

}