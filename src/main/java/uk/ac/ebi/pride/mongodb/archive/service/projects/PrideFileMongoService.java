package uk.ac.ebi.pride.mongodb.archive.service.projects;

import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.repo.projects.PrideFileMongoRepository;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * This Service allows to handle the Project Repositories.
 *
 * @author ypriverol
 */
@Service
public class PrideFileMongoService {

    final
    PrideFileMongoRepository fileRepository;

    @Autowired
    private MongoOperations mongo;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideFileMongoService.class);

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
        if (!fileRepository.findPrideFileByAccession(prideFile.getAccession()).isPresent()) {
            if (prideFile.getAccession() == null) {
                String accession = "PXF" + formatter.format(PrideMongoUtils.getNextSequence(mongo, PrideArchiveField.PRIDE_FILE_COLLECTION_NAME));
                prideFile.setAccession(accession);
            }
            prideFile = fileRepository.save(prideFile);
            LOGGER.debug("A new project has been saved into MongoDB database with Accession -- " + prideFile.getAccession());
        } else
            LOGGER.error("A project with similar accession has been found in the MongoDB database, please use update function -- " + prideFile.getAccession());
        return prideFile;
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
            LOGGER.info("The following MongoPrideFile -- " + prideFile.get().getAccession() + " has been updated with a new Project Accession -- " + projectAccessions);
            return true;
        }
        LOGGER.error("The following  MongoPrideFile is not in the database -- " + fileAccession);
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


}