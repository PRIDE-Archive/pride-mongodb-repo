package uk.ac.ebi.pride.mongodb.molecules.repo.peptide;

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
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideEvidence;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author ypriverol
 */
public class PridePeptideEvidenceMongoRepositoryImpl implements PridePeptideEvidenceMongoRepositoryCustom {

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
    public Page<PrideMongoPeptideEvidence> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) {
        Query queryMongo = PrideMongoUtils.buildQuery(filters);
        queryMongo.with(page);
            List<PrideMongoPeptideEvidence> files =  mongoTemplate.find(queryMongo, PrideMongoPeptideEvidence.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, PrideMongoPeptideEvidence.class));
    }

    @Override
    public List<PrideMongoPeptideEvidence> findByIdAccessions(Collection<String> accessions, Sort sort) {
        Criteria queryCriteria = PrideMongoUtils.builQueryByAccessions(accessions);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        queryMongo.with(sort);
        return mongoTemplate.find(queryMongo, PrideMongoPeptideEvidence.class);
    }

    @Override
    public Page<PrideMongoPeptideEvidence> findPeptideEvidenceByProteinEvidence(String projectAccession, String assayAccession, String reportedProtein, Pageable page) {

        Criteria criteria = new Criteria(PrideArchiveField.EXTERNAL_PROJECT_ACCESSION).is(projectAccession)
                .and(PrideArchiveField.PROTEIN_ASSAY_ACCESSION).is(assayAccession).and(PrideArchiveField.REPORTED_PROTEIN_ACCESSION).is(reportedProtein);
        Query queryMongo = new Query().addCriteria(criteria);
        queryMongo.with(page);
        List<PrideMongoPeptideEvidence> files =  mongoTemplate.find(queryMongo, PrideMongoPeptideEvidence.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(queryMongo, PrideMongoPeptideEvidence.class));

    }
}
