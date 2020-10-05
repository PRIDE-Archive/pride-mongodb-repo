package uk.ac.ebi.pride.mongodb.molecules.repo.psm;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.mongodb.molecules.model.psm.PrideMongoPsmSummaryEvidence;

import java.util.List;
import java.util.Map;

/**
 * @author ypriverol
 */
public interface PridePsmSummaryEvidenceMongoRepositoryCustom {

    /**
     * Filter By Attributes
     *
     * @param filters Attributes in {@link Triple} structure
     * @param page    Pageable
     * @return List of {@link uk.ac.ebi.pride.mongodb.molecules.model.psm.PrideMongoPsmSummaryEvidence}
     */
    Page<PrideMongoPsmSummaryEvidence> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page);

    Page<PrideMongoPsmSummaryEvidence> filterByAttributes(Criteria criteria, Pageable page);

    Page<PrideMongoPsmSummaryEvidence> findPsmSummaryEvidencesByUsis(List<String> usis, Pageable page);

    long bulkupdatePsms(Map<ObjectId, String> map);
}
