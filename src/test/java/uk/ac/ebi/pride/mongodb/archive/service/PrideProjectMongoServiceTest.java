package uk.ac.ebi.pride.mongodb.archive.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.service.projects.PrideProjectMongoService;

/**
 * @author ypriverol
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectTestConfig.class})
public class PrideProjectMongoServiceTest {

    @Autowired
    PrideProjectMongoService prideProjectService;

    @Test
    public void save() {

        /** Save Project using only an accession in the dataset **/
        MongoPrideProject project = MongoPrideProject.builder().accession("PXD000001").build();
        prideProjectService.save(project);
    }
}