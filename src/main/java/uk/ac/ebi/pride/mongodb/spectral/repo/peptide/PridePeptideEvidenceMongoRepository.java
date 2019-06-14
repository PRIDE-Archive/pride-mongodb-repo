package uk.ac.ebi.pride.mongodb.spectral.repo.peptide;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.spectral.model.peptide.PrideMongoPeptideEvidence;

import java.util.Optional;

/**
 *
 * @author ypriverol
 */
public interface PridePeptideEvidenceMongoRepository extends MongoRepository<PrideMongoPeptideEvidence, ObjectId>, PridePeptideEvidenceMongoRepositoryCustom {

    @Query("{'"+ PrideArchiveField.ACCESSION + "' : ?0}")
    Optional<PrideMongoPeptideEvidence> findByAccession(String accession);


}
