package uk.ac.ebi.pride.mongodb.archive.service.assays;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import uk.ac.ebi.pride.mongodb.archive.model.assay.MongoPrideAssay;
import uk.ac.ebi.pride.mongodb.archive.repo.assay.PrideAssayMongoRepository;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.List;


/**
 * This code is licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * ==Overview==
 *
 * @author ypriverol on 01/11/2018.
 */

public class PrideAssayMongoService {

    final PrideAssayMongoRepository assayRepository;

    @Autowired
    private MongoOperations mongo;

    @Autowired
    public PrideAssayMongoService(PrideAssayMongoRepository assayRepository) {
        this.assayRepository = assayRepository;
    }

    /**
     * Insert an {@link MongoPrideAssay} in to the PRIDE MongoDB
     * @param assay {@link MongoPrideAssay}
     * @return MongoPrideFile
     */
    public MongoPrideAssay insert(MongoPrideAssay assay) {
        return assayRepository.save(assay);
    }

    /**
     * Find by Project Accession the following Files.
     * @param accession Find Files by Project Accession
     * @return Return File List
     */
    public List<MongoPrideAssay> findAssayByProjectAccession(String accession){
        List<Triple<String, String, String>> filters = PrideMongoUtils.parseFilterParameters("projectAccessions=all=" + accession);
        return assayRepository.filterByAttributes(filters);
    }



}
