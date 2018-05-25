package uk.ac.ebi.pride.mongodb.archive.service.projects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.repo.projects.PrideProjectMongoRepository;
import uk.ac.ebi.pride.utilities.util.Triple;

import java.util.List;
import java.util.Optional;

/**
 * The {@link PrideProjectMongoService} is used to perform operations into Mongo Database for Pride Archive Projects.
 *
 * @author ypriverol
 */
@Service
public class PrideProjectMongoService {

    /** Logger use to query and filter the data **/
    private static final Logger LOGGER = LoggerFactory.getLogger(PrideProjectMongoService.class);

    final PrideProjectMongoRepository repository;

    @Autowired
    public PrideProjectMongoService(PrideProjectMongoRepository repository) {
        this.repository = repository;
    }

    /**
     * This function save a project in the Mongo Database, if the project already exist in the database, the function will skip the function.
     *
     * @param project {@link MongoPrideProject}
     * @return MongoPrideProject
     */
    public Optional<MongoPrideProject> save(MongoPrideProject project){
        if(!repository.findByAccession(project.getAccession()).isPresent()){
            project = repository.save(project);
            LOGGER.info("A new project has been saved into MongoDB database with Accession -- " + project.getAccession());
        }else
            LOGGER.info("A project with similar accession has been found in the MongoDB database, please use update function -- " + project.getAccession());
        return Optional.of(project);
    }


    /**
     * This method add projectFiles with the corresponding type of relations defined as {@link CvParamProvider}.
     *
     * @param projectAccession project accession
     * @param projectFileRelations Project File relations
     * @return MongoPrideProject
     */
    public Optional<MongoPrideProject> updateFileRelations(String projectAccession, List<Triple<String, List<String>, CvParamProvider>> projectFileRelations){
        Optional<MongoPrideProject> project = repository.findByAccession(projectAccession);
        if(project.isPresent()){
            project.get().setSubmittedFileRelations(projectFileRelations);
            repository.save(project.get());
            LOGGER.info("Update the current project -- " + project.get().getAccession() + " with the File relations -- " + projectFileRelations);
        }else
            LOGGER.info("The requested project is not in the Database -- " + project.get().getAccession());
        return project;
    }





}
