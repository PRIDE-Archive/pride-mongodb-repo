package uk.ac.ebi.pride.mongodb.molecules.repo.peptide;

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
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideSummary;

import java.util.List;

public class PridePeptideSummaryMongoRepositoryImpl implements PridePeptideSummaryMongoRepositoryCustom {

    private MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("moleculesMongoTemplate")
    public void setMongoTemplate(MongoTemplate template) {
        this.mongoTemplate = template;
    }


    MongoOperations mongoOperations;

    @Autowired
    @Qualifier("moleculesMongoTemplate")
    public void setMongoOperations(MongoTemplate mongoTemplate) {
        this.mongoOperations = mongoTemplate;
    }


    @Override
    public Page<PrideMongoPeptideSummary> findUniprotPeptideSummaryByProperties(String keyword,
                                                                                Boolean includeUniprotOnly,
                                                                                Boolean isMultiorganismPeptide,
                                                                                Boolean isUniqPeptideWithinOrganism,
                                                                                Pageable page) {

        Criteria criteria = new Criteria();
        if (keyword != null && keyword.trim().length() > 0) {
            criteria = criteria.orOperator(
                    Criteria.where(PrideArchiveField.PEPTIDE_SEQUENCE).is(keyword),
                    Criteria.where(PrideArchiveField.PROTEIN_ACCESSION).is(keyword),
                    Criteria.where(PrideArchiveField.PROTEIN_NAME).is(keyword),
                    Criteria.where(PrideArchiveField.GENE).is(keyword));
        }
        if (includeUniprotOnly != null && includeUniprotOnly) {
            criteria = criteria.andOperator(Criteria.where(PrideArchiveField.IS_UNIPROT).is(true));
        }
        if (isMultiorganismPeptide != null && isMultiorganismPeptide) {
            criteria = criteria.andOperator(Criteria.where(PrideArchiveField.IS_MULTI_ORGANISM).is(true));
        }
        if (isUniqPeptideWithinOrganism != null && isUniqPeptideWithinOrganism) {
            criteria = criteria.andOperator(Criteria.where(PrideArchiveField.IS_UNIQUE).is(true));
        }
        Query query = Query.query(criteria).with(page);
        List<PrideMongoPeptideSummary> files = mongoTemplate.find(query, PrideMongoPeptideSummary.class);
        return PageableExecutionUtils.getPage(files, page, () -> mongoOperations.count(query,
                PrideMongoPeptideSummary.class));
    }
}
