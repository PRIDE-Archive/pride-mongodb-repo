package uk.ac.ebi.pride.mongodb.archive.config;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Test Configuration for Mongo PRIDE Projects using the fongo {@link Fongo} library.
 *
 * @author ypriverol
 */

@ComponentScan(basePackages = "uk.ac.ebi.pride.mongodb.archive.service")
@EnableMongoRepositories(basePackages = {"uk.ac.ebi.pride.mongodb.archive.repo"})
@Configuration
public class PrideProjectFongoTestConfig extends AbstractMongoConfiguration{

    @Override
    protected String getDatabaseName() {
        return "mongo-unit-test";
    }

    @Bean
    @Override
    public MongoClient mongoClient() {
        return new Fongo("mongo-test").getMongo();
    }
}



