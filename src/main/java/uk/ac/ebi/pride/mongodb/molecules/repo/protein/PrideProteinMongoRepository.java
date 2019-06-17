package uk.ac.ebi.pride.mongodb.molecules.repo.protein;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uk.ac.ebi.pride.mongodb.archive.model.PrideArchiveField;
import uk.ac.ebi.pride.mongodb.molecules.model.protein.PrideMongoProteinEvidence;

import java.util.Optional;

@Repository
public interface PrideProteinMongoRepository extends MongoRepository<PrideMongoProteinEvidence, ObjectId>, PrideProteinMongoRepositoryCustom {

    @Query("{'"+ PrideArchiveField.PROTEIN_REPORTED_ACCESSION + "' : ?0, '" + PrideArchiveField.PROTEIN_ASSAY_ACCESSION + "' : ?1}")
    Optional<PrideMongoProteinEvidence> findByAccessionAndAssayAccession(String accession, String assayAccession);

}
