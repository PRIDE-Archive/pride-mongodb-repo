package uk.ac.ebi.pride.mongodb.archive.config;
//
//import com.github.fakemongo.Fongo;
//import com.mongodb.MongoClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
///**
// * Test Configuration for Mongo PRIDE Projects using the fongo {@link Fongo} library.
// *
// * @author ypriverol
// */
//
//@ComponentScan(basePackages = "uk.ac.ebi.pride.mongodb.archive.service")
//@EnableMongoRepositories(basePackages = {"uk.ac.ebi.pride.mongodb.archive.repo"})
//@Configuration
//public class PrideProjectFongoTestConfig extends AbstractMongoConfiguration{
//
//    @Override
//    protected String getDatabaseName() {
//        return "mongo-unit-test";
//    }
//
//    @Bean
//    @Override
//    public MongoClient mongoClient() {
//        return new Fongo("mongo-test").getMongo();
//    }
//}
//
//
//

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import de.flapdoodle.embed.mongo.distribution.Version;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.process.runtime.Network;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = "uk.ac.ebi.pride.mongodb.archive.service")
@EnableMongoRepositories(basePackages = {"uk.ac.ebi.pride.mongodb.archive.repo"})
@Configuration
public class PrideProjectFongoTestConfig {

    private MongodExecutable mongodExecutable;

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        MongodStarter starter = MongodStarter.getDefaultInstance();
        MongodConfig mongodConfig = MongodConfig.builder()
                .version(Version.Main.PRODUCTION)
                .net(new Net(27017, Network.localhostIsIPv6()))
                .build();

        mongodExecutable = starter.prepare(mongodConfig);
        mongodExecutable.start();

        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoClient, "test"));
    }
}
