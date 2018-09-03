package uk.ac.ebi.pride.mongodb.archive.repo.files;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideFile;
import uk.ac.ebi.pride.mongodb.archive.model.files.MongoPrideMSRun;
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
    public Page<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) {
        Criteria queryCriteria = PrideMongoUtils.buildQuery(filters);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        queryMongo.with(page);
        List<MongoPrideFile> files =  mongoTemplate.find(queryMongo, MongoPrideFile.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, MongoPrideFile.class));
    }

    @Override
    public List<MongoPrideFile> filterByAttributes(List<Triple<String, String, String>> filters) {
        Criteria queryCriteria = PrideMongoUtils.buildQuery(filters);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        return mongoTemplate.find(queryMongo, MongoPrideFile.class);
    }

    @Override
    public List<MongoPrideMSRun> filterMSRunByProjectAccession(String projectAccession) {
        Criteria criteria = Criteria.where("_class").is(PrideArchiveField.MONGO_MSRUN_ALIAS);
        criteria = criteria.andOperator( Criteria.where(PrideArchiveField.EXTERNAL_PROJECT_ACCESSIONS).in(projectAccession));
        Query queryMongo = new Query().addCriteria(criteria);
        return mongoTemplate.find(queryMongo, MongoPrideMSRun.class);
    }
}
