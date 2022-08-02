package uk.ac.ebi.pride.mongodb.configs;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;

/**
 * This Abstract class is used to Configure all the connections to Spring.
 * @author ypriverol
 */
public abstract class   AbstractPrideMongoConfiguration extends AbstractMongoClientConfiguration {

    @Override
    public MongoClient mongoClient() {

        ConnectionString connectionString = new ConnectionString(getMongoURI());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }


    public abstract String getMongoURI();
}
