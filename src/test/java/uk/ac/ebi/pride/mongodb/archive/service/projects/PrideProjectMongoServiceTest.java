package uk.ac.ebi.pride.mongodb.archive.service.projects;

import org.junit.Assert;
import org.junit.Ignore;
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
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.utils.TestUtils;
import uk.ac.ebi.pride.utilities.util.Triple;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.io.File;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@link PrideProjectMongoService} and {@link PrideFileMongoService} Units tests.
 *
 * @author ypriverol
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideProjectFongoTestConfig.class})
public class PrideProjectMongoServiceTest {

    @Autowired
    PrideProjectMongoService prideProjectService;

    @Autowired
    PrideFileMongoService prideFileMongoService;

    /**
     * Save Project using only an accession in the Project
     **/
    @Test
    public void save() {

        MongoPrideProject project = MongoPrideProject.builder().accession("PXD000001").build();
        prideProjectService.save(project);
    }

    private Submission readSubmission() throws SubmissionFileException, URISyntaxException {
        File pxFile = new File(Objects.requireNonNull(PrideProjectMongoServiceTest.class.getClassLoader().getResource("pride-submission-three.px")).toURI());
        return SubmissionFileParser.parse(pxFile);
    }

    /**
     * The import project from File Submission
     * @throws SubmissionFileException
     * @throws URISyntaxException
     */
    @Test
    public void importPrideProject() throws SubmissionFileException, URISyntaxException {
        Optional<MongoPrideProject> project = prideProjectService.save(TestUtils.parseProject(readSubmission()));
        Assert.assertTrue(project.get().getAccession().equalsIgnoreCase("PXD000003"));

    }

    /**
     * This method helps to read all the projects from PRIDE Archive Oracle Database and move then into MongoDB. This is
     * an integration Test. Some maven profiles needs to be selected.
     */
    @Test
    @Ignore
    public void importPrideProjectWithFiles() throws SubmissionFileException, URISyntaxException {
        Submission pxSubmission = readSubmission();
        Optional<MongoPrideProject> project = prideProjectService.save(TestUtils.parseProject(pxSubmission));

        List<DataFile> dataFiles = pxSubmission.getDataFiles();
        Optional<MongoPrideProject> finalProject = project;
        List<MongoPrideFile> mongoFiles = dataFiles.stream().map(dataFile -> {
            String accession = finalProject.get().getAccession();
            return MongoPrideFile.builder()
                    .fileName(dataFile.getFileName())
                    .fileCategory(new MongoCvParam(ProjectFileCategoryConstants.findCategory(dataFile.getFileType().getName()).getCv()))
                    .projectAccessions(Collections.singleton(accession))
                    .build();
        }).collect(Collectors.toList());

        List<Tuple<MongoPrideFile, MongoPrideFile>> filesInserted= prideFileMongoService.insertAll(mongoFiles);
        Assert.assertTrue(mongoFiles.size() == dataFiles.size());

        List<Triple<String, String, CvParamProvider>> fileRelations = new ArrayList<>();
        for(DataFile dataFile: dataFiles){
            for(Tuple tuple: filesInserted){
                MongoPrideFile fileToInsert = (MongoPrideFile) tuple.getKey();
                MongoPrideFile fileInserted = (MongoPrideFile) tuple.getValue();
                if(dataFile.getFileName().equalsIgnoreCase(fileToInsert.getFileName())){
                    fileRelations.addAll(returnRelation(fileInserted.getAccession(), dataFile, filesInserted, ProjectFileCategoryConstants.findCategory(dataFile.getFileType().getName())));
                }
            }
        }

       project = prideProjectService.updateFileRelations(project.get().getAccession(),fileRelations);

        Assert.assertTrue(project.get().getSubmittedFileRelations().size() == 600);



    }

    /**
     * Return file realations from Submission PX
     * @param insertedDataFileAccession inserted file
     * @param dataFile data File in the Submission Core
     * @param filesInserted Inserted File
     * @param category Category of the file
     * @return List of {@link Triple}
     */
    private List<Triple<String, String, CvParamProvider>> returnRelation(String insertedDataFileAccession, DataFile dataFile, List<Tuple<MongoPrideFile, MongoPrideFile>> filesInserted, ProjectFileCategoryConstants category) {
        List<Triple<String, String, CvParamProvider>> resultRelations = new ArrayList<>();
        if(dataFile.getFileMappings() == null || dataFile.getFileMappings().isEmpty())
            resultRelations.add(new Triple<String, String, CvParamProvider>(insertedDataFileAccession, null, category.getCv()));
        else{
            for(DataFile file: dataFile.getFileMappings()){
                for(Tuple tuple: filesInserted){
                    MongoPrideFile fileToInsert = (MongoPrideFile) tuple.getKey();
                    MongoPrideFile fileInserted = (MongoPrideFile) tuple.getValue();
                    if(file.getFileName().equalsIgnoreCase(fileToInsert.getFileName())){
                        resultRelations.add(new Triple<String, String, CvParamProvider>(insertedDataFileAccession, fileInserted.getAccession(), category.getCv()));
                    }
                }
            }
        }
        return resultRelations;
    }
}