package uk.ac.ebi.pride.mongodb.archive.service.integration.projects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.utils.MSFileTypeConstants;
import uk.ac.ebi.pride.archive.repo.repos.file.ProjectFile;
import uk.ac.ebi.pride.archive.repo.repos.file.ProjectFileRepository;
import uk.ac.ebi.pride.archive.repo.repos.project.Project;
import uk.ac.ebi.pride.archive.repo.repos.project.ProjectRepository;
import uk.ac.ebi.pride.mongodb.archive.config.ArchiveOracleConfig;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectFongoTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.service.files.PrideFileMongoService;


import java.util.*;
import java.util.stream.IntStream;


/**
 * All these tests are integration tests. They need to be performed using some specific configurations in PRIDE Ecosystem. This particular test class needs
 * the following maven profiles :
 *     - oracle-pridedb-ro-user
 *     - oracle-pridedb-test-machine
 *     - mongodb-pridedb-localchost-machines
 *
 * @author ypriverol
 */
@SpringBootTest(classes = {PrideProjectFongoTestConfig.class,  ArchiveOracleConfig.class})
@TestPropertySource(locations = "classpath:application-test.properties")
public class PrideFileMongoServiceTest {

    @Autowired
    PrideFileMongoService prideFileMongoService;

    @Autowired
    ProjectFileRepository oracleFileRepository;

    @Autowired
    ProjectRepository oracleProjectRepository;

    @Disabled
    @Test
    public void save() {
        MongoPrideFile file = MongoPrideFile
                .builder()
                .fileName("Filename.txt").build();
        prideFileMongoService.insert(file);
    }

    @Test
    @Disabled
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
     * This method helps to read all the projects from PRIDE Archive Oracle Database and move then into MongoDB. This is
     * an integration Test. Some maven profiles needs to be selected.
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
            MongoPrideFile file = MongoPrideFile.builder()
                    .fileName(x.getFileName()).fileCategory(new CvParam(fileType.getFileType().getCv()))
                    .projectAccessions(projectAccessions)
                    .fileSourceFolder(x.getFileSource().getFolderName())
                    .publicationDate(oracleProjectRepository.findById(x.getProjectId()).get().getPublicationDate())
                    .submissionDate(oracleProjectRepository.findById(x.getProjectId()).get().getSubmissionDate())
                    .build();
            prideFileMongoService.insert(file);
       }
       );

        Assertions.assertEquals(400, prideFileMongoService.count());

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
            projectFiles.forEach(x -> {
                MSFileTypeConstants fileType = MSFileTypeConstants.OTHER;
                for(MSFileTypeConstants currentFileType: MSFileTypeConstants.values()){
                    if(currentFileType.getFileType().getName().equalsIgnoreCase(x.getFileType().getName())){
                        fileType = currentFileType;
                    }
                }
                MongoPrideFile file = MongoPrideFile.builder()
                        .fileName(x.getFileName())
                        .fileCategory(new CvParam(fileType.getFileType().getCv()))
                        .projectAccessions(Collections.singleton(project.getAccession()))
                        .fileSourceFolder(x.getFileSource().getFolderName())
                        .publicationDate(project.getPublicationDate())
                        .submissionDate(project.getSubmissionDate())
                        .build();
                mongoPrideFiles.add(file);
            });
            prideFileMongoService.insertAllFilesAndMsRuns(mongoPrideFiles,null);
        });

        Assertions.assertEquals(1200, prideFileMongoService.count());

    }
}