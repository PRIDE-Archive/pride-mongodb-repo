package uk.ac.ebi.pride.mongodb.archive.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.PrideFile;

import static org.junit.Assert.*;

/**
 * @author ypriverol
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectTestConfig.class})
public class PrideFileMongoServiceTest {

    @Autowired
    PrideFileMongoService prideFileMongoService;

    @Test
    public void save() {
        PrideFile file = PrideFile.builder().fileName("Filename.txt").build();
        prideFileMongoService.save(file);
    }
}