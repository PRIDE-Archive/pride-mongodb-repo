package uk.ac.ebi.pride.mongodb.archive.service.projects;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParam;
import uk.ac.ebi.pride.archive.dataprovider.param.CvParamProvider;
import uk.ac.ebi.pride.mongodb.archive.model.assay.MongoPrideAssay;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.repo.assay.PrideAssayMongoRepository;
import uk.ac.ebi.pride.mongodb.archive.repo.projects.PrideProjectMongoRepository;

import java.util.Arrays;
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
    final PrideAssayMongoRepository assayMongoRepository;

    @Autowired
    public PrideProjectMongoService(PrideProjectMongoRepository repository, PrideAssayMongoRepository assayMongoRepository) {
        this.repository = repository;
        this.assayMongoRepository = assayMongoRepository;
    }

    /**
     * This function insert a project in the Mongo Database, if the project already exist in the database, the function will skip the function.
     *
     * @param project {@link MongoPrideProject}
     * @return MongoPrideProject
     */
    public Optional<MongoPrideProject> insert(MongoPrideProject project) {
        if (!repository.findByAccession(project.getAccession()).isPresent()) {
            project = repository.save(project);
            log.info("A new project has been saved into MongoDB database with Accession -- " + project.getAccession());
        } else
            log.info("A project with similar accession has been found in the MongoDB database, please use update function -- " + project.getAccession());
        return Optional.of(project);
    }

    /**
     * This function insert a project in the Mongo Database, if the project already exist in the database, the function will skip the function.
     *
     * @param project {@link MongoPrideProject}
     * @return MongoPrideProject
     */
    public Optional<MongoPrideProject> update(MongoPrideProject project) {
        if (repository.findByAccession(project.getAccession()).isPresent()) {
            project = repository.save(project);
            log.info("project has been updated in MongoDB with accession -- " + project.getAccession());
        } else
            log.info("The project do not exists in the database the insert function should be used -- " + project.getAccession());
        return Optional.of(project);
    }


    /**
     * This function insert a project in the Mongo Database, if the project already exist in the database, the function will update the record
     *
     * @param project {@link MongoPrideProject}
     * @return MongoPrideProject
     */
    public Optional<MongoPrideProject> upsert(MongoPrideProject project) {
        Optional<MongoPrideProject> optionalProject = repository.findByAccession(project.getAccession());
        if(optionalProject.isPresent()){
            project.setId((ObjectId) optionalProject.get().getId());
        }
            project = repository.save(project);
            log.info("project has been Inserted or updated in MongoDB with accession -- " + project.getAccession());
        return Optional.of(project);
    }


    /**
     * This method add projectFiles with the corresponding type of relations defined as {@link CvParamProvider}.
     *
     * @param projectAccession     project accession
     * @param projectFileRelations Project File relations
     * @return MongoPrideProject
     */
    public Optional<MongoPrideProject> updateFileRelations(String projectAccession, List<Triple<String, String, CvParamProvider>> projectFileRelations) {
        Optional<MongoPrideProject> project = repository.findByAccession(projectAccession);
        List projectFiles = projectFileRelations.stream()
                .map(x -> new Triple(x.getFirst(), x.getSecond(), new CvParam(x.getThird().getCvLabel(),
                        x.getThird().getAccession(), x.getThird().getName(), x.getThird().getValue())))
                .collect(Collectors.toList());
        if (project.isPresent()) {
            project.get().setSubmittedFileRelations(projectFiles);
            repository.save(project.get());
            log.info("Update the current project -- " + project.get().getAccession() + " with the File relations -- " + projectFileRelations);
        } else
            log.info("The requested project is not in the Database -- " + project.get().getAccession());
        return project;
    }


    /**
     * This method return the Pride Mongo Project by accession of the Project PX or PRD
     *
     * @param accession PX accession
     * @return Optional
     */
    public Optional<MongoPrideProject> findByAccession(String accession) {
        return repository.findByAccession(accession);
    }

    public List<MongoPrideProject> findByMultipleAccessions(List<String> accessions) {
        return repository.findByMultipleAccessions(accessions);
    }

    public List<String> getAllProjectAccessions() {
        return repository.getAllProjectAccessions();
    }

    /**
     * Return al Page with all the MongoPrideProjects
     *
     * @param page Page
     * @return List of Mongo Pride Projects
     */
    public Page<MongoPrideProject> findAll(Pageable page) {
        return repository.findAll(page);
    }

    /*
     sample_attributes = species
     ptmList = modifications
    */
    //TODO : this code is not used for now. But it might be useful for future
    public Page<MongoPrideProject> findByMultipleAttributes(Pageable page, String accessionsStr, String speciesStr,
                                                            String instrumentsStr, String contact,
                                                            String modifications, String publicationsStr,
                                                            String keywordsStr) {
        String[] accessions = null;
        String[] species = null;
        String[] instruments = null;
        String[] publications = null;
        String[] keywords = null;

        if (accessionsStr != null) {
            accessions = Arrays.stream(accessionsStr.split(",")).map(String::trim).toArray(String[]::new);
        }
        if (speciesStr != null) {
            species = Arrays.stream(speciesStr.split(",")).map(String::trim).toArray(String[]::new);
        }
        if (instrumentsStr != null) {
            instruments = Arrays.stream(instrumentsStr.split(",")).map(String::trim).toArray(String[]::new);
        }
        if (publicationsStr != null) {
            publications = Arrays.stream(publicationsStr.split(",")).map(String::trim).toArray(String[]::new);
        }
        if (keywordsStr != null) {
            keywords = Arrays.stream(keywordsStr.split(",")).map(String::trim).toArray(String[]::new);
        }

        return repository.findByMultipleAttributes(page, accessions, species,
                instruments, contact, modifications, publications, keywords);
    }

    /**
     * Return all the Projects from the database using Stream. It is important to know that
     * this method is only retrieving the Stream of the List returned
     *
     * @return
     */
    public Stream<MongoPrideProject> findAllStream() {
        return repository.findAll().stream();
    }


    public void deleteAll() {
        repository.deleteAll();
    }

    public boolean deleteByAccession(String accession) {
        Optional<MongoPrideProject> project = repository.findByAccession(accession);
        if (project.isPresent()) {
            repository.delete(project.get());
            return true;
        }

        return false;
    }

    public void saveAssays(List<MongoPrideAssay> mongoAssays) {
        mongoAssays.forEach(x -> {
            Optional<MongoPrideAssay> currentAssay = assayMongoRepository.findPrideAssayByAccession(x.getAccession());
            if (!currentAssay.isPresent())
                assayMongoRepository.save(x);
            else {
                updateAssay(currentAssay.get(), x);
                log.info("The request assay is already in MongoDB, it will be updated -- " + x.getAccession());
            }
        });

    }

    public MongoPrideAssay updateAssay(MongoPrideAssay currentAssay, MongoPrideAssay newAssay) {
        newAssay.setId(currentAssay.getId());
        assayMongoRepository.save(newAssay);
        return newAssay;
    }

    public MongoPrideAssay updateAssay(MongoPrideAssay newAssay) {
        Optional<MongoPrideAssay> currentAssay = assayMongoRepository.findPrideAssayByAccession(newAssay.getAccession());
        if (currentAssay.isPresent()) {
            updateAssay(currentAssay.get(), newAssay);
            log.info("The request assay is already in MongoDB, it will be updated -- " + newAssay.getAccession());
        }
        return newAssay;
    }


    public Optional<MongoPrideAssay> findAssayByAccession(String assayAccession) {
        return assayMongoRepository.findPrideAssayByAccession(assayAccession);
    }
}
