package co.edu.ing.escuela.backamongus.configures;

import co.edu.ing.escuela.backamongus.repositories.PlayerRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.context.annotation.FilterType;

@Configuration
@EnableMongoRepositories(basePackages = "co.edu.ing.escuela.backamongus.repositories",
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = PlayerRepository.class),
        mongoTemplateRef = "playersMongoTemplate")
public class PlayersMongoConfig {

    @Bean(name = "playersProperties")
    @ConfigurationProperties(prefix = "players.mongodb")
    public MongoProperties playersProperties() {
        return new MongoProperties();
    }

    @Bean(name = "playersMongoClient")
    public MongoClient mongoClient(@Qualifier("playersProperties") MongoProperties mongoProperties) {
        return MongoClients.create(mongoProperties.getUri());
    }

    @Bean(name = "playersMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("playersMongoClient") MongoClient mongoClient,
                                       @Qualifier("playersProperties") MongoProperties mongoProperties) {
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }
}