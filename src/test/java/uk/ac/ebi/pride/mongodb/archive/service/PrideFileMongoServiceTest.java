package uk.ac.ebi.pride.mongodb.archive.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.archive.dataprovider.utils.MSFileTypeConstants;
import uk.ac.ebi.pride.archive.repo.repos.file.ProjectFile;
import uk.ac.ebi.pride.archive.repo.repos.file.ProjectFileRepository;
import uk.ac.ebi.pride.mongodb.archive.config.ArchiveOracleConfig;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.MongoPrideFile;


import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.StreamSupport;


/**
 * @author ypriverol
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectTestConfig.class,  ArchiveOracleConfig.class})
public class PrideFileMongoServiceTest {

    @Autowired
    PrideFileMongoService prideFileMongoService;

    @Autowired
    ProjectFileRepository oracleRepository;

    @Test
    public void save() {
        MongoPrideFile file = MongoPrideFile.builder().fileName("Filename.txt").build();
        prideFileMongoService.insert(file);
    }

    @Test
    public void parallelSave() {
        insertFilesSave();
    }

    @Test
    public void searchFilesTest(){
        insertFilesSave();
        String filterRaw = "fileCategory.name==RAW";
        Page<MongoPrideFile> pageFiles = prideFileMongoService.searchFiles(filterRaw, new PageRequest(0, 10));
        System.out.println(pageFiles.getTotalElements());
    }


    /**
     * This method helps to read all the projects from PRIDE Archive Oracle Database and
     * move then into MongoDB
     */
    private void insertFilesSave(){

        Iterable<ProjectFile> iterator = oracleRepository.findAll();
        AtomicLong currentCount = new AtomicLong();
        StreamSupport.stream(iterator.spliterator(), true).parallel().forEach( x-> {
            MSFileTypeConstants fileType = MSFileTypeConstants.OTHER;
            for(MSFileTypeConstants currentFileType: MSFileTypeConstants.values()){
                if(currentFileType.getFileType().getName().equalsIgnoreCase(x.getFileType().getName())){
                    fileType = currentFileType;
                }
            }
            if(currentCount.intValue() < 2000){
                MongoPrideFile file = MongoPrideFile.builder().fileName(x.getFileName()).
                        fileCategory(fileType). build();
                prideFileMongoService.insert(file);
                currentCount.getAndIncrement();
                System.out.println(currentCount.toString());
            }
        });

        Assert.assertTrue(currentCount.intValue() == prideFileMongoService.count());

    }
}