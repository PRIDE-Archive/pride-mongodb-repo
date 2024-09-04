package uk.ac.ebi.pride.mongodb.archive.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import uk.ac.ebi.pride.mongodb.configs.AbstractPrideMongoConfiguration;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 31/08/2018.
 */

@ComponentScan(basePackages = {"uk.ac.ebi.pride.mongodb.archive.service", "uk.ac.ebi.pride.utilities.ols.web.service.cache"})
@EnableMongoRepositories(basePackages = {"uk.ac.ebi.pride.mongodb.archive.repo"})
@Configuration
public class PrideMongoLocalhostConfig extends AbstractPrideMongoConfiguration {


    @Override
    @Bean(name = "archiveMongoTestTemplate")
    public MongoTemplate mongoTemplate() {
        return super.mongoTemplate();
    }

    @Override
    public String getMongoURI() {
        return "localhost" + ":" + "27017";
    }

    @Override
    public String getDatabaseName() {
        return "prideDB";
    }
}
