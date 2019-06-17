package uk.ac.ebi.pride.mongodb.molecules.repo.peptide;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.molecules.model.peptide.PrideMongoPeptideEvidence;

import java.util.Optional;

/**
 *
 * @author ypriverol
 */
@Repository
public interface PridePeptideEvidenceMongoRepository extends MongoRepository<PrideMongoPeptideEvidence, ObjectId>, PridePeptideEvidenceMongoRepositoryCustom {

    @Query("{'"+ PrideArchiveField.PROTEIN_ACCESSION + "' : ?0, '" + PrideArchiveField.PROTEIN_ASSAY_ACCESSION + "' : ?1, '" + PrideArchiveField.PEPTIDE_ACCESSION + "' : ?2 }")
    Optional<PrideMongoPeptideEvidence> findPeptideByProteinAndAssayAccession(String proteinAccession, String assayAccession, String peptideAccession);

//    @Query("{'"+ PrideArchiveField.ACCESSION + "' : ?0}")
//    Optional<PrideMongoPeptideEvidence> findByAccession(String accession);


}
