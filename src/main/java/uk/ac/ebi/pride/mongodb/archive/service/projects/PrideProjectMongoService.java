package uk.ac.ebi.pride.mongodb.archive.service.projects;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.archive.dataprovider.param.DefaultCvParam;
import uk.ac.ebi.pride.mongodb.archive.model.param.MongoCvParam;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.repo.projects.PrideProjectMongoRepository;
import uk.ac.ebi.pride.utilities.util.Triple;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The {@link PrideProjectMongoService} is used to perform operations into Mongo Database for Pride Archive Projects.
 *
 * @author ypriverol
 */
@Service
@Slf4j
public class PrideProjectMongoService {

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
            log.info("A new project has been saved into MongoDB database with Accession -- " + project.getAccession());
        }else
            log.info("A project with similar accession has been found in the MongoDB database, please use update function -- " + project.getAccession());
        return Optional.of(project);
    }


    /**
     * This method add projectFiles with the corresponding type of relations defined as {@link CvParamProvider}.
     *
     * @param projectAccession project accession
     * @param projectFileRelations Project File relations
     * @return MongoPrideProject
     */
    public Optional<MongoPrideProject> updateFileRelations(String projectAccession, List<Triple<String, String, CvParamProvider>> projectFileRelations){
        Optional<MongoPrideProject> project = repository.findByAccession(projectAccession);
        List projectFiles = projectFileRelations.stream().map(x-> {
            return new Triple(x.getFirst(), x.getSecond(), new MongoCvParam(x.getThird().getCvLabel(), x.getThird().getAccession(), x.getThird().getName(), x.getThird().getValue()));
        }).collect(Collectors.toList());
        if(project.isPresent()){
            project.get().setSubmittedFileRelations(projectFiles);
            repository.save(project.get());
            log.info("Update the current project -- " + project.get().getAccession() + " with the File relations -- " + projectFileRelations);
        }else
            log.info("The requested project is not in the Database -- " + project.get().getAccession());
        return project;
    }


    /**
     * This method return the Pride Mongo Project by accession of the Project PX or PRD
     * @param accession PX accession
     * @return Optional
     */
    public Optional<MongoPrideProject> findByAccession(String accession){
        return repository.findByAccession(accession);
    }

    /**
     * Return al Page with all the MongoPrideProjects
     *
     * @param page Page
     * @return List of Mongo Pride Projects
     */
    public Page<MongoPrideProject> findAll(Pageable page){
        return repository.findAll(page);
    }

    /**
     * Return all the Projects from the database using Stream. It is important to know that
     * this method is only retrieving the Stream of the List returned
     * @return
     */
    public Stream<MongoPrideProject> findAllStream(){
        return repository.findAll().stream();
    }





}
