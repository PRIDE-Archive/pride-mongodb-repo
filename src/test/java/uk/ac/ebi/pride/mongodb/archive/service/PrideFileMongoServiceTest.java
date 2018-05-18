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
import uk.ac.ebi.pride.archive.repo.repos.project.ProjectRepository;
import uk.ac.ebi.pride.mongodb.archive.config.ArchiveOracleConfig;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.MongoPrideFile;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;


/**
 * @author ypriverol
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectTestConfig.class,  ArchiveOracleConfig.class})
public class PrideFileMongoServiceTest {

    @Autowired
    PrideFileMongoService prideFileMongoService;

    @Autowired
    ProjectFileRepository oracleFileRepository;

    @Autowired
    ProjectRepository oracleProjectRepository;

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
        String filterRaw = "fileCategory.value==RESULT";
        Page<MongoPrideFile> pageFiles = prideFileMongoService.searchFiles(filterRaw, new PageRequest(0, 10));
        System.out.println(pageFiles.getTotalElements());

        filterRaw = "fileCategory.value==RESULT,projectAccessions=all=PRD000001";
        pageFiles = prideFileMongoService.searchFiles(filterRaw, new PageRequest(0, 10));
        System.out.println(pageFiles.getTotalElements());

        List<MongoPrideFile> projectFiles = prideFileMongoService.findFilesByProjectAccession("PRD000001");
        System.out.println(projectFiles.size());


    }


    /**
     * This method helps to read all the projects from PRIDE Archive Oracle Database and
     * move then into MongoDB
     */
    private void insertFilesSave(){

        Iterable<ProjectFile> iterator = oracleFileRepository.findAll();
        List<ProjectFile> files = new ArrayList<>();
        IntStream.range(0,100).forEach( x-> files.add(iterator.iterator().next()));
       files.stream().parallel().forEach(x-> {
            MSFileTypeConstants fileType = MSFileTypeConstants.OTHER;
            Set<String> projectAccessions = new HashSet<>();
            projectAccessions.add(oracleProjectRepository.findById(x.getProjectId()).get().getAccession());
            for(MSFileTypeConstants currentFileType: MSFileTypeConstants.values()){
                if(currentFileType.getFileType().getName().equalsIgnoreCase(x.getFileType().getName())){
                    fileType = currentFileType;
                }
            }
            MongoPrideFile file = MongoPrideFile.builder().fileName(x.getFileName()).fileCategory(fileType.getFileType().getCv()).projectAccessions(projectAccessions).build();
            prideFileMongoService.insert(file);
       }
       );

        Assert.assertEquals(100, prideFileMongoService.count());

    }
}