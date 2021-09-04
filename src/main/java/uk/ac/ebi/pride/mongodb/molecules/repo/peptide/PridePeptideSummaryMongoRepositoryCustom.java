package uk.ac.ebi.pride.mongodb.molecules.repo.peptide;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideSummary;

public interface PridePeptideSummaryMongoRepositoryCustom {

    Page<PrideMongoPeptideSummary> findUniprotPeptideSummaryByProperties(String peptideSequence,
                                                                         String proteinAccession,
                                                                         String proteinName,
                                                                         String gene,
                                                                         Pageable pageable);
}
