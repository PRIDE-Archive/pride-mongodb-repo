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
import uk.ac.ebi.pride.archive.repo.repos.project.Project;
import uk.ac.ebi.pride.archive.repo.repos.project.ProjectRepository;
import uk.ac.ebi.pride.mongodb.archive.config.ArchiveOracleConfig;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectFongoTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.service.projects.PrideFileMongoService;


import java.util.*;
import java.util.stream.IntStream;


/**
 * @author ypriverol
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectFongoTestConfig.class,  ArchiveOracleConfig.class})
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
    public void searchFilesTest(){
        insertFilesSave();
        String filterRaw = "fileCategory.value==RESULT";
        //noinspection deprecation
        Page<MongoPrideFile> pageFiles = prideFileMongoService.searchFiles(filterRaw, PageRequest.of(0, 10));
        System.out.println(pageFiles.getTotalElements());

        filterRaw = "fileCategory.value==RESULT,projectAccessions=all=PRD000001";
        pageFiles = prideFileMongoService.searchFiles(filterRaw, PageRequest.of(0, 10));
        System.out.println(pageFiles.getTotalElements());

        List<MongoPrideFile> projectFiles = prideFileMongoService.findFilesByProjectAccession("PRD000001");
        System.out.println(projectFiles.size());

        filterRaw = "publicationDate=range=[2005-01-01 TO 2016-12-31]";
        pageFiles = prideFileMongoService.searchFiles(filterRaw, PageRequest.of(0, 10));
        System.out.println(pageFiles.getTotalElements());

        // Delete all mongo Files
        prideFileMongoService.deleteAll();
        insertFilesInBatchSave();
        filterRaw = "fileCategory.value==RESULT";
        //noinspection deprecation
        pageFiles = prideFileMongoService.searchFiles(filterRaw, PageRequest.of(0, 10));
        System.out.println(pageFiles.getTotalElements());

        filterRaw = "fileCategory.value==RESULT,projectAccessions=all=PRD000001";
        pageFiles = prideFileMongoService.searchFiles(filterRaw, PageRequest.of(0, 10));
        System.out.println(pageFiles.getTotalElements());

        projectFiles = prideFileMongoService.findFilesByProjectAccession("PRD000001");
        System.out.println(projectFiles.size());

        filterRaw = "publicationDate=range=[2005-01-01 TO 2016-12-31]";
        pageFiles = prideFileMongoService.searchFiles(filterRaw, PageRequest.of(0, 10));
        System.out.println(pageFiles.getTotalElements());

    }


    /**
     * This method helps to read all the projects from PRIDE Archive Oracle Database and
     * move then into MongoDB
     */
    private void insertFilesSave(){

        Iterable<ProjectFile> iterator = oracleFileRepository.findAll();
        List<ProjectFile> files = new ArrayList<>();
        IntStream.range(0,400).forEach( x-> files.add(iterator.iterator().next()));
        files.stream().parallel().forEach(x-> {
            MSFileTypeConstants fileType = MSFileTypeConstants.OTHER;
            Set<String> projectAccessions = new HashSet<>();
            projectAccessions.add(oracleProjectRepository.findById(x.getProjectId()).get().getAccession());
            for(MSFileTypeConstants currentFileType: MSFileTypeConstants.values()){
                if(currentFileType.getFileType().getName().equalsIgnoreCase(x.getFileType().getName())){
                    fileType = currentFileType;
                }
            }
            MongoPrideFile file = MongoPrideFile.builder().fileName(x.getFileName()).fileCategory(fileType.getFileType().getCv()).projectAccessions(projectAccessions)
                    .fileSourceFolder(x.getFileSource().getFolderName())
                    .publicationDate(oracleProjectRepository.findById(x.getProjectId()).get().getPublicationDate())
                    .submissionDate(oracleProjectRepository.findById(x.getProjectId()).get().getSubmissionDate())
                    .build();
            prideFileMongoService.insert(file);
       }
       );

        Assert.assertEquals(401, prideFileMongoService.count());

    }

    /**
     * This method helps to read all the projects from PRIDE Archive Oracle Database and
     * move then into MongoDB
     */
    private void insertFilesInBatchSave(){

        Iterable<Project> iterator = oracleProjectRepository.findAll();
        List<Project> projectList = new ArrayList<>();
        IntStream.range(0,400).forEach( x-> projectList.add(iterator.iterator().next()));
        projectList.stream().parallel().forEach(project-> {

            List<ProjectFile> projectFiles = oracleFileRepository.findAllByProjectId(project.getId());
            List<MongoPrideFile> mongoPrideFiles = new ArrayList<>(projectFiles.size());
            projectFiles.stream().forEach(x -> {
                MSFileTypeConstants fileType = MSFileTypeConstants.OTHER;
                for(MSFileTypeConstants currentFileType: MSFileTypeConstants.values()){
                    if(currentFileType.getFileType().getName().equalsIgnoreCase(x.getFileType().getName())){
                        fileType = currentFileType;
                    }
                }
                MongoPrideFile file = MongoPrideFile.builder()
                        .fileName(x.getFileName())
                        .fileCategory(fileType.getFileType().getCv())
                        .projectAccessions(Collections.singleton(project.getAccession()))
                        .fileSourceFolder(x.getFileSource().getFolderName())
                        .publicationDate(project.getPublicationDate())
                        .submissionDate(project.getSubmissionDate())
                        .build();
                mongoPrideFiles.add(file);
            });
            prideFileMongoService.insertAll(mongoPrideFiles);
        });

        Assert.assertEquals(401, prideFileMongoService.count());

    }
}