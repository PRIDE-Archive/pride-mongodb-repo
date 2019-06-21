package uk.ac.ebi.pride.mongodb.molecules.repo.protein;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import uk.ac.ebi.pride.mongodb.molecules.model.protein.PrideMongoProteinEvidence;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.Collection;
import java.util.List;

public class PrideProteinMongoRepositoryImpl implements PrideProteinMongoRepositoryCustom{

    MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("moleculesMongoTemplate")
    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }


    MongoOperations mongoOperations;

    @Autowired
    @Qualifier("moleculesMongoTemplate")
    public void setMongoOperations(MongoTemplate mongoTemplate){
        this.mongoOperations = mongoTemplate;
    }

    @Override
    public Page<PrideMongoProteinEvidence> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) {
        Criteria queryCriteria = PrideMongoUtils.buildQuery(filters);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        queryMongo.with(page);
        List<PrideMongoProteinEvidence> files =  mongoTemplate.find(queryMongo, PrideMongoProteinEvidence.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, PrideMongoProteinEvidence.class));
    }

    @Override
    public List<PrideMongoProteinEvidence> findByIdAccessions(Collection<String> accessions, Sort sort) {
        Criteria queryCriteria = PrideMongoUtils.builQueryByAccessions(accessions);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        queryMongo.with(sort);
        return mongoTemplate.find(queryMongo, PrideMongoProteinEvidence.class);
    }

}
