package co.edu.ing.escuela.backamongus.repositories;
import co.edu.ing.escuela.backamongus.classes.MatchAmongUs;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MatchRepository extends MongoRepository<MatchAmongUs, String> {

    @Query("{id:'?0'}")
    MatchAmongUs findMatchRepositoriesBy(String id);
    public long count();

}