package uk.ac.ebi.pride.mongodb.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configures the Mongo-based repositories. For details, see:
 * https://docs.spring.io/spring-data/mongodb/docs/2.1.0.BUILD-SNAPSHOT/reference/html/
 */

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {"uk.ac.ebi.pride.mongodb.archive.service", "uk.ac.ebi.pride.utilities.ols.web.service.cache"})
@EnableMongoRepositories(basePackages = {"uk.ac.ebi.pride.mongodb.archive.repo"}, mongoTemplateRef="archiveMongoTemplate")
public class ArchiveMongoConfig extends AbstractPrideMongoConfiguration {

  @Value("${mongodb.project.database}")
  private String mongoProjectDatabase;

  @Value("${mongodb.project.app.user}")
  private String user;

  @Value("${mongodb.project.app.password}")
  private String password;

  @Value("${mongodb.project.app.authenticationDatabase}")
  private String authenticationDatabse;

  @Value("${mongodb.projects.replicate.hosts}")
  private String mongoHosts;

  @Value("${mongodb.projects.replicate.ports}")
  private String mongoPorts;

  @Value("${mongodb.project.app.machine.port}")
  private String port;

  @Value("${mongo.single.machine}")
  private String singleMachine;

  @Value("${mongodb.projects.single.machine.host}")
  private String mongoHost;

  @Value("${mongodb.projects.machine.uri}")
  private String mongoURI;

  @Bean(name = "archiveMongoTemplate")
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
