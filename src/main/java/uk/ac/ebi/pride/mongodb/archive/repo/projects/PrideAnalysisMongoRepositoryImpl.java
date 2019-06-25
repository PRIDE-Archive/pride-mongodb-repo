package uk.ac.ebi.pride.mongodb.archive.repo.projects;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import uk.ac.ebi.pride.mongodb.archive.model.projects.MongoPrideAnalysis;
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
 * <p>
 * This class
 * <p>
 * Created by ypriverol (ypriverol@gmail.com) on 25/05/2018.
 */
public class PrideAnalysisMongoRepositoryImpl implements PrideAnalysisMongoRepositoryCustom{

    MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("archiveMongoTemplate")
    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }


    MongoOperations mongoOperations;

    @Autowired
    @Qualifier("archiveMongoTemplate")
    public void setMongoOperations(MongoTemplate mongoTemplate){
        this.mongoOperations = mongoTemplate;
    }

    @Override
    public Page<MongoPrideAnalysis> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) {
//        Criteria queryCriteria = PrideMongoUtils.buildCriteria(filters);
        Query queryMongo = PrideMongoUtils.buildQuery(filters);
        queryMongo.with(page);
        List<MongoPrideAnalysis> files =  mongoTemplate.find(queryMongo, MongoPrideAnalysis.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, MongoPrideAnalysis.class));
    }
}
