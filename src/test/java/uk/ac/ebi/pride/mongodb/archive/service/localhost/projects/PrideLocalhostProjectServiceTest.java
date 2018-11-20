package uk.ac.ebi.pride.mongodb.archive.service.localhost.projects;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
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
import uk.ac.ebi.pride.mongodb.archive.config.PrideMongoLocalhostConfig;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideMSRun;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.service.files.PrideFileMongoService;
import uk.ac.ebi.pride.mongodb.archive.service.projects.PrideProjectMongoService;
import uk.ac.ebi.pride.mongodb.archive.utils.MSRunJson;
import uk.ac.ebi.pride.mongodb.archive.utils.TestUtils;
import uk.ac.ebi.pride.utilities.util.Triple;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.io.*;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * {@link PrideProjectMongoService} and {@link PrideFileMongoService} Units tests.
 *
 * @author ypriverol
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PrideMongoLocalhostConfig.class})
public class PrideLocalhostProjectServiceTest {

    @Autowired
    PrideProjectMongoService prideProjectService;

    @Autowired
    PrideFileMongoService prideFileMongoService;


    @Before
    public void setup(){
        prideProjectService.deleteAll();
        prideFileMongoService.deleteAll();
    }

    /**
     * Save Project using only an accession in the Project
     **/
    @Test
    public void save() {

        MongoPrideProject project = MongoPrideProject.builder()
                .accession("PXD000001")
                .build();
        prideProjectService.insert(project);
    }

    private Submission readSubmission() throws SubmissionFileException, URISyntaxException {
        File pxFile = new File(Objects.requireNonNull(PrideLocalhostProjectServiceTest.class.getClassLoader().getResource("pride-submission-three.px")).toURI());
        return SubmissionFileParser.parse(pxFile);
    }

    private MSRunJson readMSrunMetadata() throws URISyntaxException, IOException {
        ObjectMapper mapper = new ObjectMapper();

        File msRunFile = new File(Objects.requireNonNull(PrideLocalhostProjectServiceTest.class.getClassLoader().getResource("submissions/msrun/ms-run-metadata.json")).toURI());
        InputStream fileStream = new FileInputStream(msRunFile);
        return mapper.readValue(fileStream, MSRunJson.class);

    }

    /**
     * The import project from File Submission
     * @throws SubmissionFileException
     * @throws URISyntaxException
     */
    @Test
    public void importPrideProject() throws SubmissionFileException, URISyntaxException {
        Optional<MongoPrideProject> project = prideProjectService.insert(TestUtils.parseProject(readSubmission()));
        Assert.assertTrue(project.get()
                .getAccession().equalsIgnoreCase("PXD000003"));

    }


    /**
     * Import a {@link MongoPrideProject} and the corresponding files.
     * @throws SubmissionFileException Submission Exception can be read.
     * @throws URISyntaxException The file hasn't been found
     */
    @Test
    public void importPrideProjectWithFiles() throws SubmissionFileException, URISyntaxException {
        Submission pxSubmission = readSubmission();
        Optional<MongoPrideProject> project = prideProjectService.insert(TestUtils.parseProject(pxSubmission));


        List<DataFile> dataFiles = pxSubmission.getDataFiles();
        Optional<MongoPrideProject> finalProject = project;
        List<MongoPrideFile> mongoFiles = dataFiles.stream().map(dataFile -> {
            String accession = finalProject.get().getAccession();
            return MongoPrideFile.builder()
                    .fileName(dataFile.getFileName())
                    .fileCategory(new MongoCvParam(ProjectFileCategoryConstants
                            .findCategory(dataFile.getFileType()
                                     .getName()).getCv()))
                    .projectAccessions(Collections.singleton(accession))
                    .build();
        }).collect(Collectors.toList());

        List<Tuple<MongoPrideFile, MongoPrideFile>> filesInserted= prideFileMongoService.insertAll(mongoFiles);
        Assert.assertEquals(mongoFiles.size(), dataFiles.size());

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
        Assert.assertEquals(600, project.get().getSubmittedFileRelations().size());



    }

    @Test
    public void updateTypeOfRawMSRun() throws SubmissionFileException, URISyntaxException, IOException {
        Submission pxSubmission = readSubmission();
        Optional<MongoPrideProject> project = prideProjectService.insert(TestUtils.parseProject(pxSubmission));

        List<DataFile> dataFiles = pxSubmission.getDataFiles();
        Optional<MongoPrideProject> finalProject = project;
        List<MongoPrideFile> mongoFiles = dataFiles.stream().map(dataFile -> {
            String accession = finalProject.get().getAccession();
            return MongoPrideFile.builder()
                    .fileName(dataFile.getFileName())
                    .fileCategory(new MongoCvParam(ProjectFileCategoryConstants
                            .findCategory(dataFile.getFileType()
                                    .getName()).getCv()))
                    .projectAccessions(Collections.singleton(accession))
                    .build();
        }).collect(Collectors.toList());

        List<Tuple<MongoPrideFile, MongoPrideFile>> filesInserted= prideFileMongoService.insertAll(mongoFiles);
        Assert.assertEquals(mongoFiles.size(), dataFiles.size());

        MSRunJson msRunJson = readMSrunMetadata();

        for(MongoPrideFile prideFile: prideFileMongoService.findFilesByProjectAccession(project.get().getAccession())){
            if(prideFile.getFileCategory().getAccession().equalsIgnoreCase(ProjectFileCategoryConstants.RAW.getCv().getAccession())){
                MongoPrideMSRun msRun = new MongoPrideMSRun(prideFile);
                msRun.addFileProperties(Arrays.stream(msRunJson.getFileProperties())
                        .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toSet()));
                msRun.addInstrumentProperties(Arrays.stream(msRunJson.getInstrumentProperties())
                        .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toSet()));
                msRun.addMsData(Arrays.stream(msRunJson.getMsData())
                        .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toSet()));
                msRun.addScanSettings(Arrays.stream(msRunJson.getScanSeetings())
                        .map(x -> new MongoCvParam(x.getCvLabel(), x.getAccession(), x.getName(), x.getValue()))
                        .collect(Collectors.toSet()));

                prideFileMongoService.updateMSRun(msRun);
            }

        }

        List<MongoPrideMSRun> msRuns = prideFileMongoService.getMSRunsByProject(project.get().getAccession());
        Assert.assertEquals(150, msRuns.size());
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
            resultRelations.add(new Triple<>(insertedDataFileAccession, null, category.getCv()));
        else{
            for(DataFile file: dataFile.getFileMappings()){
                for(Tuple tuple: filesInserted){
                    MongoPrideFile fileToInsert = (MongoPrideFile) tuple.getKey();
                    MongoPrideFile fileInserted = (MongoPrideFile) tuple.getValue();
                    if(file.getFileName().equalsIgnoreCase(fileToInsert.getFileName())){
                        resultRelations.add(new Triple<>(insertedDataFileAccession, fileInserted.getAccession(), category.getCv()));
                    }
                }
            }
        }
        return resultRelations;
    }
}