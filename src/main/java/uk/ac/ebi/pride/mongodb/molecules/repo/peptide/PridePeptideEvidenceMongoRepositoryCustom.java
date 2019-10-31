package uk.ac.ebi.pride.mongodb.molecules.repo.peptide;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import uk.ac.ebi.pride.archive.dataprovider.common.Triple;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideEvidence;


import java.util.Collection;
import java.util.List;

/**
 * @author ypriverol
 */
public interface PridePeptideEvidenceMongoRepositoryCustom {

    /**
     * Filter By Attributes
     * @param filters Attributes in {@link Triple} structure
     * @param page Pageable
     * @return List of {@link PrideMongoPeptideEvidence}
     */
    Page<PrideMongoPeptideEvidence> filterByAttributes(List<Triple<String, String, String>> filters, Pageable page) ;

    /**
     * Find by A list of Accessions
     * @param accessions Accessions of the PSMs
     * @param sort Sort to order the output
     * @return Page
     */
    List<PrideMongoPeptideEvidence> findByIdAccessions(Collection<String> accessions, Sort sort);

    Page<PrideMongoPeptideEvidence> findPeptideEvidenceByProteinEvidence(String projectAccession, String assayAccession,
                                                                         String reportedProtein, Pageable page);

    List<String> findProteinAccessionByProjectAccessions(String projectAccession);
    List<String> findPeptideSequenceByProjectAccessions(String projectAccession);
}
