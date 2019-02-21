package uk.ac.ebi.pride.mongodb.spectral.repo.psms;

import org.apache.commons.lang3.tuple.Triple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uk.ac.ebi.pride.mongodb.spectral.model.psms.PrideMongoPSM;


import java.util.Collection;
import java.util.List;

/**
 * @author ypriverol
 */
public interface PridePSMMongoRepositoryCustom {

    /**
     * Filter By Attributes
     * @param filters Attributes in {@link Triple} structure
     * @param page Pageable
     * @return List of {@link PrideMongoPSM}
     */
    Page<PrideMongoPSM> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) ;

    /**
     * Find by A list of Accessions
     * @param accessions Accessions of the PSMs
     * @param sort Sort to order the output
     * @return Page
     */
    List<PrideMongoPSM> findByIdAccessions(Collection<String> accessions, Sort sort);
}
