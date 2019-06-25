package uk.ac.ebi.pride.mongodb.archive.repo.msruns;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.archive.model.msrun.MongoPrideMSRun;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author ypriverol
 */
public class PrideMSRunMongoRepositoryImpl implements PrideMSRunMongoRepositoryCustom {

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
    public Page<MongoPrideMSRun> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) {
//        Criteria queryCriteria = PrideMongoUtils.buildCriteria(filters);
        Query queryMongo = PrideMongoUtils.buildQuery(filters);
//        if(queryCriteria!=null){
//            queryMongo.addCriteria(queryCriteria);
//        }
        queryMongo.with(page);
        List<MongoPrideMSRun> files =  mongoTemplate.find(queryMongo, MongoPrideMSRun.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, MongoPrideMSRun.class));
    }

    @Override
    public List<MongoPrideMSRun> filterByAttributes(List<Triple<String, String, String>> filters) {
//        Criteria queryCriteria = PrideMongoUtils.buildCriteria(filters);
        Query queryMongo = PrideMongoUtils.buildQuery(filters);
        return mongoTemplate.find(queryMongo, MongoPrideMSRun.class);
    }

    @Override
    public List<MongoPrideMSRun> filterMSRunByProjectAccession(String projectAccession) {
        Criteria criteria = new Criteria(PrideArchiveField.EXTERNAL_PROJECT_ACCESSIONS).in(projectAccession);
        Query queryMongo = new Query().addCriteria(criteria);
        return mongoTemplate.find(queryMongo, MongoPrideMSRun.class);
    }

    @Override
    public List<MongoPrideMSRun> findByProjectAccessions(List<String> accessions) {
        Criteria criteria = new Criteria(PrideArchiveField.EXTERNAL_PROJECT_ACCESSIONS).in(accessions);
        Query queryMongo = new Query().addCriteria(criteria);
        return mongoTemplate.find(queryMongo, MongoPrideMSRun.class);
    }

    @Override
    public Optional<MongoPrideMSRun> findMsRunByAccession(String accession) {
        Criteria criteria = new Criteria(PrideArchiveField.ACCESSION).in(accession);
        Query queryMongo = new Query().addCriteria(criteria);
        MongoPrideMSRun result = mongoTemplate.findOne(queryMongo, MongoPrideMSRun.class);
        if(result != null)
            return Optional.of(result);
        return Optional.empty();
    }
}
