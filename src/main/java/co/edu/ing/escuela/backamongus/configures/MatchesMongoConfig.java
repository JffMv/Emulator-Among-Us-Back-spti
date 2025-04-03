package co.edu.ing.escuela.backamongus.configures;

import co.edu.ing.escuela.backamongus.repositories.PlayerRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "co.edu.ing.escuela.backamongus.repositories",
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MatchesMongoConfig.class),

        mongoTemplateRef = "matchesMongoTemplate")
public class MatchesMongoConfig {

    @Primary
    @Bean(name = "matchesProperties")
    @ConfigurationProperties(prefix = "matches.mongodb")
    public MongoProperties matchesProperties() {
        return new MongoProperties();
    }

    @Primary
    @Bean(name = "matchesMongoClient")
    public MongoClient mongoClient(@Qualifier("matchesProperties") MongoProperties mongoProperties) {
        return MongoClients.create(mongoProperties.getUri());
    }

    @Primary
    @Bean(name = "matchesMongoTemplate")
    public MongoTemplate mongoTemplate(@Qualifier("matchesMongoClient") MongoClient mongoClient,
                                       @Qualifier("matchesProperties") MongoProperties mongoProperties) {
        return new MongoTemplate(mongoClient, mongoProperties.getDatabase());
    }
}