package uk.ac.ebi.pride.mongodb.molecules.repo.peptide;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideSummary;

@Repository
public interface PridePeptideSummaryMongoRepository extends MongoRepository<PrideMongoPeptideSummary, ObjectId> {

    Page<PrideMongoPeptideSummary> findByPeptideSequence(String peptideSequence, Pageable pageable);
    Page<PrideMongoPeptideSummary> findByPeptideSequenceAndProteinAccession(String peptideSequence, String proteinAccession, Pageable pageable);
}
