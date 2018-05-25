package uk.ac.ebi.pride.mongodb.archive.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.utils.ProjectFileCategoryConstants;
import uk.ac.ebi.pride.data.exception.SubmissionFileException;
import uk.ac.ebi.pride.data.io.SubmissionFileParser;
import uk.ac.ebi.pride.data.model.DataFile;
import uk.ac.ebi.pride.data.model.Submission;
import uk.ac.ebi.pride.mongodb.archive.config.PrideProjectFongoTestConfig;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.service.projects.PrideFileMongoService;
import uk.ac.ebi.pride.mongodb.archive.service.projects.PrideProjectMongoService;
import uk.ac.ebi.pride.mongodb.archive.utils.TestUtils;
import uk.ac.ebi.pride.utilities.util.Triple;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ypriverol
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectFongoTestConfig.class})
public class PrideProjectMongoServiceTest {

    @Autowired
    PrideProjectMongoService prideProjectService;

    @Autowired
    PrideFileMongoService prideFileMongoService;

    @Test
    public void save() {

        /** Save Project using only an accession in the dataset **/
        MongoPrideProject project = MongoPrideProject.builder().accession("PXD000001").build();
        prideProjectService.save(project);
    }


    @Test
    public void importPrideProject() throws URISyntaxException, SubmissionFileException {
        File pxFile = new File(Objects.requireNonNull(PrideProjectMongoServiceTest.class.getClassLoader().getResource("pride-submission-three.px")).toURI());
        Submission pxSubmission = SubmissionFileParser.parse(pxFile);
        Optional<MongoPrideProject> project = prideProjectService.save(TestUtils.parseProject(pxSubmission));
        Assert.assertTrue(project.get().getAccession().equalsIgnoreCase("PXD000003"));

        List<DataFile> dataFiles = pxSubmission.getDataFiles();
        List<MongoPrideFile> mongoFiles = dataFiles.stream().map(dataFile ->
                MongoPrideFile.builder()
                        .fileName(dataFile.getFileName())
                        .fileCategory(ProjectFileCategoryConstants.findCategory(dataFile.getFileType().getName()).getCv())
                        .projectAccessions(Collections.singleton(project.get().getAccession()))
                .build()
        ).collect(Collectors.toList());

        mongoFiles = prideFileMongoService.insertAll(mongoFiles);
        Assert.assertTrue(mongoFiles.size() == dataFiles.size());


    }
}