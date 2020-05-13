package uk.ac.ebi.pride.mongodb.archive.service.fongo.projects;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectFongoTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.projects.ReanalysisProject;
import uk.ac.ebi.pride.mongodb.archive.service.projects.PrideReanalysisMongoService;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author Suresh Hewapathirana
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectFongoTestConfig.class})
public class PrideReanalysisMongoServiceTest {

    final String accession = "PXD00001";

    @Autowired
    private PrideReanalysisMongoService service;


    @Test
    public void save() {
        insertDataset();
    }

    /**
     * Create dummy data and insert record
     */
    private void insertDataset(){
        Set<String> references = new HashSet<>();
        references.add("26251673");
        ReanalysisProject project = ReanalysisProject.
                builder()
                .accession(accession)
                .references(references)
                .build();
        service.insert(project);
    }

    /**
     * Test find by accession. First we insert a dummy data and then
     * query that accession from the service.
     */
    @Test
    public void findByAccession() {
        insertDataset();
        Optional<ReanalysisProject> findDatasets = service.findByAccession(accession);
        findDatasets.ifPresent(mongoPrideReanalysis -> System.out.println(mongoPrideReanalysis.getReferences().toString()));
        Assert.assertTrue(findDatasets.isPresent());
        Assert.assertEquals(findDatasets.get().getAccession(), accession);
    }
}