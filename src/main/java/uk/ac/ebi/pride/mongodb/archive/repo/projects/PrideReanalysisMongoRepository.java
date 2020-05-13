package uk.ac.ebi.pride.mongodb.archive.repo.projects;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.projects.ReanalysisProject;

import java.util.Optional;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 * <p>
 * This class
 * <p>
 * Created by Suresh Hewapathirana (sureshhewabi@gmail.com) on 12/06/2020.
 */
@Repository
public interface PrideReanalysisMongoRepository extends MongoRepository<ReanalysisProject, ObjectId> {

    @Query("{'" + PrideArchiveField.ACCESSION + "' : ?0}")
    Optional<ReanalysisProject> findByAccession(String accession);
}
