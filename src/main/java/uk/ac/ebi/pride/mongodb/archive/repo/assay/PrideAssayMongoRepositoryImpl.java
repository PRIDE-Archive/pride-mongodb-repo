package uk.ac.ebi.pride.mongodb.archive.repo.assay;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import uk.ac.ebi.pride.mongodb.archive.model.assay.MongoPrideAssay;
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
public class PrideAssayMongoRepositoryImpl implements PrideAssayMongoRepositoryCustom {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public List<MongoPrideAssay> filterByAttributes(List<Triple<String, String, String>> filters) {
        Criteria queryCriteria = PrideMongoUtils.buildQuery(filters);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        return mongoTemplate.find(queryMongo, MongoPrideAssay.class);
    }
}
