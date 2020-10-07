package uk.ac.ebi.pride.mongodb.molecules.repo.psm;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideEvidence;
import uk.ac.ebi.pride.mongodb.molecules.model.psm.PrideMongoPsmSummaryEvidence;

import java.util.Optional;

public interface PridePsmSummaryEvidenceMongoRepository extends MongoRepository<PrideMongoPsmSummaryEvidence, ObjectId>, PridePsmSummaryEvidenceMongoRepositoryCustom{

    @Query("{'"+ PrideArchiveField.USI + "' : ?0}")
    Optional<PrideMongoPeptideEvidence> findPsmSummaryByUsi(String usi);

    @Query("{'"+ PrideArchiveField.SPECTRA_USI + "' : ?0}")
    Optional<PrideMongoPsmSummaryEvidence> findPsmSummaryBySpectraUsi(String usi);


}
