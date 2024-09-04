package uk.ac.ebi.pride.mongodb.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configures the Mongo-based repositories. For details, see:
 * https://docs.spring.io/spring-data/mongodb/docs/2.1.0.BUILD-SNAPSHOT/reference/html/
 */

@Configuration
@EnableTransactionManagement
@EnableMongoRepositories(basePackages = {"uk.ac.ebi.pride.mongodb.archive.repo"})
public class ArchiveMongoConfig extends AbstractPrideMongoConfiguration {

  @Value("${mongodb.project.database}")
  private String mongoProjectDatabase;

  @Value("${mongodb.projects.machine.uri}")
  private String mongoURI;

  @Primary
  @Bean
  @Qualifier("archiveMongoTemplate")
  public MongoTemplate mongoTemplate() {
    return super.mongoTemplate();
  }

  @Override
  public String getDatabaseName() {
    return mongoProjectDatabase;
  }

  @Override
  public String getMongoURI() {
        return mongoURI;
    }
}
