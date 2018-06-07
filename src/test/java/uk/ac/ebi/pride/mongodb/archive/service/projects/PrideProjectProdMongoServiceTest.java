package uk.ac.ebi.pride.mongodb.archive.service.projects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectsHttpTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 06/06/2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectsHttpTestConfig.class})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@TestPropertySource("classpath:application-test.properties")
public class PrideProjectProdMongoServiceTest {

    @Autowired
    PrideProjectMongoService prideProjectService;

    @Autowired
    PrideFileMongoService prideFileMongoService;

    /**
     * Save Project using only an accession in the Project
     **/
    @Test
    public void checkAvailabilityInMongo() {
        Page<MongoPrideProject> projects = prideProjectService.findAll(PageRequest.of(0, 10));
        Assert.assertTrue(projects.getTotalElements() == 10 );
    }

}