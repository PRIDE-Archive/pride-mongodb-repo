package uk.ac.ebi.pride.mongodb.archive.service.projects;

import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideReanalysisProject;
import uk.ac.ebi.pride.mongodb.archive.repo.projects.PrideReanalysisMongoRepository;

import java.util.Optional;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 *
 * @author Suresh Hewapathirana
 */
@Service
@Slf4j
public class PrideReanalysisMongoService {

    final PrideReanalysisMongoRepository repository;

    public PrideReanalysisMongoService(PrideReanalysisMongoRepository repository) {
        this.repository = repository;
    }

    /**
     * This function insert a reanalysis project in the Mongo Database. If the project already exist in the database, the function will skip the function.
     *
     * @param project {@link MongoPrideReanalysisProject}
     * @return MongoPrideReanalysisProject
     */
    public Optional<MongoPrideReanalysisProject> insert(MongoPrideReanalysisProject project) {
        if (!repository.findByAccession(project.getAccession()).isPresent()) {
            project = repository.save(project);
            log.info("A new reanalysis project has been saved into MongoDB database with Accession -- " + project.getAccession());
        } else
            log.info("A reanalysis  project with similar accession has been found in the MongoDB database, please use update function -- " + project.getAccession());
        return Optional.of(project);
    }

    /**
     * This function update a reanalysis project in the Mongo Database. if the project is not already exist in the database, the function will skip the function.
     *
     * @param project {@link MongoPrideReanalysisProject}
     * @return MongoPrideReanalysisProject
     */
    public Optional<MongoPrideReanalysisProject> update(MongoPrideReanalysisProject project) {
        if (repository.findByAccession(project.getAccession()).isPresent()) {
            project = repository.save(project);
            log.info("project has been updated in MongoDB with accession -- " + project.getAccession());
        } else
            log.info("The project do not exists in the database the insert function should be used -- " + project.getAccession());
        return Optional.of(project);
    }


    /**
     * This function insert a renalysis project in the Mongo Database. If the project already exist in the database, the function will update the record
     *
     * @param project {@link MongoPrideReanalysisProject}
     * @return MongoPrideReanalysisProject
     */
    public Optional<MongoPrideReanalysisProject> upsert(MongoPrideReanalysisProject project) {
        Optional<MongoPrideReanalysisProject> optionalProject = repository.findByAccession(project.getAccession());
        if(optionalProject.isPresent()){
            project.setId((ObjectId) optionalProject.get().getId());
        }
        project = repository.save(project);
        log.info("Reanalysis project has been Inserted or updated in MongoDB with accession -- " + project.getAccession());
        return Optional.of(project);
    }


    /**
     * This method return the Reanalysis references by accession of the Project PX or PRD
     *
     * @param accession PX accession
     * @return Optional
     */
    public Optional<MongoPrideReanalysisProject> findByAccession(String accession) {
        return repository.findByAccession(accession);
    }

}
