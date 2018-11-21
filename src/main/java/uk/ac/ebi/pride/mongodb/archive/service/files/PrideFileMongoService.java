package uk.ac.ebi.pride.mongodb.archive.service.files;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.repo.files.PrideFileMongoRepository;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;
import uk.ac.ebi.pride.utilities.util.Tuple;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

/**
 * This Service allows to handle the Project File Repositories.
 *
 * @author ypriverol
 */
@Service
@Slf4j
public class PrideFileMongoService {

    final
    PrideFileMongoRepository fileRepository;

    @Autowired
    private MongoOperations mongo;

    @Autowired
    public PrideFileMongoService(PrideFileMongoRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Insert is allowing using to create a Accession for the File and insert the actual File into MongoDB.
     * @param prideFile MongoPrideFile
     * @return MongoPrideFile
     */
    public MongoPrideFile insert(MongoPrideFile prideFile) {
        NumberFormat formatter = new DecimalFormat("00000000000");
        if (prideFile.getAccession() == null) {
            String accession = "PXF" + formatter.format(PrideMongoUtils.getNextSizedSequence(mongo, PrideArchiveField.PRIDE_FILE_COLLECTION_NAME, 1));
            prideFile.setAccession(accession);
            prideFile = fileRepository.save(prideFile);
            log.debug("A new project has been saved into MongoDB database with Accession -- " + prideFile.getAccession());
        } else
            log.error("A project with similar accession has been found in the MongoDB database, please use update function -- " + prideFile.getAccession());
        return prideFile;
    }


    /**
     * Insert is allowing using to create a File Accession for the File and insert the actual File into MongoDB. The method return a List of Tuples
     * where the key is the submitted File and the value the inserted File.
     *
     * @param prideFiles MongoPride File List
     * @return List of Tuple
     */
    public List<Tuple<MongoPrideFile,MongoPrideFile>> insertAll(List<MongoPrideFile> prideFiles) {
        NumberFormat formatter = new DecimalFormat("00000000000");
        List<MongoPrideFile> newFiles = new ArrayList<>();
        List<Tuple<MongoPrideFile, MongoPrideFile>> insertedFiles = new ArrayList<>();
        prideFiles.forEach(prideFile -> {
            if (prideFile.getAccession() == null)
                newFiles.add(prideFile);
            else{
                insertedFiles.add(new Tuple<>(prideFile, null));
                log.error("The current File has an Accession already, please use the update function -- " + prideFile.getAccession());

            }

        });
        if(!newFiles.isEmpty()){
            int finalNumber = PrideMongoUtils.getNextSizedSequence(mongo, PrideArchiveField.PRIDE_FILE_COLLECTION_NAME, newFiles.size()) + 1;
            for (MongoPrideFile file: newFiles){
                finalNumber--;
                String accession = "PXF" + formatter.format(finalNumber);
                file.setAccession(accession);
                insertedFiles.add(new Tuple<>(file, fileRepository.save(file)));
                log.debug("A new project has been saved into MongoDB database with Accession -- " + accession);
            }
        }
        return insertedFiles;
    }

    /**
     * Number of Files in the Mongo Repository.
     * @return Number of Files in the MongoDB database
     */
    public long count() {
        return fileRepository.count();
    }

    /**
     * The current function add the following Project accession To the file Accession in the database. If the file is updated in the database
     * the function return true, if the file can't be updated in the database.
     *
     * @param fileAccession File Accession
     * @param projectAccessions Project Archive Accession
     * @return True if the File can be updated.
     */
    public boolean addProjectAccessions(String fileAccession, List<String> projectAccessions){
        Optional<MongoPrideFile> prideFile = fileRepository.findPrideFileByAccession(fileAccession);
        if(prideFile.isPresent()) {
            Set<String> currentProjectAccesions = prideFile.get().getProjectAccessions();
            if (currentProjectAccesions == null)
                currentProjectAccesions = new HashSet<>();
            currentProjectAccesions.addAll(projectAccessions);
            prideFile.get().setProjectAccessions(currentProjectAccesions);
            fileRepository.save(prideFile.get());
            log.info("The following MongoPrideFile -- " + prideFile.get().getAccession() + " has been updated with a new Project Accession -- " + projectAccessions);
            return true;
        }
        log.error("The following  MongoPrideFile is not in the database -- " + fileAccession);
        return false;
    }


    /**
     * The current function add the following Project accession To the file Accession in the database. If the file is updated in the database
     * the function return true, if the file can't be updated in the database.
     *
     * @param fileAccession File Accession
     * @param analysisAccessions Project Archive Accession
     * @return True if the File can be updated.
     */
    public boolean addAnalysisAccessions(String fileAccession, List<String> analysisAccessions){
        Optional<MongoPrideFile> prideFile = fileRepository.findPrideFileByAccession(fileAccession);
        if(prideFile.isPresent()) {
            Set<String> currentAnalysisAccesions = prideFile.get().getAnalysisAccessions();
            if (currentAnalysisAccesions == null)
                currentAnalysisAccesions = new HashSet<>();
            currentAnalysisAccesions.addAll(analysisAccessions);
            prideFile.get().setProjectAccessions(currentAnalysisAccesions);
            fileRepository.save(prideFile.get());
            log.info("The following MongoPrideFile -- " + prideFile.get().getAccession() + " has been updated with a new Analysis Accession -- " + analysisAccessions);
            return true;
        }
        log.error("The following  MongoPrideFile is not in the database -- " + fileAccession);
        return false;
    }


    /**
     * This method provides a way to search Files by different properties. The search Allows only to Filter the File using different properties. in the Ffile
     * the structure of the filter is the following:
     * property: propertyValue, property2: propertyValue2
     * @param filterQuery Filter query.
     * @param page Page to retrieve the Files.
     * @return Page containing all the files.
     */
    public Page<MongoPrideFile> searchFiles(String filterQuery, Pageable page){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters(filterQuery);
        return fileRepository.filterByAttributes(filters, page);

    }

    /**
     * Find by Project Accession the following Files.
     * @param accession Find Files by Project Accession
     * @return Return File List
     */
    public List<MongoPrideFile> findFilesByProjectAccession(String accession){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("projectAccessions=all=" + accession);
        return fileRepository.filterByAttributes(filters);
    }

    /**
     * Find by Project Accession the following Files.
     * @param accession Find Files by Project Accession
     * @return Return File List
     */
    public Page<MongoPrideFile> findFilesByProjectAccessionAndFiler(String accession, String filterQuery, Pageable page){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("projectAccessions=all=" + accession, filterQuery);
        return fileRepository.filterByAttributes(filters, page);
    }

    /**
     * Find a PRIDE File by the accession of the File
     * @param fileAccession File accession
     * @return Optional
     */
    public Optional<MongoPrideFile> findByFileAccession(String fileAccession){
        return fileRepository.findPrideFileByAccession(fileAccession);
    }

    /**
     * Get all the files from PRIDE Archive
     * @param page Pageable
     * @return Page with all the Files
     */
    public Page<MongoPrideFile> findAll(Pageable page){
        return fileRepository.findAll(page);
    }

    /**
     * Delete all Files
     */
    public void deleteAll(){
        fileRepository.deleteAll();
    }


    /**
     * Get the list of Files by {@link uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject} Accessions
     * @param accessions
     * @return List of Files
     */
    public List<MongoPrideFile> findFilesByProjectAccessions(List<String> accessions) {
        return fileRepository.findByProjectAccessions(accessions);
    }

}
