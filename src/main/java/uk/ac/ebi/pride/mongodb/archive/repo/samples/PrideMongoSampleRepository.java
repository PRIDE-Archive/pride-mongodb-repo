package uk.ac.ebi.pride.mongodb.archive.repo.samples;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.sample.MongoPrideExperimentalDesign;

/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 29/10/2018.
 */
@Repository
public interface PrideMongoSampleRepository extends MongoRepository<MongoPrideExperimentalDesign, ObjectId> {

    @Query("{'"+ PrideArchiveField.ACCESSION + "' : ?0}")
    MongoPrideExperimentalDesign findByProjectAccession(String accession);

}
