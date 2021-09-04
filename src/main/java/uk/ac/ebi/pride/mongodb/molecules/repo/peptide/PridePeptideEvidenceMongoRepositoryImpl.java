package uk.ac.ebi.pride.mongodb.molecules.repo.peptide;

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
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideEvidence;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ypriverol
 */
public class PridePeptideEvidenceMongoRepositoryImpl implements PridePeptideEvidenceMongoRepositoryCustom {

    private final Criteria additionalAttributesCriteria = Criteria
            .where(PrideArchiveField.ADDITIONAL_ATTRIBUTES)
            .elemMatch(Criteria.where(PrideArchiveField.ACCESSION)
                    .is("PRIDE:0000511").and(PrideArchiveField.VALUE).is("true"));

    private final Criteria validatedCriteria = Criteria.where(PrideArchiveField.IS_VALIDATED).is(true);

    private MongoTemplate mongoTemplate;

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

    @Override
    public Set<String> findProteinAccessionByProjectAccessions(String projectAccession) {
        // { "$and" : [{ "projectAccession" : "PRD000016" }, { "$or" : [{ "isValid" : true }, { "additionalAttributes" : { "$elemMatch" : { "accession" : "PRIDE:0000511", "value" : "true" } } }] }] }
        Criteria criteria = new Criteria().andOperator(
                Criteria.where(PrideArchiveField.EXTERNAL_PROJECT_ACCESSION).is(projectAccession)
                ,new Criteria().orOperator(validatedCriteria, additionalAttributesCriteria)
        );

        Query query = Query.query(criteria);
        String proteinAccessionFld = PrideArchiveField.PROTEIN_ACCESSION;
        query.fields().include(proteinAccessionFld).exclude("_id");

        List<Map> strings = mongoTemplate.find(query, Map.class, PrideArchiveField.PRIDE_PEPTIDE_COLLECTION_NAME);

        return strings.stream().map(s -> (String)s.get(proteinAccessionFld)).collect(Collectors.toSet());
    }

    @Override
    public Set<String> findPeptideSequenceByProjectAccessions(String projectAccession) {
        // { "$and" : [{ "projectAccession" : "PRD000016" }, { "$or" : [{ "isValid" : true }, { "additionalAttributes" : { "$elemMatch" : { "accession" : "PRIDE:0000511", "value" : "true" } } }] }] }
        Criteria criteria = new Criteria().andOperator(
                Criteria.where(PrideArchiveField.EXTERNAL_PROJECT_ACCESSION).is(projectAccession)
                ,new Criteria().orOperator(validatedCriteria, additionalAttributesCriteria)
        );

        Query query = Query.query(criteria);
        String peptideSequenceFld = PrideArchiveField.PEPTIDE_SEQUENCE;
        query.fields().include(peptideSequenceFld).exclude("_id");

        List<Map> strings = mongoTemplate.find(query, Map.class, PrideArchiveField.PRIDE_PEPTIDE_COLLECTION_NAME);

        return strings.stream().map(s -> (String)s.get(peptideSequenceFld)).collect(Collectors.toSet());
    }

}
