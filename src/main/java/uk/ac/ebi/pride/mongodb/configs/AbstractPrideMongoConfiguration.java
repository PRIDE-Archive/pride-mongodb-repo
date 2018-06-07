package uk.ac.ebi.pride.mongodb.configs;

import com.mongodb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.ArrayList;
import java.util.List;

/**
 * This Abstract class is used to Configure all the connections to Spring.
 * @author ypriverol
 */
public abstract class AbstractPrideMongoConfiguration extends AbstractMongoConfiguration {

    @Bean
    @Override
    public MongoClient mongoClient() {
        MongoCredential credential = MongoCredential.createCredential(getUser(), getAuthenticationDatabse(), getPassword().toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().build();
        MongoClient mongoClient;
        if(!getMongoURI().isEmpty())
            mongoClient = configureMachineFromURI(getMongoURI());
        else if(getSingleMachine().equalsIgnoreCase("true")){
            mongoClient = configureSingleMachine(credential, options);
        }else{
            mongoClient = configureReplicates(credential, options);
        }

        return mongoClient;
    }

    @Bean
    public MongoTemplate mongoTemplate(){

        return new MongoTemplate(mongoDbFactory());
    }

    @Bean
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

    /**
     * Create a mongo client using the replicates and credentials
     * @param credential Credentials
     * @param options Mongo Options
     * @return MongoClient
     */
    private MongoClient configureReplicates(MongoCredential credential, MongoClientOptions options) {
        String[] hosts = getMongoHosts().split(",");
        String[] ports = getMongoPorts().split(",");
        if (hosts.length != ports.length) {
            throw new IllegalArgumentException("The ports don't match the number of hosts for Mongo for Replica Set config. " + "Hosts: " + StringUtils.join(hosts, ',') + ", Ports: " + StringUtils.join(ports, ','));
        }
        List<ServerAddress> servers = new ArrayList<>();
        for (int i = 0; i < hosts.length; i++) {
            servers.add(new ServerAddress(hosts[i], Integer.parseInt(ports[i % ports.length])));
        }
        if (credential != null) {
            return new MongoClient(servers, credential, options);
        }
        return new MongoClient(servers,options);
    }


    /**
     * Create machine in single mode run, for example for localhost.
     * @param credential Credentials
     * @param options options
     * @return MongoClient.
     */
    private MongoClient configureSingleMachine(MongoCredential credential, MongoClientOptions options) {
        ServerAddress serverAddress = new ServerAddress(getMongoHost(), Integer.parseInt(getPort()));
        if(credential != null)
            return new MongoClient(serverAddress, credential, options);
        return new MongoClient(serverAddress, options);
    }

    public abstract String getUser();

    public abstract String getPassword();

    public abstract String getAuthenticationDatabse();

    public abstract String getMongoHosts();

    public abstract String getMongoPorts();

    public abstract String getPort() ;

    public abstract String getSingleMachine();

    public abstract String getMongoHost();

    public abstract String getMongoURI();
}
