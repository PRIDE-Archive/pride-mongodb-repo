package uk.ac.ebi.pride.mongodb.archive.service.stats;

import lombok.extern.log4j.Log4j;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.ebi.pride.mongodb.archive.model.stats.MongoPrideStats;
import uk.ac.ebi.pride.mongodb.archive.repo.stats.PrideStatsMongoRepository;

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
@Service
@Slf4j
public class PrideStatsMongoService {

    final PrideStatsMongoRepository repository;

    @Autowired
    public PrideStatsMongoService(PrideStatsMongoRepository repository) {
        this.repository = repository;
    }

    /**
     * This function save a stats in the Mongo Database, if the Stats exists in the database for the same date
     * then you Update function should be used.
     *
     * @param stats {@link MongoPrideStats}
     * @return MongoPrideProject
     */
    public Optional<MongoPrideStats> save(MongoPrideStats stats){
        if(!repository.findStatsByDate(stats.getEstimationDate()).isPresent()){
            stats = repository.save(stats);
            log.info("A new stats has been estimated and store by date -- " + stats.getEstimationDate());
        }else
            log.info("A stats has with the same date is in the database -- " + stats.getEstimationDate());
        return Optional.of(stats);
    }

    /**
     * This function save a stats in the Mongo Database, if the Stats exists in the database for the same date
     * then you Update function should be used.
     *
     * @param date Date of the corresponding report
     * @return MongoPrideProject
     */
    public Optional<MongoPrideStats> findStatsBydate(Date date){
        return repository.findStatsByDate(date);
    }

}
