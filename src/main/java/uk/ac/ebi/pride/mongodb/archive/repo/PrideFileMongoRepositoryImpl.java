package uk.ac.ebi.pride.mongodb.archive.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.MultiValueMap;
import uk.ac.ebi.pride.mongodb.archive.model.PrideFile;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.List;

/**
 * @author ypriverol
 */
public class PrideFileMongoRepositoryImpl implements PrideFileMongoRepositoryCustom{

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    MongoOperations mongoOperations;

    @Override
    public Page<PrideFile> filterByAttributes(MultiValueMap<String, String> filters, Pageable page) {
        Criteria queryCriteria = PrideMongoUtils.buildQuery(filters);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        queryMongo.with(page);
        List<PrideFile> files =  mongoTemplate.find(queryMongo, PrideFile.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, PrideFile.class));
    }
}