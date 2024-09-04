package uk.ac.ebi.pride.mongodb.configs;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;


/**
 * This Abstract class is used to Configure all the connections to Spring.
 * @author ypriverol
 */
public abstract class AbstractPrideMongoConfiguration {

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
    public MongoDatabaseFactory mongoDbFactory(){
        return new SimpleMongoClientDatabaseFactory(mongoClient(), getDatabaseName());
    }

    @Bean
    public MongoClient mongoClient() {
        return configureMachineFromURI(getMongoURI());
    }

    public MongoClient configureMachineFromURI(String uri) {
        ConnectionString connectionString = new ConnectionString(uri);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(settings);
    }

    public abstract String getMongoURI();

    public abstract String getDatabaseName();
}
