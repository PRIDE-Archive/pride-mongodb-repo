package uk.ac.ebi.pride.mongodb.molecules.config;

import com.github.fakemongo.Fongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Test Configuration for Mongo PRIDE Projects using the fongo {@link Fongo} library.
 *
 * @author ypriverol
 */

@ComponentScan(basePackages = "uk.ac.ebi.pride.mongodb.molecules.service")
@EnableMongoRepositories(basePackages = {"uk.ac.ebi.pride.mongodb.molecules.repo"})
@Configuration
public class PrideProjectFongoTestConfig extends AbstractMongoClientConfiguration {

    @Override
    protected String getDatabaseName() {
        return "mongo-unit-test";
    }

//    @Bean
//    @Override
//    public MongoClient mongoClient() {
//        return new Fongo("mongo-test").getMongo();
//    }
}



