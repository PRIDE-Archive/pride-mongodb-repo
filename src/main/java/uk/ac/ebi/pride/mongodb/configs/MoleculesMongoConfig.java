package uk.ac.ebi.pride.mongodb.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"uk.ac.ebi.pride.mongodb.molecules.service"})
@EnableMongoRepositories(basePackages = {"uk.ac.ebi.pride.mongodb.molecules.repo"}, mongoTemplateRef="moleculesMongoTemplate")
public class MoleculesMongoConfig extends AbstractPrideMongoConfiguration {

    @Value("${mongodb.molecules.database}")
    private String mongoProjectDatabase;

    @Value("${mongodb.molecules.machine.uri}")
    private String mongoURI;

    @Primary
    @Bean(name = "moleculesMongoTemplate")
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(super.mongoDbFactory());
    }

    @Override
    protected String getDatabaseName() {
        return mongoProjectDatabase;
    }

    @Override
    public String getMongoURI() {
        return mongoURI;
    }
}