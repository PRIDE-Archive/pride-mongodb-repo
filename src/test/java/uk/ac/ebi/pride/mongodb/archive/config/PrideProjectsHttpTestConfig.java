package uk.ac.ebi.pride.mongodb.archive.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import uk.ac.ebi.pride.mongodb.configs.AbstractPrideMongoConfiguration;

/**
 * @author ypriverol
 */
@ComponentScan(basePackages = "uk.ac.ebi.pride.mongodb.archive.service.projects")
@EnableMongoRepositories(basePackages = "uk.ac.ebi.pride.mongodb.archive.repo.projects")
@Configuration
public class PrideProjectsHttpTestConfig extends AbstractPrideMongoConfiguration {

    @Value("${mongodb.project.database}")
    private String mongoProjectDatabase;

    @Value("${mongodb.projects.machine.uri}")
    private String mongoURI;

    @Override
    public String getDatabaseName() {
        return mongoProjectDatabase;
    }

    @Override
    public String getMongoURI() {
        return mongoURI;
    }
}
