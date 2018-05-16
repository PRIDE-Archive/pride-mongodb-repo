package uk.ac.ebi.pride.mongodb.archive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.PrideFile;
import uk.ac.ebi.pride.mongodb.archive.repo.PrideFileMongoRepository;

/**
 * This Service allows to handle the Project Repositories.
 *
 * @author ypriverol
 */
@Service
public class PrideFileMongoService {

    @Autowired
    PrideFileMongoRepository fileRepository;

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideFileMongoService.class);

    /**
     * Save a new Pride File into MongoDB
     * @param prideFile PrideFile
     * @return PrideFile
     */
    public PrideFile save(PrideFile prideFile){
        if(!fileRepository.findPrideFileByAccession(prideFile.getAccession()).isPresent()){
            prideFile = fileRepository.save(prideFile);
            LOGGER.info("A new project has been saved into MongoDB database with Accession -- " + prideFile.getAccession());
        }else
            LOGGER.info("A project with similar accession has been found in the MongoDB database, please use update function -- " + prideFile.getAccession());
        return prideFile;
    }



}
