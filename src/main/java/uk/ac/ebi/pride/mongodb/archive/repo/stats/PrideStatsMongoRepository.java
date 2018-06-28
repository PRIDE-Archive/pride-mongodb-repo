package uk.ac.ebi.pride.mongodb.archive.repo.stats;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideProject;
import uk.ac.ebi.pride.mongodb.archive.model.stats.MongoPrideStats;

import java.util.Date;
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
 * Created by ypriverol (ypriverol@gmail.com) on 27/06/2018.
 */
@Repository
public interface PrideStatsMongoRepository extends MongoRepository<MongoPrideStats, ObjectId> {

    @Query("{'"+ PrideArchiveField.STATS_ESTIMATION_DATE + "' : ?0}")
    public Optional<MongoPrideStats> findStatsByDate(Date date);

    public MongoPrideStats findTopByOrderByEstimationDateDesc();

}
