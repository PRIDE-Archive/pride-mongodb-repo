package uk.ac.ebi.pride.mongodb.molecules.repo.psm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.molecules.model.psm.PrideMongoPsmSummaryEvidence;
import uk.ac.ebi.pride.mongodb.utils.PrideMongoUtils;

import java.util.Collection;
import java.util.List;

/**
 * @author ypriverol
 */
public class PridePsmSummaryEvidenceMongoRepositoryImpl implements PridePsmSummaryEvidenceMongoRepositoryCustom {

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
    public Page<PrideMongoPsmSummaryEvidence> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) {
        Query queryMongo = PrideMongoUtils.buildQuery(filters);
        queryMongo.with(page);
        List<PrideMongoPsmSummaryEvidence> files = mongoTemplate.find(queryMongo, PrideMongoPsmSummaryEvidence.class);
        return PageableExecutionUtils.getPage(files, page, () ->
                mongoOperations.count(queryMongo, PrideMongoPsmSummaryEvidence.class));
    }

    @Override
    public Page<PrideMongoPsmSummaryEvidence> filterByAttributes(Criteria criteria, Pageable page) {
        Query queryMongo = new Query();
        queryMongo.addCriteria(criteria);
        queryMongo.with(page);
        List<PrideMongoPsmSummaryEvidence> psms = mongoTemplate.find(queryMongo, PrideMongoPsmSummaryEvidence.class);
        return PageableExecutionUtils.getPage(psms, page, psms::size);
    }

//    @Override
//    public List<PrideMongoPsmSummaryEvidence> findByIdAccessions(Collection<String> accessions, Sort sort) {
//        Criteria queryCriteria = PrideMongoUtils.builQueryByAccessions(accessions);
//        Query queryMongo = new Query().addCriteria(queryCriteria);
//        queryMongo.with(sort);
//        return mongoTemplate.find(queryMongo, PrideMongoPsmSummaryEvidence.class);
//    }

    @Override
    public Page<PrideMongoPsmSummaryEvidence> findPsmSummaryEvidencesByUsis(List<String> usis, Pageable page) {

        Criteria queryCriteria = builQueryByUsis(usis);
        Query queryMongo = new Query().addCriteria(queryCriteria);
        queryMongo.with(page);
        List<PrideMongoPsmSummaryEvidence> files =  mongoTemplate.find(queryMongo, PrideMongoPsmSummaryEvidence.class);
        return PageableExecutionUtils.getPage(files, page, () ->
                mongoOperations.count(queryMongo, PrideMongoPsmSummaryEvidence.class));

    }

    /**
     * Search by Usis
     * @param usis List of usis
     * @return accessions.
     */
    public static Criteria builQueryByUsis(Collection<String> usis) {
        return new Criteria(PrideArchiveField.USI).in(usis);
    }



//    @Override
//    public List<String> findProteinAccessionByProjectAccessions(String projectAccession) {
//        Query query = Query.query(Criteria.where(PrideArchiveField.EXTERNAL_PROJECT_ACCESSION).is(projectAccession));
//
//        ArrayList<String> proteinAccessions = mongoTemplate.getCollection(PrideArchiveField.PRIDE_PSM_COLLECTION_NAME)
//                .distinct(PrideArchiveField.PROTEIN_ACCESSION, query.getQueryObject(), String.class)
//                .into(new ArrayList<>());
//        return proteinAccessions;
//    }
//
//    @Override
//    public List<String> findPeptideSequenceByProjectAccessions(String projectAccession) {
//        Query query = Query.query(Criteria.where(PrideArchiveField.EXTERNAL_PROJECT_ACCESSION).is(projectAccession));
//
//        ArrayList<String> peptideSequences = mongoTemplate.getCollection(PrideArchiveField.PRIDE_PEPTIDE_COLLECTION_NAME)
//                .distinct(PrideArchiveField.PEPTIDE_SEQUENCE, query.getQueryObject(), String.class)
//                .into(new ArrayList<>());
//        return peptideSequences;
//    }

}
