package uk.ac.ebi.pride.mongodb.molecules.repo.protein;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.mongodb.molecules.model.protein.PrideMongoProteinEvidence;

import java.util.Collection;
import java.util.List;

public interface PrideProteinMongoRepositoryCustom {


    /**
     * Filter By Attributes
     * @param filters Attributes in {@link Triple} structure
     * @param page Pageable
     * @return List of {@link PrideMongoProteinEvidence}
     */
    Page<PrideMongoProteinEvidence> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) ;

    /**
     * Find by A list of Accessions
     * @param accessions Accessions of the PSMs
     * @param sort Sort to order the output
     * @return Page
     */
    List<PrideMongoProteinEvidence> findByIdAccessions(Collection<String> accessions, Sort sort);
}
