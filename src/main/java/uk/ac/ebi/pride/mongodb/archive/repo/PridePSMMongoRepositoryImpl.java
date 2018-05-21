package uk.ac.ebi.pride.mongodb.archive.repo;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import uk.ac.ebi.pride.mongodb.archive.model.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.PrideMongoPSM;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.List;

/**
 * @author ypriverol
 */
public class PridePSMMongoRepositoryImpl implements PridePSMMongoRepositoryCustom{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public Page<PrideMongoPSM> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) {
        Criteria queryCriteria = PrideMongoUtils.buildQuery(filters);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        queryMongo.with(page);
        List<PrideMongoPSM> files =  mongoTemplate.find(queryMongo, PrideMongoPSM.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, PrideMongoPSM.class));
    }
}
