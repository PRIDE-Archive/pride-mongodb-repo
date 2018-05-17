package uk.ac.ebi.pride.mongodb.archive.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.repo.PrideProjectMongoRepository;

/**
 * The {@link PrideProjectMongoService} is used to perform operations into Mongo Database for Pride Archive Projects.
 *
 * @author ypriverol
 */
@Service
public class PrideProjectMongoService {

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideProjectMongoService.class);

    @Autowired
    PrideProjectMongoRepository repository;

    /**
     * This function save a project in the Mongo Database, if the project already exist in the database, the function will skip the function.
     *
     * @param project {@link MongoPrideProject}
     * @return MongoPrideProject
     */
    public MongoPrideProject save(MongoPrideProject project){
        if(!repository.findByAccession(project.getAccession()).isPresent()){
            project = repository.save(project);
            LOGGER.info("A new project has been saved into MongoDB database with Accession -- " + project.getAccession());
        }else
            LOGGER.info("A project with similar accession has been found in the MongoDB database, please use update function -- " + project.getAccession());
        return project;
    }


}
