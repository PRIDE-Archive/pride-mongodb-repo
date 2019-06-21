package uk.ac.ebi.pride.mongodb.configs;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * This Abstract class is used to Configure all the connections to Spring.
 * @author ypriverol
 */
public abstract class AbstractPrideMongoConfiguration extends AbstractMongoConfiguration {

    @Override
    public MongoClient mongoClient() {
        MongoClient mongoClient = configureMachineFromURI(getMongoURI());
        return mongoClient;
    }

    @Override
    public MongoDbFactory mongoDbFactory(){
        return new SimpleMongoDbFactory(mongoClient(), getDatabaseName());
    }


    /**
     * This method create a connection from an URI
     * @param uri URI in String format
     * @return MongoClient
     */
    public MongoClient configureMachineFromURI(String uri){
        MongoClientURI clientURI = new MongoClientURI(uri);
        return new MongoClient(clientURI);
    }

    public abstract String getMongoURI();
}
